<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'vendedor.label', default: 'Vendedor')}" />
        <title><g:message code="default.edit.label" args="[entityName]" /></title>
    </head>
    <body>
        <div class="card mb-4 wow fadeIn animated" style="visibility: visible; animation-name: fadeIn;">
            <div class="card-body d-sm-flex justify-content-between">
                <h4 class="mb-2 mb-sm-0 pt-1">
                    <g:link class="list" action="index"><i class="fas fa-th-large"></i> Lista de Vendedores</g:link>
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

                    <g:hasErrors bean="${this.vendedor}">
                        <ul class="errors" role="alert">
                            <g:eachError bean="${this.vendedor}" var="error">
                                <li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
                                </g:eachError>
                        </ul>
                    </g:hasErrors>

                    <g:form resource="${this.vendedor}" method="PUT">
                        <g:hiddenField name="version" value="${this.vendedor?.version}" />

                        <fieldset class="form">
                            <f:all bean="vendedor" order="['nombre']"/>
                            <div class="form-group col-md-6">
                                <label for="rfc">RFC</label>
                                <g:field type="text" id="rfc" maxlength="13" minlength="13" name="rfc" class="form-control" value="${this.vendedor?.rfc}" placeholder="Nombre completo" required="true" />
                            </div>
                            <f:all bean="vendedor" order="['domicilioFiscal', 'regimenFiscal', 'activo']"/>
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
