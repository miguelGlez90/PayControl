package prod.cuernasoft.com.catalogos

import grails.gorm.services.Service

@Service(Lote)
interface LoteService {

    Lote get(Serializable id)

    List<Lote> list(Map args)

    Long count()

    void delete(Serializable id)

    Lote save(Lote lote)

}