package prod.cuernasoft.com.admin

import grails.validation.ValidationException
import static org.springframework.http.HttpStatus.*

class PagoRentaController {

    PagoRentaService pagoRentaService

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond pagoRentaService.list(params), model:[pagoRentaCount: pagoRentaService.count()]
    }

    def show(Long id) {
        respond pagoRentaService.get(id)
    }

    def create() {
        respond new PagoRenta(params)
    }

    def save(PagoRenta pagoRenta) {
        if (pagoRenta == null) {
            notFound()
            return
        }

        try {
            pagoRentaService.save(pagoRenta)
        } catch (ValidationException e) {
            respond pagoRenta.errors, view:'create'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'pagoRenta.label', default: 'PagoRenta'), pagoRenta.id])
                redirect pagoRenta
            }
            '*' { respond pagoRenta, [status: CREATED] }
        }
    }

    def edit(Long id) {
        respond pagoRentaService.get(id)
    }

    def update(PagoRenta pagoRenta) {
        if (pagoRenta == null) {
            notFound()
            return
        }

        try {
            pagoRentaService.save(pagoRenta)
        } catch (ValidationException e) {
            respond pagoRenta.errors, view:'edit'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'pagoRenta.label', default: 'PagoRenta'), pagoRenta.id])
                redirect pagoRenta
            }
            '*'{ respond pagoRenta, [status: OK] }
        }
    }

    def delete(Long id) {
        if (id == null) {
            notFound()
            return
        }

        pagoRentaService.delete(id)

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'pagoRenta.label', default: 'PagoRenta'), id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'pagoRenta.label', default: 'PagoRenta'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
