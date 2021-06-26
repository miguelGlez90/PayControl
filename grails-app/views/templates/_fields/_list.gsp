<dl class="row">
    <g:each in="${domainProperties}" var="p">
        <dt class="col-sm-3"><g:message code="${domainClass.decapitalizedName}.${p.name}.label" default="${p.defaultLabel}" /></dt>
        <dd class="col-sm-9">${body(p)}</dd>
    </g:each>
</dl>