package prod.cuernasoft.com.procesos

import grails.validation.ValidationException
import static org.springframework.http.HttpStatus.*

class CobroController {

    CobroService cobroService

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond cobroService.list(params), model:[cobroCount: cobroService.count()]
    }

    def show(Long id) {
        respond cobroService.get(id)
    }

    def create() {
        respond new Cobro(params)
    }

    def save(Cobro cobro) {
        if (cobro == null) {
            notFound()
            return
        }

        try {
            cobroService.save(cobro)
        } catch (ValidationException e) {
            respond cobro.errors, view:'create'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'cobro.label', default: 'Cobro'), cobro.id])
                redirect cobro
            }
            '*' { respond cobro, [status: CREATED] }
        }
    }

    def edit(Long id) {
        respond cobroService.get(id)
    }

    def update(Cobro cobro) {
        if (cobro == null) {
            notFound()
            return
        }

        try {
            cobroService.save(cobro)
        } catch (ValidationException e) {
            respond cobro.errors, view:'edit'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'cobro.label', default: 'Cobro'), cobro.id])
                redirect cobro
            }
            '*'{ respond cobro, [status: OK] }
        }
    }

    def delete(Long id) {
        if (id == null) {
            notFound()
            return
        }

        cobroService.delete(id)

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'cobro.label', default: 'Cobro'), id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'cobro.label', default: 'Cobro'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
