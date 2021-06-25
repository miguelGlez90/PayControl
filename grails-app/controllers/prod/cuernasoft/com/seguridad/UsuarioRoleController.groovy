package prod.cuernasoft.com.seguridad

import grails.validation.ValidationException
import static org.springframework.http.HttpStatus.*

class UsuarioRoleController {

    UsuarioRoleService usuarioRoleService

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond usuarioRoleService.list(params), model:[usuarioRoleCount: usuarioRoleService.count()]
    }

    def show(Long id) {
        respond usuarioRoleService.get(id)
    }

    def create() {
        respond new UsuarioRole(params)
    }

    def save(UsuarioRole usuarioRole) {
        if (usuarioRole == null) {
            notFound()
            return
        }

        try {
            usuarioRoleService.save(usuarioRole)
        } catch (ValidationException e) {
            respond usuarioRole.errors, view:'create'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'usuarioRole.label', default: 'UsuarioRole'), usuarioRole.id])
                redirect usuarioRole
            }
            '*' { respond usuarioRole, [status: CREATED] }
        }
    }

    def edit(Long id) {
        respond usuarioRoleService.get(id)
    }

    def update(UsuarioRole usuarioRole) {
        if (usuarioRole == null) {
            notFound()
            return
        }

        try {
            usuarioRoleService.save(usuarioRole)
        } catch (ValidationException e) {
            respond usuarioRole.errors, view:'edit'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'usuarioRole.label', default: 'UsuarioRole'), usuarioRole.id])
                redirect usuarioRole
            }
            '*'{ respond usuarioRole, [status: OK] }
        }
    }

    def delete(Long id) {
        if (id == null) {
            notFound()
            return
        }

        usuarioRoleService.delete(id)

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'usuarioRole.label', default: 'UsuarioRole'), id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'usuarioRole.label', default: 'UsuarioRole'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
