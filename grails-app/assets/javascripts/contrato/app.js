var listaDeLotes = [];

window.onload = function() {
    if(document.getElementById('fecha')) $( "#fecha" ).datepicker({ dateFormat:'yy-mm-dd' });

    function iFindLote(){
        $( "#iFindLote").autocomplete({
            source: function( request, response ) {
                $.ajax( {
                    url: $('#linkFindLote').html(),
                    data: { nombre: request.term },
                    success: function( data ) {
                        response( data.list);
                    }
                } );
            },
            minLength: 1,
            select: function( event, ui ) {
                setTimeout(function(){
                    if(ui.item.id > 0){
                        const lote = {id: ui.item.id, costo: ui.item.costo, medidas: ui.item.medidas, ubicacion: ui.item.ubicacion, identificador: ui.item.identificador};
                        addLotest(lote);
                    }
                }, 1000);
            }
        } ).autocomplete("instance")._renderItem = function (ul, item) {
            if(item.id == 0)
                return $("<li>").append("<dl><dt>" + item.identificador + "</dt></dl>").appendTo(ul);

            if(item.id != 0)
                return $("<li>").append("<dl><dt>" + item.identificador + ": " + item.ubicacion + ": " + item.costo + "</dt></dl>").appendTo(ul);
        };
    };

    iFindLote();
    initLotes();
};


function getMensualidad(){
    var costo = document.getElementById("costo").value;
    var engance = document.getElementById("enganche").value;
    var plazo = document.getElementById('plazoCompra').value;

    try{ costo = parseFloat(costo) } catch(e){ costo = 0; }
    try{ engance = parseFloat(engance) }catch (e){ engance = 0; }
    try{ plazo = parseInt(plazo) }catch (e){ plazo = 0; }

    var mensualidad = parseFloat((costo-engance)/plazo);
    if(mensualidad <= 0) mensualidad = 0;

    document.getElementById("mesualidad").value = roundAccurately(mensualidad, 2);
};

function selectLotes(){
    let options = document.getElementById('lotes').selectedOptions;
    let values = Array.from(options).map(({ value }) => value);
    let ids = [];
    let suma = 0;

    for (let i = 0; i < values.length; i++) {
        let item = values[i];
        let itemsArray = item.split(',');
        ids.push(itemsArray[0])
        suma += parseFloat(itemsArray[1])
    }

    document.getElementById('costo').value = suma;
    document.getElementById('lotesSelect').value = ids;
};

function addLotest(lote){
    var add = true;

    for (let i = 0; i < listaDeLotes.length; i++) {
        var item = listaDeLotes[i];
        if(item.id == lote.id) add = false;
    }

    if(!add) return false;

    listaDeLotes.push(lote);
    refresLotes();
};

function refresLotes(){
    renderListLotes();
    operacionesLote();
    getMensualidad();
}

function operacionesLote(){
    let sumaCosto = 0;
    let ids = [];

    for (let i = 0; i < listaDeLotes.length; i++) {
        var item = listaDeLotes[i];
        sumaCosto +=  parseFloat(item.costo);
        ids.push(item.id);
    }

    document.getElementById('costo').value = sumaCosto;
    document.getElementById('lotesSelect').value = ids;
}

function renderListLotes(){
    if(listaDeLotes.length <=0){
        document.getElementById('addLotes').innerHTML = "";
        document.getElementById("lotesJSON").value = "";
        return true
    }

    let htmlBody = '<br/><br/><table class="table table-hover table-sm">\n' +
        '  <thead>\n' +
        '    <tr>\n' +
        '      <th scope="col">Identificador</th>\n' +
        '      <th scope="col">Ubicación</th>\n' +
        '      <th scope="col">Medidas</th>\n' +
        '      <th scope="col">Costo</th>\n' +
        '      <th scope="col"></th>\n' +
        '    </tr>\n' +
        '  </thead>\n' +
        '  <tbody>';

    let sumaCosto = 0
    for (let i = 0; i < listaDeLotes.length; i++) {
        var item = listaDeLotes[i];
        sumaCosto = parseFloat(item.costo);
        htmlBody += "<tr>"
        htmlBody += "<td>"+ item.identificador +"</td>"
        htmlBody += "<td>"+ item.ubicacion +"</td>"
        htmlBody += "<td>"+ item.medidas +"</td>"
        htmlBody += "<td>"+ parseFloat(item.costo) +"</td>"
        htmlBody += '<td><button type="button" class="btn btn-danger btn-sm" onclick="quitarLote(' + item.id + ')">Quitar</button></td>'
        htmlBody += "</tr>"
    }
    htmlBody += "</tbody></table>"

    document.getElementById('addLotes').innerHTML = htmlBody;
    var myJsonString = JSON.stringify(listaDeLotes);
    document.getElementById("lotesJSON").value = myJsonString;
}

function quitarLote(id){
    let r = confirm("¿Estar seguro quitar este elemento?");

    if (r == true) {
        var listaFinal = [];

        for (let i = 0; i < listaDeLotes.length; i++) {
            var item = listaDeLotes[i];

            if(item.id.toString() != id.toString()){
                const lote = {id: item.id, costo: item.costo, medidas: item.medidas, ubicacion: item.ubicacion, identificador: item.identificador};
                listaFinal.push(lote)
            }
        }

        listaDeLotes = listaFinal;
        refresLotes();
    }
}

function initLotes(){
    if(!document.getElementById("lotesJSON").value) return true;
    let lotes = JSON.parse(document.getElementById("lotesJSON").value);
    let ids = [];

    for (let i = 0; i < lotes.length; i++) {
        var item = lotes[i];
        ids.push(item.id)
        const lote = {id: item.id, costo: item.costo, medidas: item.medidas, ubicacion: item.ubicacion, identificador: item.identificador};
        listaDeLotes.push(lote);
    }
    document.getElementById('lotesSelect').value = ids;
    refresLotes();
}

function roundAccurately(cantidad, decimales){
    var exp = Math.pow(10, decimales || 2); // 2 decimales por defecto
    return parseInt(cantidad * exp, 10) / exp;
};

