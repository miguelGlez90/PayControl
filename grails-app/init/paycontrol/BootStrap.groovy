package paycontrol

import prod.cuernasoft.com.admin.Empresa
import prod.cuernasoft.com.seguridad.Requestmap
import prod.cuernasoft.com.seguridad.Role
import prod.cuernasoft.com.seguridad.Usuario
import prod.cuernasoft.com.seguridad.UsuarioRole

class BootStrap {
    def springSecurityService

    def init = { servletContext ->
        bootStrapEntities()
        springSecurityService.clearCachedRequestmaps() //Limpiar cache
    }
    def destroy = {
    }
    
    private void bootStrapEntities() throws Exception {
        initRequestmap()
        createEmpresaDefault()
        createRoles()
        createUsuarios()
    }
    
    private static void initRequestmap() {
        def existeRequestUser = Requestmap.findByUrl('/usuario/**')

        if(!existeRequestUser){
            for (String url in ['/error', '/index', '/index.gsp', '/**/favicon.ico', '/shutdown','/**/js/**', '/**/css/**', '/**/images/**','/login', '/login.*', '/login/*','/logout', '/logout.*', '/logout/*']) {new Requestmap(url: url, configAttribute: 'permitAll').save()}

            new Requestmap(url: '/assets/**', configAttribute: 'permitAll').save()
            new Requestmap(url: '/requestmap/**', configAttribute: 'permitAll').save()
            new Requestmap(url: '/role/**', configAttribute: 'permitAll').save()
            new Requestmap(url: '/usuario/**', configAttribute: 'permitAll').save()
            new Requestmap(url: '/', configAttribute: 'isAuthenticated()').save()
        }
    }
    
    private static void createEmpresaDefault() {
        def count = Empresa.count()
        if(count > 0) return ;
        
        def lmarca = "local.paycontrol.com"
        if (grails.util.Environment.current.name.toUpperCase() != "DEVELOPMENT") {
            println("VARIABLE DE ENTORNO:  "+grails.util.Environment.current.name.toUpperCase())
            lmarca = "dm.cuerna-soft.com"
        }
        
        println"creando empresa..."
        def iEmpresa = new Empresa()
        iEmpresa = new Empresa()
        iEmpresa.nombre = "Demos SA"
        iEmpresa.razonSocial = "Empresa Demo S.A de C.V."
        iEmpresa.marca = lmarca
        iEmpresa.estado = 'Morelos'
        iEmpresa.codigoPostal = "62588"
        iEmpresa.save()
    }
    
    private static void createRoles() {
        createRol('ROLE_SUPERUSER')
        createRol('ROLE_ADMINISTRADOR')
    }

    private static void createRol(String authority) {
        def iRole = Role.findByAuthority(authority)
        if(!iRole){
            println "|NEW|ROLE| authority: ${authority}|"
            iRole = new Role(authority: authority)
            iRole.save()
        }
    }

    private static void createUsuarios(String authority) {
        def iEmpresaDefault = Empresa.findByMarca('local.paycontrol.com')
        println"iEmpresaDefaultiEmpresaDefaultiEmpresaDefaultiEmpresaDefaultiEmpresaDefault "+iEmpresaDefault
        if(iEmpresaDefault){
            createUsuario('root', "root123?", "SUPER ADMINISTRADOR", iEmpresaDefault)
            createUsuario('admin', "Admin123?", "ADMINISTRADOR", iEmpresaDefault)
        }
    }
    
    private static void createUsuario(String nombreUsuario, String password, String NombreCompleto, Empresa empresa) {
        println "CREANDO USERS| nombreUsuario: ${nombreUsuario}| pass: ${password}|NombreCompleto: ${NombreCompleto}"
        def iRole
        if(nombreUsuario.toUpperCase() == 'ROOT') iRole = Role.findByAuthority('ROLE_SUPERUSER') else iRole = Role.findByAuthority('ROLE_ADMINISTRADOR')
        
        def existeUsuario = Usuario.findByUsername(nombreUsuario)
        
        if(!existeUsuario){
            def iUser = new Usuario()
            iUser.username = nombreUsuario
            iUser.enabled = true
            iUser.password = password
            iUser.nombreCompleto = NombreCompleto
            iUser.empresa = empresa
        
            if(iUser.save()){
                def iUserRole = new UsuarioRole()
                iUserRole.usuario = iUser
                iUserRole.role = iRole
                iUserRole.save()
                iUserRole.errors.allErrors.each {
                    println "|ERROR|iUserRole| ${it}"}
            }else {
                iUser.errors.allErrors.each {
                    println "|ERROR|USER| ${it}"
                }
            }
        }else{
            println "|EXISTE-ESTE-USUARIO: ${nombreUsuario}| BootStrap.createUsuario()|";
        }
    }
}
