package prod.cuernasoft.com.catalogos

import grails.validation.ValidationException
import static org.springframework.http.HttpStatus.*

class VendedorController {

    VendedorService vendedorService

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond vendedorService.list(params), model:[vendedorCount: vendedorService.count()]
    }

    def show(Long id) {
        respond vendedorService.get(id)
    }

    def create() {
        respond new Vendedor(params)
    }

    def save(Vendedor vendedor) {
        if (vendedor == null) {
            notFound()
            return
        }

        try {
            vendedorService.save(vendedor)
        } catch (ValidationException e) {
            respond vendedor.errors, view:'create'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'vendedor.label', default: 'Vendedor'), vendedor.id])
                redirect vendedor
            }
            '*' { respond vendedor, [status: CREATED] }
        }
    }

    def edit(Long id) {
        respond vendedorService.get(id)
    }

    def update(Vendedor vendedor) {
        if (vendedor == null) {
            notFound()
            return
        }

        try {
            vendedorService.save(vendedor)
        } catch (ValidationException e) {
            respond vendedor.errors, view:'edit'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'vendedor.label', default: 'Vendedor'), vendedor.id])
                redirect vendedor
            }
            '*'{ respond vendedor, [status: OK] }
        }
    }

    def delete(Long id) {
        if (id == null) {
            notFound()
            return
        }

        vendedorService.delete(id)

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'vendedor.label', default: 'Vendedor'), id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'vendedor.label', default: 'Vendedor'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
