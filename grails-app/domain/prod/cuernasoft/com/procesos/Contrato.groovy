package prod.cuernasoft.com.procesos

import prod.cuernasoft.com.admin.Empresa
import prod.cuernasoft.com.catalogos.Comprador
import prod.cuernasoft.com.catalogos.Lote
import prod.cuernasoft.com.catalogos.Vendedor
import prod.cuernasoft.com.seguridad.Usuario

class Contrato {
    Empresa empresa
    String numero //numero de contrato
    Date fecha = new Date()
    Vendedor vendedor
    Comprador comprador
    //Lote lote
    Double costo = 0
    Double enganche = 0
    Double mesualidad = 0 //Monto mensualidad
    Double deudaActual = 0
    int diaPago = 1
    int plazoCompra = 24 //Numero de meses a pagar
    boolean abierto = true
    boolean cancelado = false
    Usuario usuarioCancelo
    Date fechaCancelada
    
    static hasMany = [lotes:Lote]

    static constraints = {
        numero (nullable: false, blank: false, unique:true)
        vendedor (nullable: false, blank: false)
        comprador (nullable: false, blank: false)
        costo (nullable: false, blank: false)
        enganche (nullable: true, blank: true)
        lotes (nullable: true, blank: false)
        cancelado (nullable: true, blank: true)
        usuarioCancelo (nullable: true, blank: true)
        fechaCancelada (nullable: true, blank: true)
    }
    
    
    String toString(){
        return numero + " - " + comprador;
    }
}
