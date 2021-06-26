<div class="input-group date">
    <g:if test="${required}">
        <g:textField name="${property}" id="datepicker-${property}" value="${value?.format('yyyy-MM-dd')}" required="required" class="form-control date-picker"/>
    </g:if>
    <g:else>
        <g:textField name="${property}" id="datepicker-${property}" value="${value?.format('yyyy-MM-dd')}" class="form-control date-picker"/>
    </g:else>
    <span class="input-group-addon">
        <i class="fa fa-time"></i>
    </span>
</div>
<script>
    window.onload = function() {
        $( "#datepicker-${property}" ).datepicker({ dateFormat:'yy-mm-dd' });
    };
</script>