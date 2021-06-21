package prod.cuernasoft.com.procesos

import prod.cuernasoft.com.admin.Empresa

class Cobro {
    Empresa empresa
    Date fecha = new Date()
    Contrato contrato
    Double monto = 0
    int formaPago = 0 // 0: Efectivo, 1: Transferencia, 2:Cheque, 3:Deposito bancario
    String referencia
    //Falta el usuario
    
    static constraints = {
        contrato (nullable: false, blank: false)
        monto (nullable: false, blank: false)
        formaPago (nullable: false, blank: false)
        referencia (nullable: true, blank: true)
    }
}
