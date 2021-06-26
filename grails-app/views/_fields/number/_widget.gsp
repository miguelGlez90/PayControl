<g:if test="${required}">
    <g:field type="text" onkeypress="javascript:return toDecimal(event,this);" name="${property}" value="${value}" required="${required}" class="form-control"/>
</g:if>
<g:else>
    <g:field type="text" onkeypress="javascript:return toDecimal(event,this);" name="${property}" value="${value}" class="form-control"/>
</g:else>

<script>
    function toDecimal(evt, el) {
        var charCode = (evt.which) ? evt.which : event.keyCode;
        var number = el.value.split('.');
        if (charCode !== 46 && charCode > 31 && (charCode < 48 || charCode > 57)) {
            return false;
        }
        //just one dot (thanks ddlab)
        if (number.length > 1 && charCode === 46) {
            return false;
        }
        //get the carat position
        var caratPos = getSelectionStart(el);
        var dotPos = el.value.indexOf(".");
        if (caratPos > dotPos && dotPos > -1 && (number[1].length > 1)) {
            return false;
        }
        return true;
    }
</script>