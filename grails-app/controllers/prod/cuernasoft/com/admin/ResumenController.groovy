package prod.cuernasoft.com.admin

import grails.converters.JSON
import prod.cuernasoft.com.procesos.Contrato
import prod.cuernasoft.com.catalogos.Lote

class ResumenController {
    def IEmpresaService

    def index() { }

    def getResumenAll(){
        def empresaInstance = null
        try{ empresaInstance = IEmpresaService.empresa(request) }catch(e){ println "ERROR: ${e}"}
        if(!empresaInstance){ response.status = 404; return }

        def resumenLotes = getResumenLotes.call(empresaInstance)
        def resumenContratos = getResumenContratos.call(empresaInstance)
        def model = [lotesVendiso: resumenLotes?.vendidos, lotesPendientes: resumenLotes?.pendientes, contratosCerrados: resumenContratos?.cerrados, contratosAbiertos: resumenContratos?.abiertos] as JSON

        return model
    }

    def getResumenLotes(){
        def empresaInstance = null
        try{ empresaInstance = IEmpresaService.empresa(request) }catch(e){ println "ERROR: ${e}"}
        if(!empresaInstance){ response.status = 404; return }
        def resumenLotes = getResumenLotes.call(empresaInstance)
        return resumenLotes as JSON
    }

    def getResumenLotes={empresaInstance->
        def lotesVendidos = Lote.countByVendido(true)
        def lotesPendientes = Lote.countByVendido(false)

        return [vendidos: lotesVendidos, pendientes: lotesPendientes]
    }

    def getResumenContratos={empresaInstance->
        def contratosCerrados = Contrato.countByEmpresaAndAbierto(empresaInstance, false)
        def contratosAbiertos = Contrato.countByEmpresaAndAbierto(empresaInstance, true)
        return [cerrados: contratosCerrados, abiertos: contratosAbiertos]
    }
}
