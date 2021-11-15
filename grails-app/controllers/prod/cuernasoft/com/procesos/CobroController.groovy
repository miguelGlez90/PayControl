package prod.cuernasoft.com.procesos

import grails.validation.ValidationException
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import static org.springframework.http.HttpStatus.*
import java.util.Formatter;
import prod.cuernasoft.com.catalogos.Lote
import prod.cuernasoft.com.seguridad.Usuario;
import grails.plugins.jasper.JasperExportFormat
import grails.plugins.jasper.JasperReportDef
import com.utilerias.NumeroLetra

class CobroController {
    def IEmpresaService, springSecurityService

    CobroService cobroService
    ContratoService contratoService
    def jasperService

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        /*params.max = Math.min(max ?: 10, 100)
        respond cobroService.list(params), model:[cobroCount: cobroService.count()]*/
        SimpleDateFormat formatterSql = new SimpleDateFormat ("yyyy-MM-dd hh:mm:ss")
        
        def empresaInstance = null
        try{ empresaInstance = IEmpresaService.empresa(request) }catch(e){ }
        if(!empresaInstance){ response.status = 404; return }

        params.max = Math.min(max ?: 10, 100)
        def contract
        
        if(params?.contratoNumber){
            contract = Contrato.findByEmpresaAndNumero(empresaInstance, params?.contratoNumber)
        }
        
        def dateIni
        def dateFin
        if(params?.fecha && params?.fecha?.toString() != ''){
            dateIni = formatterSql.parse(params?.fecha+" 00:00:00")
            dateFin = formatterSql.parse(params?.fecha+" 23:59:59")
        }

