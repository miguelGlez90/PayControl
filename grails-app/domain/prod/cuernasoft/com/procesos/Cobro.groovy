package prod.cuernasoft.com.procesos

import prod.cuernasoft.com.admin.Empresa
import prod.cuernasoft.com.seguridad.Usuario

class Cobro {
    String folio 
    Empresa empresa
    Date fecha = new Date()
    Contrato contrato
    Double monto = 0
    int formaPago = 0 // 0: Efectivo, 1: Transferencia, 2:Cheque, 3:Deposito bancario
    String referencia
    boolean cancelado = false
    Usuario creadoPor
    
    static constraints = {
        folio (nullable: false, blank: false)
        creadoPor (nullable: false, blank: false)
        contrato (nullable: false, blank: false)
        monto (nullable: false, blank: false)
        formaPago (nullable: false, blank: false)
        referencia (nullable: true, blank: true)
    }
    
    def getFormaDePagoVenta(){
        if(this.formaPago == 0) return "Efectivo"
        if(this.formaPago == 1) return "Cheque"
        if(this.formaPago == 2) return "Transferencia Bancaria o Depósito Bancario"
        if(this.formaPago == 3) return "Bienes/Servicios"
        return "---"
    }
    
    static mapping = {
        sort fecha: "desc" // or "asc"
    }
}
