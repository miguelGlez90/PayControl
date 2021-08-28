<!doctype html>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>Bienvenido</title>

</head>
<body class="grey lighten-3">


<div class="card mb-4 wow fadeIn" style="visibility: visible; animation-name: fadeIn;">

    <!--Card content-->
    <div class="card-body d-sm-flex justify-content-between">

        <h4 class="mb-2 mb-sm-0 pt-1">
            <a href="javascript: void(0)" target="_blank">Bienvedido</a>
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

            <!-- Card header -->
            <div class="card-header text-center">
                Pie chart
            </div>

            <!--Card content-->
            <div class="card-body">

                <canvas id="pieChart"></canvas>

            </div>

        </div>
        <!--/.Card-->

        <!--Card-->
        <div class="card mb-4">

            <!--Card content-->
            <div class="card-body">

                <!-- List group links -->
                <div class="list-group list-group-flush">
                    <a class="list-group-item list-group-item-action waves-effect">Sales
                        <span class="badge badge-success badge-pill pull-right">22%
                            <i class="fas fa-arrow-up ml-1"></i>
                        </span>
                    </a>
                    <a class="list-group-item list-group-item-action waves-effect">Traffic
                        <span class="badge badge-danger badge-pill pull-right">5%
                            <i class="fas fa-arrow-down ml-1"></i>
                        </span>
                    </a>
                    <a class="list-group-item list-group-item-action waves-effect">Orders
                        <span class="badge badge-primary badge-pill pull-right">14</span>
                    </a>
                    <a class="list-group-item list-group-item-action waves-effect">Issues
                        <span class="badge badge-primary badge-pill pull-right">123</span>
                    </a>
                    <a class="list-group-item list-group-item-action waves-effect">Messages
                        <span class="badge badge-primary badge-pill pull-right">8</span>
                    </a>
                </div>
                <!-- List group links -->

            </div>

        </div>
        <!--/.Card-->

    </div>
    <!--Grid column-->

</div>
<!--Grid row-->
<script type="application/javascript">
    function test(){
        // Line
        var ctx = document.getElementById("myChart").getContext('2d');
        var myChart = new Chart(ctx, {
            type: 'bar',
            data: {
                labels: ["Red", "Blue", "Yellow", "Green", "Purple", "Orange"],
                datasets: [{
                    label: '# of Votes',
                    data: [12, 19, 3, 5, 2, 3],
                    backgroundColor: [
                        'rgba(255, 99, 132, 0.2)',
                        'rgba(54, 162, 235, 0.2)',
                        'rgba(255, 206, 86, 0.2)',
                        'rgba(75, 192, 192, 0.2)',
                        'rgba(153, 102, 255, 0.2)',
                        'rgba(255, 159, 64, 0.2)'
                    ],
                    borderColor: [
                        'rgba(255,99,132,1)',
                        'rgba(54, 162, 235, 1)',
                        'rgba(255, 206, 86, 1)',
                        'rgba(75, 192, 192, 1)',
                        'rgba(153, 102, 255, 1)',
                        'rgba(255, 159, 64, 1)'
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

        //pie
        var ctxP = document.getElementById("pieChart").getContext('2d');
        var myPieChart = new Chart(ctxP, {
            type: 'pie',
            data: {
                labels: ["Red", "Green", "Yellow", "Grey", "Dark Grey"],
                datasets: [{
                    data: [300, 50, 100, 40, 120],
                    backgroundColor: ["#F7464A", "#46BFBD", "#FDB45C", "#949FB1", "#4D5360"],
                    hoverBackgroundColor: ["#FF5A5E", "#5AD3D1", "#FFC870", "#A8B3C5", "#616774"]
                }]
            },
            options: {
                responsive: true,
                legend: false
            }
        });
    };


    window.onload = test;





</script>
</body>
</html>
