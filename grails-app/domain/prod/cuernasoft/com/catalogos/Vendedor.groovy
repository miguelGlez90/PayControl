package prod.cuernasoft.com.catalogos

import prod.cuernasoft.com.admin.Empresa

class Vendedor {
    Empresa empresa
    String nombre
    String rfc
    String domicilioFiscal
    RegimenFiscal regimenFiscal
    Date fechaAlta = new Date()
    boolean activo = true//Estatus del sistema

    static constraints = {
        nombre (nullable: false, blank: false)
        rfc (nullable: true, blank: true)
        domicilioFiscal (nullable: true, blank: true)
        regimenFiscal (nullable: true, blank: true)
    }
    
    String toString(){
        return nombre
    }
}
