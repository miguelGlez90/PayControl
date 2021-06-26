<g:if test="${required}">
    <g:select name="${property}" from="${association.javaClass.list()}" value="${value}" class="form-control"/>
</g:if>
<g:else>
    <g:select name="${property}" from="${association.javaClass.list()}" value="${value}" noSelection="[null: '']" class="form-control"/>
</g:else>