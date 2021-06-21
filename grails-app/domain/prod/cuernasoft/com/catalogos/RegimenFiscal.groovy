package prod.cuernasoft.com.catalogos

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
