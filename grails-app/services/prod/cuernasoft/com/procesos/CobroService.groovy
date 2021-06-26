package prod.cuernasoft.com.procesos

import grails.gorm.services.Service

@Service(Cobro)
interface CobroService {

    Cobro get(Serializable id)

    List<Cobro> list(Map args)

    Long count()

    void delete(Serializable id)

    Cobro save(Cobro cobro)

}