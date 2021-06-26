package prod.cuernasoft.com.catalogos

import grails.validation.ValidationException
import static org.springframework.http.HttpStatus.*

class CompradorController {

    CompradorService compradorService

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond compradorService.list(params), model:[compradorCount: compradorService.count()]
    }

    def show(Long id) {
        respond compradorService.get(id)
    }

    def create() {
        respond new Comprador(params)
    }

    def save(Comprador comprador) {
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
