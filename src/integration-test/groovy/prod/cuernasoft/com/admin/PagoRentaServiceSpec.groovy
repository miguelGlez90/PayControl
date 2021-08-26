package prod.cuernasoft.com.admin

import grails.testing.mixin.integration.Integration
import grails.gorm.transactions.Rollback
import spock.lang.Specification
import org.hibernate.SessionFactory

@Integration
@Rollback
class PagoRentaServiceSpec extends Specification {

    PagoRentaService pagoRentaService
    SessionFactory sessionFactory

    private Long setupData() {
        // TODO: Populate valid domain instances and return a valid ID
        //new PagoRenta(...).save(flush: true, failOnError: true)
        //new PagoRenta(...).save(flush: true, failOnError: true)
        //PagoRenta pagoRenta = new PagoRenta(...).save(flush: true, failOnError: true)
        //new PagoRenta(...).save(flush: true, failOnError: true)
        //new PagoRenta(...).save(flush: true, failOnError: true)
        assert false, "TODO: Provide a setupData() implementation for this generated test suite"
        //pagoRenta.id
    }

    void "test get"() {
        setupData()

        expect:
        pagoRentaService.get(1) != null
    }

    void "test list"() {
        setupData()

        when:
        List<PagoRenta> pagoRentaList = pagoRentaService.list(max: 2, offset: 2)

        then:
        pagoRentaList.size() == 2
        assert false, "TODO: Verify the correct instances are returned"
    }

    void "test count"() {
        setupData()

        expect:
        pagoRentaService.count() == 5
    }

    void "test delete"() {
        Long pagoRentaId = setupData()

        expect:
        pagoRentaService.count() == 5

        when:
        pagoRentaService.delete(pagoRentaId)
        sessionFactory.currentSession.flush()

        then:
        pagoRentaService.count() == 4
    }

    void "test save"() {
        when:
        assert false, "TODO: Provide a valid instance to save"
        PagoRenta pagoRenta = new PagoRenta()
        pagoRentaService.save(pagoRenta)

        then:
        pagoRenta.id != null
    }
}
