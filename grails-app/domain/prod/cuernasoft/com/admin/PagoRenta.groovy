package prod.cuernasoft.com.admin

import prod.cuernasoft.com.seguridad.Usuario

class PagoRenta {
    Empresa empresa
    Date fechaPago = new Date()
    int periodo = 0 //  0 Mensual, 1 Trimestral, 2 Semestral, 3 Anual
    String concepto
    Double monto
    Usuario usuarioPago
    String observaciones
    boolean pagado = false
    
    static constraints = {
        empresa unique: ['fechaPago']
    }
}
