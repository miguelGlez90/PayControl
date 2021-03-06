<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main" />
    <g:set var="entityName" value="${message(code: 'usuario.label', default: 'Usuario')}" />
    <title>Cambiar password</title>
</head>
<body>

<div class="card mb-4 wow fadeIn animated" style="visibility: visible; animation-name: fadeIn;">
    <div class="card-body d-sm-flex justify-content-between">
        <h4 class="mb-2 mb-sm-0 pt-1">
            Actualizar
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

            <g:form resource="${this.usuario}" method="PUT" action="updatePasswordUsuario">
                <g:hiddenField name="version" value="${this.usuario?.version}" />
                <div class="form-row">
                    <div class="col">
                        <label for="password">Contraseña Nueva</label>
                        <g:field type="password" id="password" name="password" class="form-control" placeholder="Contraseña nueva" required="true" />
                    </div>
                    <div class="col">
                        <label for="passwordR">Repite contraseña Nueva</label>
                        <g:field type="password" id="passwordR" name="passwordR" class="form-control"  placeholder="Repite contraseña nueva" required="true" />
                    </div>
                </div>
                <br/>
                <input class="btn btn-outline-primary" type="submit" value="${message(code: 'default.button.update.label', default: 'Update')}" />
            </g:form>
        </div>
    </div>
</div>

</body>
</html>
