<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'role.label', default: 'Role')}" />
        <title><g:message code="default.show.label" args="[entityName]" /></title>
    </head>
    <body>


    <div class="card mb-4 wow fadeIn animated" style="visibility: visible; animation-name: fadeIn;">
        <div class="card-body d-sm-flex justify-content-between">
            <h4 class="mb-2 mb-sm-0 pt-1">
                <g:link class="list" action="index"><g:message code="default.list.label" args="[entityName]" /></g:link>
                <span>/</span>
                <g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link>
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

                <f:display bean="role" />
                <g:form resource="${this.role}" method="DELETE">
                    <fieldset class="buttons">
                        <g:link class="btn btn-outline-primary ripple-surface ripple-surface-dark" action="edit" resource="${this.role}"><g:message code="default.button.edit.label" default="Edit" /></g:link>

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
