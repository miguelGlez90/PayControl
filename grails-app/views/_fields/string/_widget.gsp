<g:set var="validaciones">
    <input type="text" name="${property}" value="${value}"  class="form-control"
        <g:if test="${required}"> required="${required}"</g:if>
        <g:if test="${constraints?.maxSize}"> maxlength="${constraints?.maxSize}"</g:if>
        <g:if test="${constraints?.matches}"> pattern="${constraints?.matches}"</g:if>
    >
</g:set>

${raw(validaciones)}
