package prod.cuernasoft.com.procesos

import grails.validation.ValidationException
import static org.springframework.http.HttpStatus.*

class ContratoController {

    ContratoService contratoService

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond contratoService.list(params), model:[contratoCount: contratoService.count()]
    }

    def show(Long id) {
        respond contratoService.get(id)
    }

    def create() {
        respond new Contrato(params)
    }

    def save(Contrato contrato) {
        if (contrato == null) {
            notFound()
            return
        }

        try {
            contratoService.save(contrato)
        } catch (ValidationException e) {
            respond contrato.errors, view:'create'
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
        respond contratoService.get(id)
    }

    def update(Contrato contrato) {
        if (contrato == null) {
            notFound()
            return
        }

        try {
            contratoService.save(contrato)
        } catch (ValidationException e) {
            respond contrato.errors, view:'edit'
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
}
