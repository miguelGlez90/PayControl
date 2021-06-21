package prod.cuernasoft.com.admin

class Empresa {
    String nombre
    String razonSocial
    boolean activo = true
    Date fechaAlta = new Date()
    String marca
    String logo
    String css
    String icon
    String calle
    String numeroInterior
    String numeroExterior
    String codigoPostal //limit: 5
    String colonia
    String municipio //Delegacion,Ciudad
    String estado
    String rfc
    String telefono
    String celular
    String notas
    Date fechaContratacion = new Date()
    int tipoContratacion = 0//  0 Mensual, 1 Trimestral, 2 Semestral, 3 Anual
    boolean rentaPagada = true
    Double costoRenta = 0
    boolean showLicenceMsg = false
    String pathImagesP

    static constraints = {
        nombre nullable: false, blank: false
        razonSocial nullable: false, blank: false
        marca nullable: false, blank: false, unique: true
        logo nullable: true, blank: true
        css nullable: true, blank: true
        icon nullable: true, blank: true
        calle nullable: true, blank: true
        numeroInterior nullable: true, blank: true
        numeroExterior nullable: true, blank: true
        codigoPostal nullable: false, blank: false, minSize: 4, maxSize: 5        
        colonia nullable: true, blank: true
        municipio nullable: true, blank: true
        estado nullable: false, blank: false
        rfc nullable: true, blank: true
        telefono nullable: true, blank: true
        celular nullable: true, blank: true
        notas nullable: true, blank: true
        pathImagesP nullable: true, blank: true
    }
    
    String toString(){
        return razonSocial
    }
}
