package paycontrol

import grails.gorm.transactions.Transactional
import prod.cuernasoft.com.admin.Empresa

@Transactional
class IEmpresaService {

    def serviceMethod() {

    }

    def getEmpresa(request) {
        def url = request.getServerName()
        def empresa = null

        def empresaList = Empresa.findAll().each{
            def marcas = it.marca.split(",")
            def marcaid = it.id
            marcas.each{
                def marcaFound = (url).find(it)
                //println "|it: ${it}| marcaFound: ${marcaFound}| url: ${url}| it: ${it}"
                if(marcaFound){
                    empresa = Empresa.findByMarcaAndActivo(marcaFound?.toString(), true)
                    return empresa
                }
                marcaFound = null
            }
        }

        empresaList = null
        return empresa
    }
}
