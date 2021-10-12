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
            <div class="card-header"># Buscador</div>
            <div class="card-body">
                <g:form action="index" method="post">
                    <div class="form-row">
                        <div class="col">
                            <label for="folio">Folio</label>
                            <input type="text" name="folio" id="folio" class="form-control" value="${params?.folio ? params?.folio : ''}" placeholder="Folio">
                        </div>

                        <div class="col">
                            <label for="contratoNumber">Contrato</label>
                            <g:field type="text" id="contratoNumber" name="contratoNumber" class="form-control" value="${params?.contratoNumber ? params?.contratoNumber : ''}" placeholder="Contrato"/>
                        </div>

                        <div class="col">
                            <label for="fecha">Fecha</label>
                            <g:field type="text" id="fecha" autocomplete="off" name="fecha" class="form-control" value="" placeholder="Fecha"/>
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
    <span id="linkSearchContract" style="display: none">${createLink(controller: 'contrato', action: 'searchContractAjax')}</span>
    <script>
        window.onload = function () {
        if (document.getElementById('fecha'))
            $("#fecha").datepicker({dateFormat: 'yy-mm-dd'});

        autoCompleteProducto('contratoNumber', $('#linkSearchContract').html());

        function autoCompleteProducto(iElementId, url) {
            if (!iElementId)
                return true;
            if (!document.getElementById(iElementId))
                return false;

            $("#" + iElementId).autocomplete({
                source: function (request, response) {
                    $.ajax({
                        url: url,
                        data: {frase: request.term},
                        success: function (data) {
                            response(data.list);
                        }
                    });
                },
                minLength: 1,
                select: function (event, ui) {
                    if (ui.item.id > 0) {
                        setTimeout(function () {
                            document.getElementById('contratoNumber').value = ui.item.numero;
                        }, 400);
                    } else {
                        document.getElementById('contratoNumber').value = '';
                    }
                }
            }).autocomplete("instance")._renderItem = function (ul, item) {
                if (item.id <= 0)
                    return $("<li>").append("<dl><dt>" + item.nombre + "</dt></dl>").appendTo(ul);
                if (item.id > 0)
                    return $("<li>").append("<dl><dt>" + item.numero + ": " + item.comprador.nombre + ")</dt></dl>").appendTo(ul);
            };
        };
    };
    </script>
    </body>
</html>