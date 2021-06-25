package prod.cuernasoft.com.seguridad

import grails.testing.mixin.integration.Integration
import grails.gorm.transactions.Rollback
import spock.lang.Specification
import org.hibernate.SessionFactory

@Integration
@Rollback
class UsuarioRoleServiceSpec extends Specification {

    UsuarioRoleService usuarioRoleService
    SessionFactory sessionFactory

    private Long setupData() {
        // TODO: Populate valid domain instances and return a valid ID
        //new UsuarioRole(...).save(flush: true, failOnError: true)
        //new UsuarioRole(...).save(flush: true, failOnError: true)
        //UsuarioRole usuarioRole = new UsuarioRole(...).save(flush: true, failOnError: true)
        //new UsuarioRole(...).save(flush: true, failOnError: true)
        //new UsuarioRole(...).save(flush: true, failOnError: true)
        assert false, "TODO: Provide a setupData() implementation for this generated test suite"
        //usuarioRole.id
    }

    void "test get"() {
        setupData()

        expect:
        usuarioRoleService.get(1) != null
    }

    void "test list"() {
        setupData()

        when:
        List<UsuarioRole> usuarioRoleList = usuarioRoleService.list(max: 2, offset: 2)

        then:
        usuarioRoleList.size() == 2
        assert false, "TODO: Verify the correct instances are returned"
    }

    void "test count"() {
        setupData()

        expect:
        usuarioRoleService.count() == 5
    }

    void "test delete"() {
        Long usuarioRoleId = setupData()

        expect:
        usuarioRoleService.count() == 5

        when:
        usuarioRoleService.delete(usuarioRoleId)
        sessionFactory.currentSession.flush()

        then:
        usuarioRoleService.count() == 4
    }

    void "test save"() {
        when:
        assert false, "TODO: Provide a valid instance to save"
        UsuarioRole usuarioRole = new UsuarioRole()
        usuarioRoleService.save(usuarioRole)

        then:
        usuarioRole.id != null
    }
}