        def c = Cobro.createCriteria()
        def results = c.list (max: params.max, offset: params?.offset){
            eq("empresa", empresaInstance)
            and {
                if(params?.folio){
                    like("folio", "%" + params?.folio + "%")
                }
                if(contract){
                    eq("contrato", contract)
                }
                
                if(params?.fecha){
                    between("fecha", dateIni, dateFin)
                }
            }
            order("contrato", "desc")
            order("id", "desc")
        }
        respond results, model:[cobroCount: results.totalCount]
    }

    def show(Long id) {
        respond cobroService.get(id)
    }

    def create() {
        respond new Cobro(params)
    }

    def save(Cobro cobro) {
        if (cobro == null) {
            notFound()
            return
        }
        
        def empresaInstance = null
        try{ empresaInstance = IEmpresaService.empresa(request) }catch(e){e.printStackTrace()}
        if(!empresaInstance){ response.status = 404; return }
        
        def usuarioInstance
        try { usuarioInstance = Usuario.get(springSecurityService.principal.id) } catch (e) { }
        if(!usuarioInstance){ response.status = 404; return }

        try {
            //Valores por default
            def contract = Contrato.findByNumeroAndEmpresa(params?.contratoNumber, empresaInstance)
            if(contract){
                cobro.contrato = contract
            } else {
                println("Contrato no encontrado con el numero: " + params?.contratoNumber)
            }
            cobro.folio = this.getMaxCobro(contract);
            cobro.empresa = empresaInstance;
            cobro.creadoPor = usuarioInstance
            cobro.formaPago = params?.formaDePago as int
            cobroService.save(cobro)
            //VALIDACION DE MONTOS
            if(contract.deudaActual >= cobro.monto){
                //Update saldo del contrato
                contract.deudaActual = contract.deudaActual - cobro.monto;
                //Valida Apertura de contrato
                if(contract.deudaActual == 0){
                    contract.abierto = false;
                } else {
                    contract.abierto = true;
                }
                contratoService.save(contract as Contrato)
            } else {
                flash.message = "El monto no puede ser superior a la deuda actual: (" + contract.deudaActual + ")."
                respond cobro.errors, view:'create'
                return
            }
        } catch (ValidationException e) {
            respond cobro.errors, view:'create'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'cobro.label', default: 'Cobro'), cobro.id])
                redirect cobro
            }
            '*' { respond cobro, [status: CREATED] }
        }
    }

    def edit(Long id) {
        respond cobroService.get(id)
    }

    def update(Cobro cobro) {
        if (cobro == null) {
            notFound()
            return
        }

        try {
            //Actualización de saldo
            def contrato = cobro?.contrato;
            def deudaActualContrato = cobro?.contrato.deudaActual as double;
            //Aumentamos el monto original a la deuda
            deudaActualContrato = deudaActualContrato + (params?.montoOri as double);
            //Ahora descontamos el monto del cobro modificado
            deudaActualContrato = deudaActualContrato - cobro.monto;
            //Seteamos nueva deuda
            contrato.deudaActual = deudaActualContrato;
            
            //VALIDACION DE MONTOS ********** FALTA ***********
            if(contrato.deudaActual >= cobro.monto){
                //Valida Apertura de contrato
                if(contrato.deudaActual == 0){
                    contrato.abierto = false;
                } else {
                    contrato.abierto = true;
                }
                //Actualiza cobro
                cobroService.save(cobro)
                //Actualiza contrato:
                contratoService.save(contrato as Contrato)
            } else {
                flash.message = "El monto no puede ser superior a la deuda actual: (" + contract.deudaActual + ")."
                respond cobro.errors, view:'edit'
                return
            }
        } catch (ValidationException e) {
            respond cobro.errors, view:'edit'
            return
        }

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'cobro.label', default: 'Cobro'), cobro.id])
                redirect cobro
            }
            '*'{ respond cobro, [status: OK] }
        }
    }

    def delete(Long id) {
        if (id == null) {
            notFound()
            return
        }
        
        //Actualización de saldo
        def cobro = Cobro.get(id);
        def contrato = cobro?.contrato;
        def deudaActualContrato = cobro?.contrato.deudaActual as double;
        //Ahora descontamos el monto del cobro modificado
        deudaActualContrato = deudaActualContrato + cobro.monto;
        //Seteamos nueva deuda
        contrato.deudaActual = deudaActualContrato;
        //Valida Apertura de contrato
        if(contrato.deudaActual == 0){
            contrato.abierto = false;
        } else {
            contrato.abierto = true;
        }
        //Actualiza contrato:
        contratoService.save(contrato as Contrato)
        //Elimina cobro
        cobroService.delete(id)

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'cobro.label', default: 'Cobro'), id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'cobro.label', default: 'Cobro'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
    
    def getMaxCobro(Contrato contract) {
        def iCount = 0
        Formatter obj = new Formatter();
        def empresaInstance = null
        try{ empresaInstance = IEmpresaService.empresa(request) }catch(e){e.printStackTrace()}
        if(!empresaInstance){ response.status = 404; return }
        
        def c = Cobro.createCriteria();
        def results = c.list {
            like("empresa", empresaInstance)
            like("contrato", contract)
        }
        
        iCount = (results.size() + 1)
        
        return "Recibo No. " + String.valueOf(obj.format("%03d", iCount));
    }
    
    def stringToDecimal = {monto->
        DecimalFormat decimalFormat = new DecimalFormat("#,##0.00");
        String formatted = decimalFormat.format(monto);
        return "\$" + formatted;
    }
    
    def downloadRecibo(){
        def nombreReporte = "comprobanteCobro.jasper"
        def nameLogo = ""
        def path = ""
        def empresaInstance = null
        def iNumeroLetra = new NumeroLetra()
        def infoLotesReport = "LOTE: "
        def periodoReport = ""
        String[] meses = ["enero", "febrero", "marzo", "abril", "mayo", "junio", "julio", "agosto", "setiembre", "octubre", "noviembre", "diciembre"];
        
        try {
            try{ empresaInstance = IEmpresaService.empresa(request) }catch(e){e.printStackTrace()}
            if(!empresaInstance){ response.status = 404; return }
            if(empresaInstance){
                nameLogo = "logo" + empresaInstance.id + ".png"
                path = "grails-app\\assets\\images\\" + nameLogo
            }
            
            def cobro
            try{cobro = Cobro.get(params?.id as Long) }catch(e){ }

            if(!cobro){ render "Cobro no encontrado"; return true; }
            
            //Valida sistema operativo y variable de entorno
            if (grails.util.Environment.current.name.toUpperCase() != "DEVELOPMENT") {
                def lvarOS = System.getProperty("os.name")
                if(lvarOS.toUpperCase().contains("WINDOWS")){
                    def basePath = grailsApplication.mainContext.servletContext.getRealPath('assets');
                    path = basePath + "\\logo\\" + nameLogo
                } else {
                    path = empresaInstance.logo + "/" + nameLogo   //Production
                }                
            }
            
            //Obteniendo informacion de lotes
            def contract = cobro.contrato
            if(contract.lotes.size() == 1){
                infoLotesReport = "LOTE: "
                for (lote in contract.lotes) {
                    infoLotesReport = infoLotesReport + lote.identificador + " UBIC. " + lote.ubicacion
                }
            } else if(contract.lotes.size() > 1){
                infoLotesReport = "LOTES: "
                for (lote in contract.lotes) {
                    infoLotesReport = infoLotesReport + lote.identificador + " UBIC. " + lote.ubicacion + ", "
                }
                infoLotesReport = infoLotesReport.substring(0, infoLotesReport.length() - 2)
            }
            
            //Armando el periodo
            periodoReport = meses[cobro.fecha.getAt(Calendar.MONTH)] + " " + cobro.fecha.getAt(Calendar.YEAR);
            //Definicion de los parametros del reporte.
            def parametros = [:]
            parametros.empresaId = empresaInstance?.id?.toString();
            parametros.logoPath = path;
            parametros.cobroId = cobro.id;
            parametros.concepto = "PAGO DE MENSUALIDAD NO. " + cobro.folio.replace("Recibo No. 0", "") + "\n(" + infoLotesReport.toUpperCase() + ")\nCORRESPONDIENTE A " + periodoReport.toUpperCase();
            parametros.precioUnitario = stringToDecimal.call(cobro.monto);
            parametros.cantidadLetra = iNumeroLetra.convierteMontoALetras(cobro.monto)
            
            def reportDef = new JasperReportDef(name:nombreReporte, fileFormat: JasperExportFormat.PDF_FORMAT, parameters: parametros)
            def pdfData = jasperService.generateReport(reportDef).toByteArray()
            
            //cobro.folio
            def filename = cobro.folio + ".pdf"
        
            response.setHeader("Content-disposition", "filename=${filename}")
            response.contentType = 'application/pdf'
            response.outputStream << pdfData
            
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
