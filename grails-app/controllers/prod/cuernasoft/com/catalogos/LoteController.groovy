package prod.cuernasoft.com.catalogos

import grails.validation.ValidationException
import prod.cuernasoft.com.seguridad.Usuario

import static org.springframework.http.HttpStatus.*

class LoteController {

    LoteService loteService
    def springSecurityService
    def IEmpresaService

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        def empresaInstance = null
        try{ empresaInstance = IEmpresaService.empresa(request) }catch(e){ println "ERROR: ${e}"}
        if(!empresaInstance){ response.status = 404; return; }

        params.max = Math.min(max ?: 10, 100)
        params?.offset = params?.offset ? params?.int('offset') : 0
        params?.order = params?.order ? params?.order?.toString() : 'asc'
        params?.sort = params?.sort ? params?.sort?.toString() : 'id'

        def c = Lote.createCriteria()
        def results = c.list (max: params.max, offset: params?.offset){
            and{
                if(params?.identificador) like("identificador", "%${params?.identificador}%")
                if(params?.ubicacion) like("ubicacion", "%${params?.ubicacion}%")
                if(params?.vendido?.toString() == 'true') eq('vendido', true)
                if(params?.vendido?.toString() == 'false') eq('vendido', false)
            }
            order(params?.sort, params?.order)
        }
        respond results, model:[loteCount: results.totalCount, identificador: params?.identificador, ubicacion: params?.ubicacion, vendido: params?.vendido]
    }

    def show(Long id) {
        respond loteService.get(id)
    }

    def create() {
        respond new Lote(params)
    }

    def save(Lote lote) {
        if (lote == null) {
            notFound()
            return
        }

        try {
            loteService.save(lote)
        } catch (ValidationException e) {
            respond lote.errors, view:'create'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'lote.label', default: 'Lote'), lote.id])
                redirect lote
            }
            '*' { respond lote, [status: CREATED] }
        }
    }

    def edit(Long id) {
        respond loteService.get(id)
    }

    def update(Lote lote) {
        if (lote == null) {
            notFound()
            return
        }

        try {
            loteService.save(lote)
        } catch (ValidationException e) {
            respond lote.errors, view:'edit'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'lote.label', default: 'Lote'), lote.id])
                redirect lote
            }
            '*'{ respond lote, [status: OK] }
        }
    }

    def delete(Long id) {
        if (id == null) {
            notFound()
            return
        }

        loteService.delete(id)

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'lote.label', default: 'Lote'), id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'lote.label', default: 'Lote'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
