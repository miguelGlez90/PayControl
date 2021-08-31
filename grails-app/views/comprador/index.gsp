<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'comprador.label', default: 'Comprador')}" />
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
            <div class="card-header"># Buscador</div>
            <div class="card-body">
                <g:form action="index" method="post">
                    <div class="form-row">
                        <div class="col">
                            <label for="nombre">Nombre</label>
                            <input type="text" name="nombre" id="nombre" class="form-control" value="${params?.nombre ? params?.nombre : ''}" placeholder="Nombre">
                        </div>

                        <div class="col">
                            <label for="telefono">Teléfono</label>
                            <input type="text" maxlength="10" name="telefono" id="telefono" class="form-control" value="${params?.telefono ? params?.telefono : ''}" placeholder="Teléfono">
                        </div>

                        <div class="col">
                            <label for="activo">Estatus</label>
                            <g:select name="activo" id="activo" from="${[[id: true, value: "Activo"],[id: false, value: "Inactivo"]]}" value="${params?.activo}" class="form-control" optionKey="id" optionValue="value" noSelection="[null: '-Todos-']"></g:select>
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
                    Registros encontrados: <b>${compradorCount ?: 0}</b>
                </div>
                
                <div class="table-responsive">
                    <f:table collection="${compradorList}" except="['empresa']" />

                    <div class="pagination">
                        <g:paginate total="${compradorCount ?: 0}" params="[nombre: params?.nombre, telefono: params?.telefono, activo: params?.activo]"/>
                    </div>
                </div>
            </div>
        </div>
    </div>
    </body>
</html>