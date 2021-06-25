package prod.cuernasoft.com.seguridad

import grails.validation.ValidationException
import static org.springframework.http.HttpStatus.*

class RequestmapController {

    RequestmapService requestmapService

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond requestmapService.list(params), model:[requestmapCount: requestmapService.count()]
    }

    def show(Long id) {
        respond requestmapService.get(id)
    }

    def create() {
        respond new Requestmap(params)
    }

    def save(Requestmap requestmap) {
        if (requestmap == null) {
            notFound()
            return
        }

        try {
            requestmapService.save(requestmap)
        } catch (ValidationException e) {
            respond requestmap.errors, view:'create'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'requestmap.label', default: 'Requestmap'), requestmap.id])
                redirect requestmap
            }
            '*' { respond requestmap, [status: CREATED] }
        }
    }

    def edit(Long id) {
        respond requestmapService.get(id)
    }

    def update(Requestmap requestmap) {
        if (requestmap == null) {
            notFound()
            return
        }

        try {
            requestmapService.save(requestmap)
        } catch (ValidationException e) {
            respond requestmap.errors, view:'edit'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'requestmap.label', default: 'Requestmap'), requestmap.id])
                redirect requestmap
            }
            '*'{ respond requestmap, [status: OK] }
        }
    }

    def delete(Long id) {
        if (id == null) {
            notFound()
            return
        }

        requestmapService.delete(id)

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'requestmap.label', default: 'Requestmap'), id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'requestmap.label', default: 'Requestmap'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
