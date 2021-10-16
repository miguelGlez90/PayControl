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
            <div class="card-header"># Buscador</div>
            <div class="card-body">
                <g:form action="index" method="post">
                    <div class="form-row">
                        <div class="col">
                            <label for="numero">Número</label>
                            <input type="text" name="numero" id="numero" class="form-control" value="${params?.numero ? params?.numero : ''}" placeholder="Número" autocomplete="nope">
                        </div>

                        <div class="col">
                            <label for="vendedor">Vendedor</label>
                            <input type="text" name="vendedor" id="vendedor" class="form-control" value="${params?.vendedor ? params?.vendedor : ''}" placeholder="Vendedor" autocomplete="nope">
                        </div>

                        <div class="col">
                            <label for="comprador">Comprador</label>
                            <input type="text" name="comprador" id="comprador" class="form-control" value="${params?.comprador ? params?.comprador : ''}" placeholder="Comprador" autocomplete="nope">
                        </div>

                    </div>

                    <div class="form-row">
                        <div class="col-4">
                            <label for="abierto">Estatus</label>
                            <g:select name="abierto" id="abierto" from="${[[id: true, value: "Abierto"],[id: false, value: "Cerrado"]]}" optionKey="${{it.id}}" optionValue="${{it.value}}" value="${params?.abierto}" noSelection="[null: '-Todos-']" class="form-control"/>
                        </div>
                        <div class="col-4">
                            <label for="cancelado">Cancelado</label>
                            <g:select name="cancelado" id="cancelado" from="${[[id: true, value: "SI"],[id: false, value: "NO"]]}" optionKey="${{it.id}}" optionValue="${{it.value}}" value="${params?.cancelado}" noSelection="[null: '-Todos-']" class="form-control"/>
                        </div>
                        <div class="col-4">
                            <button type="submit" class="btn btn-primary">Buscar</button>
                        </div>
                    </div>
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
                    Registros encontrados: <b>${contratoCount ?: 0}</b>
                </div>
                
                <div class="table-responsive">

                    <table class="table table-hover table-sm table-striped">
                        <thead>
                        <tr>
                            <g:sortableColumn property="id" title="id" />
                            <g:sortableColumn property="numero" title="Número" />
                            <g:sortableColumn property="fecha" title="Fecha Registrado" />
                            <g:sortableColumn property="vendedor" title="Vendedor" />
                            <g:sortableColumn property="comprador" title="Comprador" />
                            <g:sortableColumn property="abierto" title="Estatus" />
                            <g:sortableColumn property="costo" title="Costo" />
                            <g:sortableColumn property="enganche" title="Enganche" />
                            <g:sortableColumn property="deudaActual" title="Deuda Actual" />
                            <th scope="col">&nbsp;</th>
                            <th scope="col">&nbsp;</th>
                            <th scope="col">&nbsp;</th>
                            <th scope="col">&nbsp;</th>
                        </tr>
                        </thead>
                        <tbody>
                        <tr>
                        <g:each in="${contratoList}" var="contrtado" status="i">
                            <tr>
                                <td>${contrtado?.id}</td>
                                <td>${contrtado?.numero}</td>
                                <td><g:formatDate format="dd/MM/yyyy" date="${contrtado?.fecha}"/></td>
                                <td>${contrtado?.vendedor}</td>
                                <td>${contrtado?.comprador}</td>
                                <td>
                                    <g:if test="${contrtado?.abierto}"><span class='badge badge-success'><b>ABIERTO: SI</b></span></g:if>
                                    <g:else><span class='badge badge-info'><b>ABIERTO: NO</b></span></g:else>

                                    <g:if test="${contrtado?.cancelado}"><span class='badge badge-danger'><b>CANCELADO: SI</b></span></g:if>
                                    <g:else><span class='badge badge-info'><b>CANCELADO: NO</b></span></g:else>
                                </td>
                                <td>$<g:formatNumber number="${contrtado?.costo}" format="###,###,###,###.00" /></td>
                                <td>$<g:formatNumber number="${contrtado?.enganche}" format="###,###,###,###.00" /></td>
                                <td>$<g:formatNumber number="${contrtado?.deudaActual}" format="###,###,###,###.00" /></td>

                                <td>
                                    <g:form resource="${contrtado}" action="edit" method="POST">
                                        <button type="submit" class="btn btn-outline-secondary btn-rounded ripple-surface ripple-surface-dark btn-sm">
                                            EDITAR
                                        </button>
                                    </g:form>
                                </td>

                                <td>
                                    <g:form resource="${contrtado}" method="DELETE">
                                        <g:hiddenField name="offset" value="${params?.offset}" />
                                        <g:hiddenField name="max" value="${params?.max}" />
                                        <g:hiddenField name="numero" value="${params?.numero}" />
                                        <g:hiddenField name="vendedor" value="${params?.vendedor}" />
                                        <g:hiddenField name="comprador" value="${params?.comprador}" />
                                        <g:hiddenField name="abierto" value="${params?.abierto}" />
                                        <g:hiddenField name="cancelado" value="${params?.cancelado}" />

                                        <button type="submit" onclick="return confirm('¿Desea borrar el cobro seleccionado?');" class="btn btn-outline-danger btn-rounded ripple-surface ripple-surface-dark btn-sm">
                                            BORRAR
                                        </button>
                                    </g:form>
                                </td>
                                <td>
                                    <g:form resource="${contrtado}" action="show" method="POST">
                                        <button type="submit" class="btn btn-outline-info btn-rounded ripple-surface ripple-surface-dark btn-sm">
                                            VER DETALLE
                                        </button>
                                    </g:form>
                                </td>

                                <g:if test="${!contrtado?.cancelado}">
                                    <td>
                                        <g:form resource="${contrtado}" action="cancelar" method="DELETE">
                                            <button type="submit" onclick="return confirm('¿Desea Cancelar este contrato? Al momento de cancelarlo se liberaran los LOTES asignados a este contrato');" class="btn btn-outline-warning btn-rounded ripple-surface ripple-surface-dark btn-sm">
                                                CANCELAR
                                            </button>
                                        </g:form>
                                    </td>
                                </g:if>
                                <g:else> <td> </td> </g:else>
                            </tr>
                        </g:each>
                        </tr>
                        </tbody>
                    </table>

                    <div class="pagination">
                        <g:paginate total="${contratoCount ?: 0}" params="[numero: params?.numero, vendedor: params?.vendedor, comprador: params?.comprador, abierto: params?.abierto, cancelado: params?.cancelado]"/>
                    </div>
                </div>
            </div>
        </div>
    </div>
    </body>
</html>