package prod.cuernasoft.com.seguridad

import grails.gorm.services.Service

@Service(UsuarioRole)
interface UsuarioRoleService {

    UsuarioRole get(Serializable id)

    List<UsuarioRole> list(Map args)

    Long count()

    void delete(Serializable id)

    UsuarioRole save(UsuarioRole usuarioRole)

}