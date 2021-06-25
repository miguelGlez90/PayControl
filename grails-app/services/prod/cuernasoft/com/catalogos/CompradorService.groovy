package prod.cuernasoft.com.catalogos

import grails.gorm.services.Service

@Service(Comprador)
interface CompradorService {

    Comprador get(Serializable id)

    List<Comprador> list(Map args)

    Long count()

    void delete(Serializable id)

    Comprador save(Comprador comprador)

}