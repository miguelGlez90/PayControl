package prod.cuernasoft.com.catalogos

import grails.testing.mixin.integration.Integration
import grails.gorm.transactions.Rollback
import spock.lang.Specification
import org.hibernate.SessionFactory

@Integration
@Rollback
class CompradorServiceSpec extends Specification {

    CompradorService compradorService
    SessionFactory sessionFactory

    private Long setupData() {
        // TODO: Populate valid domain instances and return a valid ID
        //new Comprador(...).save(flush: true, failOnError: true)
        //new Comprador(...).save(flush: true, failOnError: true)
        //Comprador comprador = new Comprador(...).save(flush: true, failOnError: true)
        //new Comprador(...).save(flush: true, failOnError: true)
        //new Comprador(...).save(flush: true, failOnError: true)
        assert false, "TODO: Provide a setupData() implementation for this generated test suite"
        //comprador.id
    }

    void "test get"() {
        setupData()

        expect:
        compradorService.get(1) != null
    }

    void "test list"() {
        setupData()

        when:
        List<Comprador> compradorList = compradorService.list(max: 2, offset: 2)

        then:
        compradorList.size() == 2
        assert false, "TODO: Verify the correct instances are returned"
    }

    void "test count"() {
        setupData()

        expect:
        compradorService.count() == 5
    }

    void "test delete"() {
        Long compradorId = setupData()

        expect:
        compradorService.count() == 5

        when:
        compradorService.delete(compradorId)
        sessionFactory.currentSession.flush()

        then:
        compradorService.count() == 4
    }

    void "test save"() {
        when:
        assert false, "TODO: Provide a valid instance to save"
        Comprador comprador = new Comprador()
        compradorService.save(comprador)

        then:
        comprador.id != null
    }
}
