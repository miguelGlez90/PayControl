<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'usuario.label', default: 'Usuario')}" />
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

                <f:display bean="usuario" except="['password','accountExpired','accountLocked','passwordExpired','empresa']"/>
                <br/>
                <p class="h3"><i class="fas fa-tags"></i> Roles</p>
                <br/>
                <dl class="row">
                    <g:each in="${usuario?.getAuthorities()}" status="count" var="it">
                        <dt class="col-sm-3">${count+1}.</dt>
                        <dd class="col-sm-9"><span class="badge badge-info">${it?.authority}</span></dd>
                    </g:each>
                </dl>

                <br/><br/>
                <g:form resource="${this.usuario}" method="DELETE">
                    <fieldset class="buttons">
                        <g:link class="btn btn-outline-primary ripple-surface ripple-surface-dark" action="edit" resource="${this.usuario}"><g:message code="default.button.edit.label" default="Edit" /></g:link>

                        <button type="submit"  class="btn btn-outline-danger ripple-surface ripple-surface-dark" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');">
                            <g:message code="default.button.delete.label" args="[entityName]"/>
                        </button>
                    </fieldset>
                </g:form>

            </div>
        </div>
    </div>
    </body>
</html>
