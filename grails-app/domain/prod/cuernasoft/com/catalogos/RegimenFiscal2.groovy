package prod.cuernasoft.com.paycontrol

import prod.cuernasoft.com.admin.Empresa

class RegimenFiscal {
    Empresa empresa
    String regimen

    static constraints = {
    }
    
    String toString(){
        return regimen
    }
}
