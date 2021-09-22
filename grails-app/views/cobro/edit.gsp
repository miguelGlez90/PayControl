<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'cobro.label', default: 'Cobro')}" />
        <title><g:message code="default.edit.label" args="[entityName]" /></title>
    </head>
    <body>
    <div class="card mb-4 wow fadeIn animated" style="visibility: visible; animation-name: fadeIn;">
        <div class="card-body d-sm-flex justify-content-between">
            <h4 class="mb-2 mb-sm-0 pt-1">
                <g:link class="list" action="index"><i class="fas fa-th-large"></i> <g:message code="default.list.label" args="[entityName]" /></g:link>
                <span>/</span>
                <g:link class="create" action="create"><i class="fas fa-plus"></i> <g:message code="default.new.label" args="[entityName]" /></g:link>
                <span>/</span>
                <i class="fas fa-pen"></i> Editar
            </h4>
        </div>
    </div>


    <div class="col-lg-12 col-md-12 mb-12">
        <div class="card">
            <div class="card-header"><g:message code="default.edit.label" args="[entityName]"/></div>
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

                <g:form resource="${this.cobro}" method="PUT">
                    <g:hiddenField name="version" value="${this.cobro?.version}" />
                    <g:hiddenField name="montoOri" value="${this.cobro?.monto}" />
                    <fieldset class="form">
                        <div class="form-group col-md-6">
                            <label for="folio_v">Folio</label>
                            <g:field type="text" id="folio_v" name="folio_v" readonly="true" class="form-control" value="${this.cobro?.folio}"/>
                        </div>
                        <div class="form-group col-md-6">
                            <label for="contrato_v">Contrato</label>
                            <g:field type="text" id="contrato_v" name="contrato_v" readonly="true" class="form-control" value="${this.cobro?.contrato}"/>
                        </div>
                        <div class="form-group col-md-6">
                            <label for="fecha_v">Fecha</label>
                            <g:field type="text" id="fecha_v" name="fecha_v" readonly="true" class="form-control" value="${this.cobro?.fecha}"/>
                        </div>
                        <div class="form-group col-md-6">
                            <label for="formaPago">Forma de pago</label>
                            <select class="form-control" id="formaPago" name="formaPago">
                                <option value="-1">-- Seleccione --</option>
                                <option value="0" <g:if test="${this.cobro?.formaPago == 0}">selected</g:if>>Efectivo</option>
                                <option value="1" <g:if test="${this.cobro?.formaPago == 1}">selected</g:if>>Cheque</option>
                                <option value="2" <g:if test="${this.cobro?.formaPago == 2}">selected</g:if>>Transferencia Bancaria o Dep&oacute;sito Bancario</option>
                                <option value="3" <g:if test="${this.cobro?.formaPago == 3}">selected</g:if>>Bienes/Servicios</option>
                            </select>
                        </div>
                        <f:all bean="cobro" order="['monto']"/>
                    </fieldset>
                    <br/>
                    <fieldset class="buttons">
                        <button type="submit"  class="btn btn-outline-secondary btn-rounded ripple-surface ripple-surface-dark" onclick="return confirm('${message(code: 'default.button.update.confirm.message', default: 'Are you sure?')}');">
                            <g:message code="default.button.update.label" args="[entityName]"/>
                        </button>
                    </fieldset>
                </g:form>
            </div>
        </div>
    </div>
    </body>
</html>
