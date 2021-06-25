package prod.cuernasoft.com.seguridad

import grails.gorm.services.Service

@Service(Requestmap)
interface RequestmapService {

    Requestmap get(Serializable id)

    List<Requestmap> list(Map args)

    Long count()

    void delete(Serializable id)

    Requestmap save(Requestmap requestmap)

}