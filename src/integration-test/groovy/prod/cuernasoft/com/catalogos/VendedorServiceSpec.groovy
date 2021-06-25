package prod.cuernasoft.com.catalogos

import grails.testing.mixin.integration.Integration
import grails.gorm.transactions.Rollback
import spock.lang.Specification
import org.hibernate.SessionFactory

@Integration
@Rollback
class VendedorServiceSpec extends Specification {

    VendedorService vendedorService
    SessionFactory sessionFactory

    private Long setupData() {
        // TODO: Populate valid domain instances and return a valid ID
        //new Vendedor(...).save(flush: true, failOnError: true)
        //new Vendedor(...).save(flush: true, failOnError: true)
        //Vendedor vendedor = new Vendedor(...).save(flush: true, failOnError: true)
        //new Vendedor(...).save(flush: true, failOnError: true)
        //new Vendedor(...).save(flush: true, failOnError: true)
        assert false, "TODO: Provide a setupData() implementation for this generated test suite"
        //vendedor.id
    }

    void "test get"() {
        setupData()

        expect:
        vendedorService.get(1) != null
    }

    void "test list"() {
        setupData()

        when:
        List<Vendedor> vendedorList = vendedorService.list(max: 2, offset: 2)

        then:
        vendedorList.size() == 2
        assert false, "TODO: Verify the correct instances are returned"
    }

    void "test count"() {
        setupData()

        expect:
        vendedorService.count() == 5
    }

    void "test delete"() {
        Long vendedorId = setupData()

        expect:
        vendedorService.count() == 5

        when:
        vendedorService.delete(vendedorId)
        sessionFactory.currentSession.flush()

        then:
        vendedorService.count() == 4
    }

    void "test save"() {
        when:
        assert false, "TODO: Provide a valid instance to save"
        Vendedor vendedor = new Vendedor()
        vendedorService.save(vendedor)

        then:
        vendedor.id != null
    }
}
