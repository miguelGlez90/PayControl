package prod.cuernasoft.com.procesos

import grails.testing.mixin.integration.Integration
import grails.gorm.transactions.Rollback
import spock.lang.Specification
import org.hibernate.SessionFactory

@Integration
@Rollback
class CobroServiceSpec extends Specification {

    CobroService cobroService
    SessionFactory sessionFactory

    private Long setupData() {
        // TODO: Populate valid domain instances and return a valid ID
        //new Cobro(...).save(flush: true, failOnError: true)
        //new Cobro(...).save(flush: true, failOnError: true)
        //Cobro cobro = new Cobro(...).save(flush: true, failOnError: true)
        //new Cobro(...).save(flush: true, failOnError: true)
        //new Cobro(...).save(flush: true, failOnError: true)
        assert false, "TODO: Provide a setupData() implementation for this generated test suite"
        //cobro.id
    }

    void "test get"() {
        setupData()

        expect:
        cobroService.get(1) != null
    }

    void "test list"() {
        setupData()

        when:
        List<Cobro> cobroList = cobroService.list(max: 2, offset: 2)

        then:
        cobroList.size() == 2
        assert false, "TODO: Verify the correct instances are returned"
    }

    void "test count"() {
        setupData()

        expect:
        cobroService.count() == 5
    }

    void "test delete"() {
        Long cobroId = setupData()

        expect:
        cobroService.count() == 5

        when:
        cobroService.delete(cobroId)
        sessionFactory.currentSession.flush()

        then:
        cobroService.count() == 4
    }

    void "test save"() {
        when:
        assert false, "TODO: Provide a valid instance to save"
        Cobro cobro = new Cobro()
        cobroService.save(cobro)

        then:
        cobro.id != null
    }
}
