<div class="table-responsive">
    <table class="table table-hover table-sm table-striped">
        <thead>
        <tr>
            <g:each in="${domainProperties}" var="p" status="i">
                <g:sortableColumn property="${p.property}" title="${p.label}" > ${params?.order}</g:sortableColumn>
            </g:each>
            <th scope="col">&nbsp;</th>
            <th scope="col">&nbsp;</th>
            <th scope="col">&nbsp;</th>
        </tr>
        </thead>
        <tbody>
        <tr>
        <g:each in="${collection}" var="bean" status="i">
            <tr>
            <g:each in="${domainProperties}" var="p" status="j">
                <td>
                    <f:display bean="${bean}"
                               property="${p.name}"
                               displayStyle="${displayStyle?:'table'}"/>
                </td>
                </td>
            </g:each>

            <td>
                <a title="Editar" class="btn btn-outline-secondary btn-rounded ripple-surface ripple-surface-dark btn-sm" href="${createLink(action: 'edit', params: [id: bean?.id])}" role="button">EDITAR</a>
            </td>

            <td>
                <g:form resource="${bean}" method="DELETE">
                    <button type="submit" onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');" class="btn btn-outline-danger btn-rounded ripple-surface ripple-surface-dark btn-sm">
                        QUITAR
                    </button>
                </g:form>
            </td>

            <td>
                <a title="Ver Detalle" class="btn btn-outline-info btn-rounded ripple-surface ripple-surface-dark btn-sm" href="${createLink(action: 'show', params: [id: bean?.id])}" role="button">DETALLE</a>
            </td>
            </tr>
        </g:each>
        </tr>
        </tbody>
    </table>
</div>