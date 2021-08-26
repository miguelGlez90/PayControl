package prod.cuernasoft.com.catalogos

import prod.cuernasoft.com.admin.Empresa

class Comprador {
    Empresa empresa
    String nombre
    String domicilio
    String telefono
    Date fechaAlta = new Date()
    boolean activo = true //Estatus en el sistema
    
    static constraints = {
        nombre (nullable: false, blank: false)
        domicilio (nullable: true, blank: true)
        telefono (nullable: true, blank: true)
    }
    
    String toString(){
        return nombre
    }
}
