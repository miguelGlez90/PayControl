package prod.cuernasoft.com.seguridad

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString
import prod.cuernasoft.com.admin.Empresa

import grails.compiler.GrailsCompileStatic

@GrailsCompileStatic
@EqualsAndHashCode(includes='username')
@ToString(includes='username', includeNames=true, includePackage=false)
class Usuario implements Serializable {

    private static final long serialVersionUID = 1

    String username
    String password
    boolean enabled = true
    boolean accountExpired
    boolean accountLocked
    boolean passwordExpired
    Empresa empresa
    Date fechaAlta = new Date()
    String nombreCompleto

    Set<Role> getAuthorities() {
        (UsuarioRole.findAllByUsuario(this) as List<UsuarioRole>)*.role as Set<Role>
    }

    static constraints = {
        password nullable: false, blank: false, password: true
        username nullable: false, blank: false, unique: true
        nombreCompleto nullable: true, blank: true
    }

    static mapping = {
	    password column: '`password`'
    }
}
