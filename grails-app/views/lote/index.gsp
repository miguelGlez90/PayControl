<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'lote.label', default: 'Lote')}" />
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
                            <label for="identificador">Identificador</label>
                            <input type="text" name="identificador" id="identificador" class="form-control" value="${params?.identificador ? params?.identificador : ''}" placeholder="identificador">
                        </div>

                        <div class="col">
                            <label for="ubicacion">Ubicación</label>
                            <input type="text" name="ubicacion" id="ubicacion" class="form-control" value="${params?.ubicacion ? params?.ubicacion : ''}" placeholder="Ubicación">
                        </div>

                        <div class="col">
                            <label for="vendido">Vendido</label>
                            <g:select name="vendido" id="vendido" from="${[[id: true, value: "SI"],[id: false, value: "NO"]]}" optionKey="${{it.id}}" optionValue="${{it.value}}" value="${params?.vendido}" noSelection="[null: '-Todos-']" class="form-control"/>
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
                    Registros encontrados: <b>${loteCount ?: 0}</b>
                </div>
                
                <div class="table-responsive">
                    <f:table collection="${loteList}" />

                    <div class="pagination">
                        <g:paginate total="${loteCount ?: 0}" params="[identificador: params?.identificador, ubicacion: params?.ubicacion, vendido: params?.vendido]"/>
                    </div>
                </div>
            </div>
        </div>
    </div>
    </body>
</html>