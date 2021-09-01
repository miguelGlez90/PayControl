<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'contrato.label', default: 'Contrato')}" />
        <title><g:message code="default.list.label" args="[entityName]" /></title>
    </head>
    <body>


    <div class="card mb-4 wow fadeIn animated" style="visibility: visible; animation-name: fadeIn;">
        <div class="card-body d-sm-flex justify-content-between">
            <h4 class="mb-2 mb-sm-0 pt-1">
                <g:link class="create" action="create"><i class="fas fa-plus"></i> <g:message code="default.new.label" args="[entityName]" /></g:link>
                <span>/</span>
                <span><i class="fas fa-th-large"></i> <g:message code="default.list.label" args="[entityName]" /></span>
            </h4>
        </div>
    </div>


    <div class="col-lg-12 col-md-12 mb-12">
        <div class="card">
            <div class="card-header"><g:message code="default.list.label" args="[entityName]"/></div>
            <div class="card-body">

                <g:if test="${flash.message}">
                    <div class="alert alert-info" role="alert">${flash.message}</div>
                </g:if>

                <div class="alert alert-dark" role="alert">
                    Registros encontrados: <b>${contratoCount ?: 0}</b>
                </div>
                
                <div class="table-responsive">
                    <f:table collection="${contratoList}" except="['empresa']"/>

                    <div class="pagination">
                        <g:paginate total="${contratoCount ?: 0}" />
                    </div>
                </div>
            </div>
        </div>
    </div>
    </body>
</html>