package prod.cuernasoft.com.procesos

import grails.validation.ValidationException
import static org.springframework.http.HttpStatus.*
import java.util.Formatter;
import prod.cuernasoft.com.seguridad.Usuario;

class CobroController {
    def IEmpresaService, springSecurityService

    CobroService cobroService
    ContratoService contratoService

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond cobroService.list(params), model:[cobroCount: cobroService.count()]
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
            cobroService.save(cobro)
            //Update saldo del contrato
            contract.deudaActual = contract.deudaActual - cobro.monto;
            contratoService.save(contract as Contrato)
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
            cobroService.save(cobro)
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
        
        return "Cobro No. " + String.valueOf(obj.format("%02d", iCount));
    }
}
