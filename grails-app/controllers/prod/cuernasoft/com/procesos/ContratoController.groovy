package prod.cuernasoft.com.procesos

import grails.validation.ValidationException
import grails.converters.JSON
import prod.cuernasoft.com.admin.Empresa
import prod.cuernasoft.com.catalogos.Comprador

import static org.springframework.http.HttpStatus.*

class ContratoController {
    def IEmpresaService
    ContratoService contratoService

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

        def c = Contrato.createCriteria()
        def results = c.list (max: params.max, offset: params?.offset){
            eq('empresa', empresaInstance)
            and{

            }
        }

        respond results, model:[contratoCount: results.totalCount]
    }

    def show(Long id) {
        def empresaInstance = null
        try{ empresaInstance = IEmpresaService.empresa(request) }catch(e){ println "ERROR: ${e}"}
        if(!empresaInstance){ response.status = 404; return }

        respond contratoService.get(id), model: [empresaInstance: empresaInstance]
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

        if (contrato == null) {
            notFound()
            return
        }

        try {
            contratoService.save(contrato)
        } catch (ValidationException e) {
            respond contrato.errors, view:'create', model: [empresaInstance: empresaInstance]
            return
        }

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

        respond contratoService.get(id), model: [empresaInstance: empresaInstance]
    }

    def update(Contrato contrato) {
        def empresaInstance = null
        try{ empresaInstance = IEmpresaService.empresa(request) }catch(e){ println "ERROR: ${e}" }
        if(!empresaInstance){ response.status = 404; return true }

        if (contrato == null) {
            notFound()
            return
        }

        try {
            contratoService.save(contrato)
        } catch (ValidationException e) {
            respond contrato.errors, view:'edit', model: [empresaInstance: empresaInstance]
            return
        }

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
                costo: it?.deudaActual,
                comprador: it?.comprador,
                mesualidad: it?.mesualidad
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
}
