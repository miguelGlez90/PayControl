var listaDeLotes = [];

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
                        if(ui.item.deudaActual <= ui.item.mesualidad){
                            document.getElementById('monto').value = ui.item.deudaActual;
                        } else {
                            document.getElementById('monto').value = ui.item.mesualidad;
                        }
                    }, 400);
                } else {
                    document.getElementById('contratoNumber').value = '';
                }
            }
        }).autocomplete("instance")._renderItem = function (ul, item) {
            if (item.id <= 0)
                return $("<li>").append("<dl><dt>" + item.nombre + "</dt></dl>").appendTo(ul);
            if (item.id > 0)
                return $("<li>").append("<dl><dt>" + item.numero + ": " + item.comprador.nombre + " (" + item.costo + ")</dt></dl>").appendTo(ul);
        };
    }
    ;
};



