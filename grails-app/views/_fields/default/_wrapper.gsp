<div class="form-group col-md-6">
    <label for="${property}">${label} ${required ? '*' : ''}</label>

    <f:widget class="form-control" property="${property}"/>
    <g:if test="${errors}">
        <g:each in="${errors}" var="error">
            <span style="color: red"><i class="fas fa-exclamation-triangle text-danger"></i> <g:message error="${error}"/></span>
        </g:each>
    </g:if>
</div>
