package prod.cuernasoft.com.procesos

import grails.validation.ValidationException
import prod.cuernasoft.com.catalogos.Lote
import prod.cuernasoft.com.catalogos.LoteService

import java.text.DecimalFormat
import grails.converters.JSON
import prod.cuernasoft.com.admin.Empresa
import prod.cuernasoft.com.catalogos.Comprador

import static org.springframework.http.HttpStatus.*

class ContratoController {
    def IEmpresaService
    ContratoService contratoService
    LoteService loteService

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        //Empresa
        def empresaInstance = null
        try{ empresaInstance = IEmpresaService.empresa(request) }catch(e){ println "ERROR: ${e}" }
        if(!empresaInstance){ response.status = 404; return true }

        params.max = Math.min(max ?: 10, 100)
        params?.offset = params?.offset ? params?.int('offset') : 0
        params?.order = params?.order ? params?.order?.toString() : 'desc'
        params?.sort = params?.sort ? params?.sort?.toString() : 'id'
        println params

        def c = Contrato.createCriteria()
        def results = c.list (max: params.max, offset: params?.offset){
            eq('empresa', empresaInstance)
            order(params?.sort, params?.order)
        }

        respond results, model:[contratoCount: results.totalCount]
    }

    def show(Long id) {
        def empresaInstance = null
        try{ empresaInstance = IEmpresaService.empresa(request) }catch(e){ println "ERROR: ${e}"}
        if(!empresaInstance){ response.status = 404; return }

        def contrato = contratoService.get(id)
        def c = Cobro.createCriteria()
        def ti = c.list {
            eq("empresa", empresaInstance)
            and {
                eq("contrato", contrato)
                eq('cancelado', false)
            }
            projections {
                sum "monto"
                rowCount()
            }
        }

        def monto = 0, count = 0

        try{ monto = ti[0][0] as double }catch(e){ }
        try{ count = ti[0][1] as Integer }catch(e){ }

        respond contrato, model: [empresaInstance: empresaInstance, montoCobrado: monto, countCobrado: count]
    }

    def create() {
        def empresaInstance = null
        try{ empresaInstance = IEmpresaService.empresa(request) }catch(e){ println "ERROR: ${e}"}
        if(!empresaInstance){ response.status = 404; return }

        respond new Contrato(params), model: [empresaInstance: empresaInstance]
    }

    def save(Contrato contrato) {
        def empresaInstance = null
        try{ empresaInstance = IEmpresaService.empresa(request) }catch(e){ println "ERROR: ${e}" }
        if(!empresaInstance){ response.status = 404; return true }

        def itemsLote = []
        def result = params?.iLotes?.split(',').each{
            try{ itemsLote.add(it as Integer) }catch(e){ }
        }

        def loteList = Lote.getAll(itemsLote)

        if(loteList.size() <= 0){
            flash.message= 'Favor de Seleccionar un Lote por lo menos'
            respond contrato.errors, view:'create', model: [empresaInstance: empresaInstance, iLotes: params?.iLotes, lotesJSON: params?.lotesJSON]
            return
        }

        loteList.each{ contrato.addToLotes(it) }

        contrato.deudaActual = contrato?.costo - contrato?.enganche


        if (contrato == null) {
            notFound()
            return
        }

        try {
            contratoService.save(contrato)
        } catch (ValidationException e) {
            respond contrato.errors, view:'create', model: [empresaInstance: empresaInstance, iLotes: params?.iLotes, lotesJSON: params?.lotesJSON]
            return
        }

        //Marca lotes como Vendido = true
        updateLotes.call(loteList)

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'contrato.label', default: 'Contrato'), contrato.id])
                redirect contrato
            }
            '*' { respond contrato, [status: CREATED] }
        }
    }

    def edit(Long id) {
        def empresaInstance = null
        try{ empresaInstance = IEmpresaService.empresa(request) }catch(e){ println "ERROR: ${e}" }
        if(!empresaInstance){ response.status = 404; return true }

        def contrato = contratoService.get(id)
        def cobroCount = Cobro.countByContratoAndCancelado(contrato, false)


        def c = Cobro.createCriteria()
        def ti = c.list {
            eq("empresa", empresaInstance)
            and {
                eq("contrato", contrato)
                eq('cancelado', false)
            }
            projections {
                sum "monto"
                rowCount()
            }
        }

        def monto = 0
        try{ monto = ti[0][0] as double }catch(e){ }

        def lotesModel = lotesList.call(contrato)
        def lotesList = lotesModel?.list as JSON

        respond contrato, model: [empresaInstance: empresaInstance, montoCobrado: monto, cobroCount: cobroCount, lotesJSON: lotesList]
    }

    def update(Contrato contrato) {
        def empresaInstance = null
        try{ empresaInstance = IEmpresaService.empresa(request) }catch(e){ println "ERROR: ${e}" }
        if(!empresaInstance){ response.status = 404; return true }

        if (contrato == null) {
            notFound()
            return
        }

        def cobroCount = Cobro.countByContratoAndCancelado(contrato, false)
        def c = Cobro.createCriteria()
        def ti = c.list {
            eq("empresa", empresaInstance)
            and {
                eq("contrato", contrato)
                eq('cancelado', false)
            }
            projections {
                sum "monto"
                rowCount()
            }
        }

        def lotesModel = lotesList.call(contrato)
        def lotesList = lotesModel?.list as JSON

        def monto = 0
        try{ monto = ti[0][0] as double }catch(e){ }


        def loteList
        if(cobroCount <= 0){
            updateLotes.call(contrato.lotes, false)
            contrato.lotes = null
            def itemsLote = []
            def result = params?.iLotes?.split(',').each{
                try{ itemsLote.add(it as Integer) }catch(e){ }
            }

            loteList = Lote.getAll(itemsLote)

            if(loteList.size() <= 0){
                flash.message= 'Favor de Seleccionar un Lote por lo menos'
                respond contrato.errors, view:'create', model: [empresaInstance: empresaInstance, iLotes: params?.iLotes, lotesJSON: params?.lotesJSON]
                return
            }

            loteList.each{ contrato.addToLotes(it) }//Add-lotes()
            contrato.deudaActual = contrato?.costo - contrato?.enganche
        }

        try {
            contratoService.save(contrato)
        } catch (ValidationException e) {
            respond contrato.errors, view:'edit', model: [empresaInstance: empresaInstance, montoCobrado: monto, cobroCount: cobroCount, lotesJSON: lotesList]
            return
        }

        //Marca lotes como Vendido = true
        if(cobroCount <= 0 && loteList) updateLotes.call(loteList)

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'contrato.label', default: 'Contrato'), contrato.id])
                redirect contrato
            }
            '*'{ respond contrato, [status: OK] }
        }
    }

    def delete(Long id) {
        if (id == null) {
            notFound()
            return
        }

        def contratoInst = Contrato.get(id)
        updateLotes.call(contratoInst?.lotes, false)

        contratoService.delete(id)

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'contrato.label', default: 'Contrato'), id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'contrato.label', default: 'Contrato'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
    
    def searchContractAjax(){
        def empresaInstance = null
        try{ empresaInstance = IEmpresaService.empresa(request) }catch(e){e.printStackTrace()}
        if(!empresaInstance){ response.status = 404; return }

        def contracts = this.getContracts(empresaInstance, params?.frase)

        if(contracts.size() <= 0){
            def listado = [[id:0, nombre: "Sin contratos..."]]
            def model = [codigo: 0, list: listado] as JSON
            render model
            return true
        }

        def list = contracts.collect{
            [
                id: it?.id,
                numero: it?.numero,
                costo: stringToDecimal.call(it?.deudaActual),
                comprador: it?.comprador,
                mesualidad: it?.mesualidad,
                deudaActual: it?.deudaActual
            ]
        }

        def model = [codigo: 0, list: list] as JSON
        render model
    }
    
    def getContracts(Empresa empresa, String frase){
        def tails = frase?.split(' ')
        if(tails.size() <= 0) return Contrato.findAllByEmpresaAndAbierto(empresa, true, [max: 250, sort: "numero", order: "asc", offset: 0])


        String query = "from Contrato as p where p.empresa.id = ${empresa?.id} "

        tails.each{
            if(it && it != ' ') query += "and concat_ws('', p.numero) like '%${it}%'"
        }

        def lista = Contrato.executeQuery(query, [max: 250, offset: 0])
        return lista
    }
    
    def stringToDecimal = {monto->
        DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");
        String formatted = decimalFormat.format(monto);
        return "\$" + formatted;
    }

    def updateLotes = { lotes, vendido = true->
        lotes.each{
            it.vendido = vendido
            try { loteService.save(it) } catch (ValidationException e) { println "ERROR|UPDATE-LOTE| ${e}"}
        }
    }

    def findLote(){
        def empresaInstance = null
        try{ empresaInstance = IEmpresaService.empresa(request) }catch(e){ println "ERROR: ${e}" }
        if(!empresaInstance){ response.status = 404; return true }

        String query =  "from Lote as l where l.vendido = false AND concat_ws('', l.identificador, l.ubicacion) like '%${params?.nombre}%' order by l.identificador"
        def results = Lote.executeQuery(query, [max: 15, offset: 0])

        results = results.collect{
            [
                    id: it?.id,
                    costo: it?.costo,
                    medidas: it?.medidas,
                    ubicacion: it?.ubicacion,
                    identificador: it?.identificador
            ]
        }

        if(results.size() <= 0)
            results = [[id:0, identificador: "No hay registros que mostrar"]]

        def model = [count: results.size(), list: results] as JSON
        render model
    }

    def lotesList={contrato1->
        def list = contrato1?.lotes
        list = list.collect{
            [
                    id: it?.id,
                    identificador: it?.identificador,
                    costo: it?.costo,
                    medidas: it?.medidas,
                    ubicacion: it?.ubicacion,
            ]
        }

        return [list: list, count: list.size()]
    }
}
