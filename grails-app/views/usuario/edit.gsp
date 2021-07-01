<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'usuario.label', default: 'Usuario')}" />
        <title><g:message code="default.edit.label" args="[entityName]" /></title>
    </head>
    <body>
    <div class="card mb-4 wow fadeIn animated" style="visibility: visible; animation-name: fadeIn;">
        <div class="card-body d-sm-flex justify-content-between">
            <h4 class="mb-2 mb-sm-0 pt-1">
                Editar
                <span>/</span>
                <g:link class="list" action="index"><g:message code="default.list.label" args="[entityName]" /></g:link>
                <span>/</span>
                <g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link>
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

                <g:hasErrors bean="${this.usuario}">
                    <ul class="errors" role="alert">
                        <g:eachError bean="${this.usuario}" var="error">
                            <li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
                        </g:eachError>
                    </ul>
                </g:hasErrors>

                <g:form resource="${this.usuario}" method="PUT">
                    <g:hiddenField name="version" value="${this.usuario?.version}" />

                    <div class="form-row">
                        <div class="col">
                            <label for="nombreCompleto">Nombre completo</label>
                            <g:field type="text" id="nombreCompleto" name="nombreCompleto" class="form-control" value="${this.usuario?.nombreCompleto}" placeholder="Nombre completo" required="true" />
                        </div>
                        <div class="col">
                            <label for="username">Usuario</label>
                            <g:field type="text" id="username" name="username" class="form-control" value="${this.usuario?.username}" placeholder="username" required="true" />
                        </div>
                    </div>
                    <br/>


                    <br/><br/>
                    <h3><i class="fas fa-tasks"></i> Seleccione los Roles</h3>
                    <br/>

                    <div class="form-row">
                        <g:each in="${prod.cuernasoft.com.seguridad.Role.findAll([max: 100, sort: "authority", order: "asc", offset: 0])}" var="itRoles">
                            <div class="form-group col-md-6">
                                <label>${itRoles.authority}</label>
                                <g:if test="${this.usuario?.getAuthorities()}">
                                    <%def itemRender = false%>
                                    <g:each in="${this.usuario?.getAuthorities()}" var="itParams">
                                        <g:if test="${itParams?.id?.toString() == itRoles?.id?.toString()}">
                                            <% itemRender = true %>
                                            <g:checkBox name="iRoles" value="${itRoles?.id}" checked="true" />
                                        </g:if>
                                    </g:each>
                                    <g:if test="${!itemRender}">
                                        <g:checkBox name="iRoles" value="${itRoles?.id}" checked="false" />
                                    </g:if>
                                </g:if>
                                <g:else>
                                    <g:checkBox name="iRoles" value="${itRoles?.id}" checked="false" />
                                </g:else>
                            </div>
                        </g:each>
                    </div>
                    <br/><br/>
                    <input class="btn btn-outline-primary" type="submit" value="${message(code: 'default.button.update.label', default: 'Update')}" />
                    <g:link class="btn btn-outline-primary" action="editPassword" resource="${this.usuario}">Cambiar Constraseña</g:link>
                </g:form>
            </div>
        </div>
    </div>
    </body>
</html>
