package prod.cuernasoft.com.catalogos

class Lote {
    String identificador //MZA. A, 20: A20
    Double costo = 0
    String medidas
    String ubicacion
    boolean vendido = false // Bandera para identificar si el lote fue vendido

    String toString(){
        return "Ubicación: " + ubicacion + ", Medidas: " + medidas + ", Costo: " + costo?.toString()
    }

    static constraints = {
        identificador (nullable: false, blank: false, unique: true) 
        costo (nullable: false, blank: false) 
        medidas (nullable: false, blank: false) 
        ubicacion (nullable: false, blank: false) 
    }
}
