package prod.cuernasoft.com.admin

import grails.gorm.services.Service

@Service(PagoRenta)
interface PagoRentaService {

    PagoRenta get(Serializable id)

    List<PagoRenta> list(Map args)

    Long count()

    void delete(Serializable id)

    PagoRenta save(PagoRenta pagoRenta)

}