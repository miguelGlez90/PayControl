<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'contrato.label', default: 'Contrato')}" />
        <title><g:message code="default.show.label" args="[entityName]" /></title>
    </head>
    <body>


    <div class="card mb-4 wow fadeIn animated" style="visibility: visible; animation-name: fadeIn;">
        <div class="card-body d-sm-flex justify-content-between">
            <h4 class="mb-2 mb-sm-0 pt-1">
                <g:link class="list" action="index"><i class="fas fa-th-large"></i> <g:message code="default.list.label" args="[entityName]" /></g:link>
                <span>/</span>
                <g:link class="create" action="create"><i class="fas fa-plus"></i> <g:message code="default.new.label" args="[entityName]" /></g:link>
            </h4>
        </div>
    </div>


    <div class="col-lg-12 col-md-12 mb-12">
        <div class="card">
            <div class="card-header"><g:message code="default.show.label" args="[entityName]"/></div>
            <div class="card-body">

                <g:if test="${flash.message}">
                    <div class="alert alert-info" role="alert">${flash.message}</div>
                </g:if>

                <f:display bean="contrato" except="['empresa','deudaActual']"/>
                <br/>

                <dl class="row">
                    <dt class="col-sm-3">Número de Pagos</dt>
                    <dd class="col-sm-9"> <b><span class="badge badge-pill badge-success">${countCobrado}</span></b> </dd>
                    <dt class="col-sm-3">Total Pagado</dt>
                    <dd class="col-sm-9"> <b><span class="badge badge-pill badge-info">$<g:formatNumber number="${montoCobrado}" format='###,###,##0.00' /></span></b> </dd>
                    <dt class="col-sm-3">Saldo Actual</dt>
                    <dd class="col-sm-9"> <b><span class="badge badge-pill badge-warning">$<g:formatNumber number="${contrato?.deudaActual}" format='###,###,##0.00' /></span></b> </dd>
                </dl>
                <br/>
                <g:form resource="${this.contrato}" method="DELETE">
                    <fieldset class="buttons">
                        <g:link class="btn btn-outline-primary ripple-surface ripple-surface-dark" action="edit" resource="${this.contrato}"><g:message code="default.button.edit.label" default="Edit" /></g:link>
                    </fieldset>
                </g:form>

            </div>
        </div>
    </div>
    </body>
</html>
