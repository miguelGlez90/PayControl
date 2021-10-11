<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'cobro.label', default: 'Cobro')}" />
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
                    Registros encontrados: <b>${cobroCount ?: 0}</b>
                </div>
                
                <div class="table-responsive">
                    <!-- f:table collection="${cobroList}" order="['folio', 'contrato', 'monto', 'fecha', 'cancelado', 'creadoPor']"/ -->
                    
                    <table class="table table-hover table-sm table-striped">
                        <thead>
                        <tr>
                            <g:sortableColumn property="folio" title="Folio" />
                            <g:sortableColumn property="contrato" title="Contrato" />
                            <g:sortableColumn property="monto" title="Monto" />
                            <g:sortableColumn property="fecha" title="Fecha" />
                            <g:sortableColumn property="creadoPor" title="Creado Por" />
                            <th scope="col">&nbsp;</th>
                            <!--th scope="col">&nbsp;</th-->
                            <th scope="col">&nbsp;</th>
                        </tr>
                        </thead>
                        <tbody>
                        <tr>
                        <g:each in="${cobroList}" var="cobro" status="i">
                            <tr>
                            <td>${cobro?.folio}</td>
                            <td>${cobro?.contrato}</td>
                            <td>${cobro?.monto}</td>
                            <td><g:formatDate format="dd/MM/yyyy" date="${cobro?.fecha}"/></td>
                            <td>${cobro?.creadoPor}</td>

                            <td>
                            <g:form resource="${cobro}" action="edit" method="POST">
                                <button type="submit" class="btn btn-outline-secondary btn-rounded ripple-surface ripple-surface-dark btn-sm">
                                    EDITAR
                                </button>
                            </g:form>
                            </td>

                            <td>
                                <g:form resource="${cobro}" method="DELETE">
                                    <button type="submit" onclick="return confirm('¿Desea borrar el cobro seleccionado?');" class="btn btn-outline-danger btn-rounded ripple-surface ripple-surface-dark btn-sm">
                                        BORRAR
                                    </button>
                                </g:form>
                            </td>

                            <td>
                                <g:form resource="${cobro}" action="show" method="POST">
                                    <button type="submit" class="btn btn-outline-info btn-rounded ripple-surface ripple-surface-dark btn-sm">
                                        VER DETALLE
                                    </button>
                                </g:form>
                            </td>
                            </tr>
                        </g:each>
                        </tr>
                        </tbody>
                    </table>

                    <div class="pagination">
                        <g:paginate total="${cobroCount ?: 0}" />
                    </div>
                </div>
            </div>
        </div>
    </div>
    </body>
</html>