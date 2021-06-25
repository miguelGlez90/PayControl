package prod.cuernasoft.com.catalogos

import grails.validation.ValidationException
import static org.springframework.http.HttpStatus.*

class LoteController {

    LoteService loteService

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond loteService.list(params), model:[loteCount: loteService.count()]
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
