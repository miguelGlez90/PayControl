<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'usuario.label', default: 'Usuario')}" />
        <title><g:message code="default.list.label" args="[entityName]" /></title>
    </head>
    <body>


    <div class="card mb-4 wow fadeIn animated" style="visibility: visible; animation-name: fadeIn;">
        <div class="card-body d-sm-flex justify-content-between">
            <h4 class="mb-2 mb-sm-0 pt-1">
                <g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link>
                <span>/</span>
                <span><g:message code="default.list.label" args="[entityName]" /></span>
            </h4>
        </div>
    </div>

    <div class="col-lg-12 col-md-12 mb-12">
        <div class="card">
            <div class="card-header"># Buscador</div>
            <div class="card-body">
                <g:form action="index" method="post">
                    <div class="form-row">
                        <div class="col">
                            <label for="inputNombreCompleto">Nombre</label>
                            <input type="text" name="nombreCompleto" id="inputNombreCompleto" class="form-control" value="${params?.nombreCompleto ? params?.nombreCompleto : ''}" placeholder="Nombre">
                        </div>

                        <div class="col">
                            <label for="inputEnabled">Estatus</label>
                            <g:select name="enabled" id="inputEnabled" from="${[[id: true, value: "Activo"],[id: false, value: "Inactivo"]]}" value="${params?.enabled}" class="form-control" optionKey="id" optionValue="value" noSelection="[null: '-Todos-']"></g:select>
                        </div>

                    </div>
                    <br/>
                    <button type="submit" class="btn btn-primary">Buscar</button>
                </g:form>
            </div>
        </div>
    </div>
    <br/><br/>


    <div class="col-lg-12 col-md-12 mb-12">
        <div class="card">
            <div class="card-header"><g:message code="default.list.label" args="[entityName]"/></div>
            <div class="card-body">

                <g:if test="${flash.message}">
                    <div class="alert alert-info" role="alert">${flash.message}</div>
                </g:if>

                <div class="alert alert-dark" role="alert">
                    Registros encontrados: <b>${usuarioCount ?: 0}</b>
                </div>
                
                <div class="table-responsive">
                    <f:table collection="${usuarioList}" except="['password', 'accountExpired','accountLocked','passwordExpired','empresa']" />

                    <div class="pagination">
                        <g:paginate total="${usuarioCount ?: 0}" params="[nombreCompleto: params?.nombreCompleto, enabled: params?.enabled]"/>
                    </div>
                </div>
            </div>
        </div>
    </div>
    </body>
</html>