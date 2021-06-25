package prod.cuernasoft.com.procesos

import grails.gorm.services.Service

@Service(Contrato)
interface ContratoService {

    Contrato get(Serializable id)

    List<Contrato> list(Map args)

    Long count()

    void delete(Serializable id)

    Contrato save(Contrato contrato)

}