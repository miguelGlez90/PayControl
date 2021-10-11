<!doctype html>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>Bienvenido</title>

</head>

<g:set var="resumenc" bean="prod.cuernasoft.com.admin.ResumenController"/>

<body class="grey lighten-3">


<div class="card mb-4 wow fadeIn" style="visibility: visible; animation-name: fadeIn;">

    <!--Card content-->
    <div class="card-body d-sm-flex justify-content-between">

        <h4 class="mb-2 mb-sm-0 pt-1">
            <a href="javascript: void(0)" target="_blank">Bienvenido</a>
            <span>/</span>
            <span>Dashboard</span>
        </h4>
    </div>

</div>

<!--Grid row-->
<div class="row wow fadeIn">

    <!--Grid column-->
    <div class="col-md-9 mb-4">

        <!--Card-->
        <div class="card">

            <!--Card content-->
            <div class="card-body">

                <canvas id="myChart"></canvas>

            </div>

        </div>
        <!--/.Card-->

    </div>
    <!--Grid column-->

    <!--Grid column-->
    <div class="col-md-3 mb-4">

        <!--Card-->
        <div class="card mb-4">
            <div class="card-header text-center"> Resumen Lotes</div>

            <!--Card content-->
            <div class="card-body"> <canvas id="pieChart"></canvas> </div>

        </div>

        <div class="card mb-4">

            <!--Card content-->
            <div class="card-body">

                <!-- List group links -->
                <div class="list-group list-group-flush">
                    <a class="list-group-item list-group-item-action waves-effect">Lotes Vendidos
                        <span class="badge badge-success badge-pill pull-right"><spand id="iLotesVendidosTotal"></spand>
                            <i class="fas fa-arrow-up ml-1"></i>
                        </span>
                    </a>
                    <a class="list-group-item list-group-item-action waves-effect">Lotes Pendientes
                        <span class="badge badge-danger badge-pill pull-right"><spand id="iLotesPendientesTotal"></spand>
                            <i class="fas fa-arrow-down ml-1"></i>
                        </span>
                    </a>
                    <a class="list-group-item list-group-item-action waves-effect">Contratos Cerrados
                        <span class="badge badge-success badge-pill pull-right"><spand id="iContratosCerradosTotal"></spand>
                            <i class="fas fa-arrow-up ml-1"></i>
                        </span>
                    </a>
                    <a class="list-group-item list-group-item-action waves-effect">Contratos Abiertos
                        <span class="badge badge-danger badge-pill pull-right"><spand id="iContratosPendientesTotal"></spand>
                            <i class="fas fa-arrow-down ml-1"></i>
                        </span>
                    </a>
                </div>
                <!-- List group links -->

            </div>

        </div>
        <!--/.Card-->

    <input type="hidden" name="iResumenAll" id="iResumenAll" value="${resumenc.getResumenAll()}">
    <input type="hidden" name="iResumenLotes" id="iResumenLotes" value="${resumenc.getResumenLotes()}">
    </div>
    <!--Grid column-->

</div>
<!--Grid row-->
<script type="application/javascript">
    function initScript(){
        var items = document.getElementById("iResumenAll").value;
        items = JSON.parse(items);
        let listado = [items.lotesVendiso, items.lotesPendientes, items.contratosAbiertos, items.contratosCerrados];

        initChat(listado);
        initPie();
        resumenFinal(listado);
    };

    function initChat(listado){
        var ctx = document.getElementById("myChart").getContext('2d');
        var myChart = new Chart(ctx, {
            type: 'bar',
            data: {
                labels: ["Lotes Vendidos", "Lotes Pendientes", "Contratos abiertos", "Contratos Cerrados"],
                datasets: [{
                    label: '# Resumen',
                    data: listado,
                    backgroundColor: [
                        'rgba(255, 99, 132, 0.2)',
                        'rgba(54, 162, 235, 0.2)',
                        'rgba(255, 206, 86, 0.2)',
                        'rgba(75, 192, 192, 0.2)',
                    ],
                    borderColor: [
                        'rgba(255,99,132,1)',
                        'rgba(54, 162, 235, 1)',
                        'rgba(255, 206, 86, 1)',
                        'rgba(75, 192, 192, 1)'
                    ],
                    borderWidth: 1
                }]
            },
            options: {
                scales: {
                    yAxes: [{
                        ticks: {
                            beginAtZero: true
                        }
                    }]
                }
            }
        });
    }

    function initPie(){
        var items = document.getElementById("iResumenLotes").value;
        items = JSON.parse(items);
        console.log(items);
        let listado = [items.pendientes, items.vendidos];

        var ctxP = document.getElementById("pieChart").getContext('2d');
        var myPieChart = new Chart(ctxP, {
            type: 'pie',
            data: {
                labels: ["Pendientes", "Vendidos"],
                datasets: [{
                    data: listado,
                    backgroundColor: ["#F7464A", "#46BFBD"],
                    hoverBackgroundColor: ["#FF5A5E", "#5AD3D1"]
                }]
            },
            options: {
                responsive: true,
                legend: false
            }
        });
    }

    function resumenFinal(listado){
        //let listado = [items.lotesVendiso, items.lotesPendientes, items.contratosAbiertos, items.contratosCerrados];
        document.getElementById("iLotesVendidosTotal").innerHTML = listado[0];
        document.getElementById("iLotesPendientesTotal").innerHTML = listado[1];

        document.getElementById("iContratosCerradosTotal").innerHTML = listado[3];
        document.getElementById("iContratosPendientesTotal").innerHTML = listado[2];
    }

    window.onload = initScript;





</script>
</body>
</html>
