<g:if test="${required}">
    <g:select name="${property}" from="${type.list()}" value="${value?.id}" optionKey="id" class="form-control"/>
</g:if>
<g:else>
    <g:select name="${property}" from="${type.list()}" value="${value?.id}" optionKey="id" noSelection="[null: 'N/A']" class="form-control"/>
</g:else>