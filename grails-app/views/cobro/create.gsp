<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'cobro.label', default: 'Cobro')}" />
        <title><g:message code="default.create.label" args="[entityName]" /></title>
    </head>
    <body>


    <div class="card mb-4 wow fadeIn animated" style="visibility: visible; animation-name: fadeIn;">
        <div class="card-body d-sm-flex justify-content-between">
            <h4 class="mb-2 mb-sm-0 pt-1">
                <g:link class="list" action="index"><i class="fas fa-th-large"></i> <g:message code="default.list.label" args="[entityName]" /></g:link>
                <span>/</span>
                <span><g:message code="default.create.label" args="[entityName]" /></span>
            </h4>
        </div>
    </div>


    <div class="col-lg-12 col-md-12 mb-12">
        <div class="card">
            <div class="card-header"><g:message code="default.create.label" args="[entityName]"/></div>
            <div class="card-body">

                <g:if test="${flash.message}">
                    <div class="alert alert-info" role="alert">${flash.message}</div>
                </g:if>

                <g:hasErrors bean="${this.cobro}">
                    <ul class="errors" role="alert">
                        <g:eachError bean="${this.cobro}" var="error">
                            <li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
                        </g:eachError>
                    </ul>
                </g:hasErrors>



                <g:form resource="${this.cobro}" method="POST">
                    <fieldset class="form">
                        <div class="form-group col-md-6">
                            <label for="rfc">Contrato</label>
                            <g:field type="text" id="contratoNumber" name="contratoNumber" class="form-control" value="" />
                        </div>
                        <div class="form-group col-md-6">
                            <label for="monto">Monto</label>
                            <g:field type="text" onkeypress="javascript:return toDecimal(event,this);" id="monto" name="monto" class="form-control" value="" />
                        </div>
                        <div class="form-group col-md-6">
                            <label for="formaDePago">Forma de pago</label>
                            <label>Forma de pago</label>
                            <select class="form-control" id="formaDePago" name="formaDePago">
                                <option value="0">Efectivo</option>
                                <option value="1">Cheque</option>
                                <option value="2">Transferencia Bancaria o Dep&oacute;sito Bancario</option>
                                <option value="3">Bienes/Servicios</option>
                            </select>
                        </div>
                        <f:all bean="cobro" order="['referencia']"/>
                    </fieldset>
                    <br/>
                    <fieldset class="buttons">
                        <button type="submit"  class="btn btn-outline-secondary btn-rounded ripple-surface ripple-surface-dark">
                            <g:message code="default.button.create.label" args="[entityName]"/>
                        </button>
                    </fieldset>
                </g:form>
            </div>
        </div>
    </div>
    
    <span id="linkSearchContract" style="display: none">${createLink(controller: 'contrato', action: 'searchContractAjax')}</span>
    
    <script type="application/javascript">
        function toDecimal(evt, el) {
            var charCode = (evt.which) ? evt.which : event.keyCode;
            var number = el.value.split('.');
            if (charCode !== 46 && charCode > 31 && (charCode < 48 || charCode > 57)) {
                return false;
            }
            //just one dot (thanks ddlab)
            if (number.length > 1 && charCode === 46) {
                return false;
            }
            //get the carat position
            var caratPos = getSelectionStart(el);
            var dotPos = el.value.indexOf(".");
            if (caratPos > dotPos && dotPos > -1 && (number[1].length > 1)) {
                return false;
            }
            return true;
        }
    </script>
    <script>        
        window.onload = function() {
            autoCompleteProducto('contratoNumber', $('#linkSearchContract').html());


            function autoCompleteProducto(iElementId, url) {
                if(!iElementId) return true;
                if(!document.getElementById(iElementId)) return false;

                $( "#"+ iElementId ).autocomplete({
                    source: function( request, response ) {
                        $.ajax( {
                            url: url,
                            data: { frase: request.term },
                            success: function( data ) {
                                response( data.list );
                            }
                        } );
                    },
                    minLength: 1,
                    select: function( event, ui ) {
                        if(ui.item.id > 0){
                            setTimeout(function(){
                                document.getElementById('contratoNumber').value = ui.item.numero;
                                document.getElementById('monto').value = ui.item.mesualidad;
                            }, 400);
                        }else{
                            document.getElementById('contratoNumber').value = '';
                        }
                    }
                } ).autocomplete("instance")._renderItem = function (ul, item) {
                    if(item.id <= 0) return $("<li>").append("<dl><dt>" + item.nombre + "</dt></dl>").appendTo(ul);
                    if(item.id > 0) return $("<li>").append("<dl><dt>" + item.numero + ": " + item.comprador.nombre + " (" + item.costo + ")</dt></dl>").appendTo(ul);
                };
            };
        };
    </script>
    </body>
</html>
