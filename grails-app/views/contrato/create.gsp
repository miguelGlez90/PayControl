<!DOCTYPE html>
<html>
    <head>
        <meta name="layout" content="main" />
        <g:set var="entityName" value="${message(code: 'contrato.label', default: 'Contrato')}" />
        <title><g:message code="default.create.label" args="[entityName]" /></title>
    </head>
    <body>


    <div class="card mb-4 wow fadeIn animated" style="visibility: visible; animation-name: fadeIn;">
        <div class="card-body d-sm-flex justify-content-between">
            <h4 class="mb-2 mb-sm-0 pt-1">
                <g:link class="list" action="index"><i class="fas fa-th-large"></i> <g:message code="default.list.label" args="[entityName]" /></g:link>
                <span>/</span>
                <span><g:message code="default.create.label" args="[entityName]" /></span>
            </h4>
        </div>
    </div>


    <div class="col-lg-12 col-md-12 mb-12">
        <div class="card">
            <div class="card-header"><g:message code="default.create.label" args="[entityName]"/></div>
            <div class="card-body">

                <g:if test="${flash.message}">
                    <div class="alert alert-info" role="alert">${flash.message}</div>
                </g:if>

                <g:hasErrors bean="${this.contrato}">
                    <div class="alert alert-danger d-flex align-items-center" role="alert">
                        <ul class="errors" role="alert">
                            <g:eachError bean="${this.contrato}" var="error">
                                <li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
                            </g:eachError>
                        </ul>
                    </div>
                </g:hasErrors>



                <g:form resource="${this.contrato}" method="POST">
                    <input type="hidden" name="empresa" value="${empresaInstance?.id}">
                    <input type="hidden" name="iLotes" id="lotesSelect" value="${params?.iLotes}">
                    <input type="hidden" name="lotesJSON" id="lotesJSON" value="${params?.lotesJSON}">
                    <fieldset class="form">
                        <div class="form-row">
                            <div class="col-6">
                                <label for="fecha">Fecha *</label>
                                <g:field type="text" id="fecha" autocomplete="off" name="fecha" class="form-control" value="${this.contrato?.fecha?.format('yyyy-MM-dd')}" placeholder="Fecha" required="true" />
                            </div>

                            <div class="col">
                                <label for="numero">Número *</label>
                                <g:field type="text" id="numero" autocomplete="off" name="numero" class="form-control" value="${this.contrato?.numero}" placeholder="Número" required="true" />
                            </div>

                        </div>


                        <div class="form-row">
                            <div class="col">
                                <label for="vendedor">Vendedor *</label>
                                <g:select name="vendedor" class="form-control" from="${prod.cuernasoft.com.catalogos.Vendedor.findAllByActivoAndEmpresa(true, empresaInstance)}" optionKey="id" value="${this.contrato?.vendedor}"/>
                            </div>

                            <div class="col">
                                <label for="comprador">Comprador *</label>
                                <g:select name="comprador" class="form-control" from="${prod.cuernasoft.com.catalogos.Comprador.findAllByActivoAndEmpresa(true, empresaInstance)}" optionKey="id" value="${this.contrato?.comprador}"/>
                            </div>
                        </div>

                        <br/><br/>
                        <div class="form-row">
                            <div class="col">
                                <label for="costo">Buscar Lote...</label>
                                <g:field type="text" id="iFindLote" autocomplete="off" name="iFindLote" class="form-control"  placeholder="Buscar lote..." />
                            </div>
                        </div>

                        <spand id="addLotes"></spand>
                        <br/><br/>


                        <div class="form-row">
                            <div class="col">
                                <label for="costo">Costo *</label>
                                <g:field type="number" onchange="getMensualidad()" id="costo" autocomplete="off" name="costo" class="form-control" value="${this.contrato?.costo}" placeholder="Costo" required="true" />
                            </div>
                            <div class="col">
                                <label for="enganche">Enganche *</label>
                                <g:field type="number" onchange="getMensualidad()" id="enganche" autocomplete="off" name="enganche" class="form-control" value="${this.contrato?.enganche}" placeholder="Enganche" required="true" />
                            </div>
                        </div>

                        <div class="form-row">
                            <div class="col">
                                <label for="plazoCompra">Plazo de Compra *</label>
                                <g:field type="number" onchange="getMensualidad()" id="plazoCompra" autocomplete="off" name="plazoCompra" class="form-control" value="${this.contrato?.plazoCompra}" placeholder="Plazo de Compra" required="true" />
                            </div>
                            <div class="col">
                                <label for="mesualidad">Mesualidad *</label>
                                <g:field type="text" id="mesualidad" autocomplete="off" name="mesualidad" class="form-control" value="${this.contrato?.mesualidad}" placeholder="Mesualidad" required="true" />
                            </div>
                        </div>

                        <div class="form-row">
                            <div class="col-6">
                                <label for="plazoCompra">Día de Pago *</label>
                                <g:field type="number" id="diaPago" autocomplete="off" name="diaPago" class="form-control" value="${this.contrato?.diaPago}" placeholder="Día de Pago" required="true" />
                            </div>
                        </div>

                        <br/><br/>

                    </fieldset>
                    <br/>
                    <fieldset class="buttons">
                        <button type="submit"  class="btn btn-outline-secondary btn-rounded ripple-surface ripple-surface-dark">
                            <g:message code="default.button.create.label" args="[entityName]"/>
                        </button>
                    </fieldset>
                </g:form>
            </div>
        </div>
    </div>
    <span id="linkFindLote" style="display: none">${createLink(controller: 'contrato', action: 'findLote')}</span>


    <asset:javascript src="contrato/app.js"></asset:javascript>
    </body>
</html>
