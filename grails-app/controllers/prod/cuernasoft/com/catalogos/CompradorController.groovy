package prod.cuernasoft.com.catalogos

import grails.validation.ValidationException
import static org.springframework.http.HttpStatus.*

class CompradorController {
    def IEmpresaService
    CompradorService compradorService

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        //Empresa
        def empresaInstance = null
        try{ empresaInstance = IEmpresaService.getEmpresa(request) }catch(e){ println "ERROR: ${e}"}
        if(!empresaInstance){
            response.status = 404; return
        }

        params.max = Math.min(max ?: 10, 100)
        params?.offset = params?.offset ? params?.int('offset') : 0
        params?.order = params?.order ? params?.order?.toString() : 'desc'
        params?.sort = params?.sort ? params?.sort?.toString() : 'id'

        def c = Comprador.createCriteria()
        def results = c.list (max: params.max, offset: params?.offset){
            and{
                eq("empresa", empresaInstance)
                if(params?.activo?.toString() == 'true'){
                    eq('activo', true)
                }
                if(params?.nombre && params?.nombre?.toString() != 'null'){
                    like("nombre", "%${params?.nombre}%")
                }
                if(params?.telefono && params?.telefono?.toString() != 'null'){
                    like("telefono", "%${params?.telefono}%")
                }
                if(params?.activo?.toString() == 'false'){
                    eq('activo', false)
                }
            }
            order(params?.sort, params?.order)
        }


        respond results, model:[compradorCount: results.totalCount]
    }

    def show(Long id) {
        respond compradorService.get(id)
    }

    def create() {
        respond new Comprador(params)
    }

    def save(Comprador comprador) {
        //Empresa
        def empresaInstance = null
        try{ empresaInstance = IEmpresaService.getEmpresa(request) }catch(e){ }
        println "|save| empresaInstance: ${empresaInstance}"
        if(!empresaInstance){
            response.status = 404; return
        }
        comprador.empresa = empresaInstance

        if (comprador == null) {
            notFound()
            return
        }

        try {
            compradorService.save(comprador)
        } catch (ValidationException e) {
            respond comprador.errors, view:'create'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'comprador.label', default: 'Comprador'), comprador.id])
                redirect comprador
            }
            '*' { respond comprador, [status: CREATED] }
        }
    }

    def edit(Long id) {
        respond compradorService.get(id)
    }

    def update(Comprador comprador) {
        //Empresa
        def empresaInstance = null
        try{ empresaInstance = IEmpresaService.getEmpresa(request) }catch(e){ }
        println "|update| empresaInstance: ${empresaInstance}"
        if(!empresaInstance){
            response.status = 404; return
        }
        comprador.empresa = empresaInstance

        if (comprador == null) {
            notFound()
            return
        }

        try {
            compradorService.save(comprador)
        } catch (ValidationException e) {
            respond comprador.errors, view:'edit'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'comprador.label', default: 'Comprador'), comprador.id])
                redirect comprador
            }
            '*'{ respond comprador, [status: OK] }
        }
    }

    def delete(Long id) {
        if (id == null) {
            notFound()
            return
        }

        compradorService.delete(id)

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'comprador.label', default: 'Comprador'), id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'comprador.label', default: 'Comprador'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
