package prod.cuernasoft.com.seguridad

import grails.validation.ValidationException
import static org.springframework.http.HttpStatus.*

class UsuarioController {

    UsuarioService usuarioService
    UsuarioRoleService usuarioRoleService
    def springSecurityService

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        params?.offset = params?.offset ? params?.int('offset') : 0
        params?.order = params?.order ? params?.order?.toString() : 'asc'
        params?.sort = params?.sort ? params?.sort?.toString() : 'id'
        //respond usuarioService.list(params), model:[usuarioCount: usuarioService.count()]


        def c = Usuario.createCriteria()
        def results = c.list (max: params.max, offset: params?.offset){
            and{
                if(params?.nombreCompleto && params?.nombreCompleto?.toString() != 'null')
                    like("nombreCompleto", "%${params?.nombreCompleto}%")
                if(params?.enabled?.toString() == 'true') eq('enabled', true)
                if(params?.enabled?.toString() == 'false') eq('enabled', false)
            }

            order(params?.sort, params?.order)
        }

        respond results, model:[usuarioCount: results.totalCount, nombreCompleto: params?.nombreCompleto, enabled: params?.enabled]
    }

    def show(Long id) {
        respond usuarioService.get(id)
    }

    def create() {
        def usuarioInstance = null
        try { usuarioInstance = Usuario.get(springSecurityService.principal.id) } catch (e) { }

        respond new Usuario(params), model: [empresa: usuarioInstance?.empresa]
    }

    def save(Usuario usuario) {
        if (usuario == null) {
            notFound()
            return
        }

        if(params?.password?.toString() == 'null'){
            flash.message = "Favor de seleccionar ingresar el passoword del usuario"
            respond usuario.errors, view:'create', params: params
            return
        }

        if(params?.password != params?.passwordR){
            flash.message = "El password y repite password deben ser iguales"
            respond usuario.errors, view:'create', params: params
            return
        }
        println params
        def rolesList = params.list('iRoles')
        if(!rolesList || rolesList.size() <= 0){
            flash.message = "Favor de seleccionar un Rol por lo menos"
            respond usuario.errors, view:'create', params: params
            return
        }


        if(!params?.password){
            flash.message = "El password y repite password deben ser iguales! LUL"
            respond usuario.errors, view:'create', params: params
            return
        }

        try {
            usuarioService.save(usuario)
        } catch (ValidationException e) {
            respond usuario.errors, view:'create'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'usuario.label', default: 'Usuario'), usuario.id])
                redirect usuario
            }
            '*' { respond usuario, [status: CREATED] }
        }
    }

    def edit(Long id) {
        respond usuarioService.get(id)
    }

    def editPassword(Long id) {
        respond usuarioService.get(id)
    }

    def update(Usuario usuario) {
        if (usuario == null) {
            notFound()
            return
        }

        try {
            usuarioService.save(usuario)
        } catch (ValidationException e) {
            respond usuario.errors, view:'edit'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'usuario.label', default: 'Usuario'), usuario.id])
                redirect usuario
            }
            '*'{ respond usuario, [status: OK] }
        }
    }

    def updatePasswordUsuario(Usuario usuario){
        if (usuario == null) {
            notFound()
            return
        }

        if(params?.password?.toString() == 'null'){
            flash.message = "Favor de seleccionar ingresar el passoword del usuario"
            respond usuario.errors, view:'editPassword'
            return
        }

        if(params?.password != params?.passwordR){
            flash.message = "El password y repite password deben ser iguales"
            respond usuario.errors, view:'editPassword'
            return
        }

        try {
            usuarioService.save(usuario)
        } catch (ValidationException e) {
            respond usuario.errors, view:'edit'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'usuario.label', default: 'Usuario'), usuario.username, "o"])
                redirect usuario
            }
            '*' { respond usuario, [status: OK] }
        }
    }

    def delete(Long id) {
        if (id == null) {
            notFound()
            return
        }

        usuarioService.delete(id)

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'usuario.label', default: 'Usuario'), id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'usuario.label', default: 'Usuario'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }

    def updateRoles(rolList, Usuario usuario){
        def iListDemo = UsuarioRole.findAll("from UsuarioRole as ur where ur.usuario.id = ${usuario?.id} and ur.role.authority NOT IN (${rolList?.toString()?.replace('[','')?.replace(']','')}) ")
        iListDemo.each{usuarioRoleService.delete(it) }

        rolList.each{
            def iRole = Role.get(it)

            if(iRole){
                def iUserRole = new UsuarioRole()
                iUserRole.role = iRole
                iUserRole.usuario = usuario

                try{ usuarioRoleService.save(iUserRole) }catch(e){ println "|ERROR| ${new Date()?.format('dd/MM/yyyy HH:mm:ss')}| ${e}" }
            }
        }
    }
}
