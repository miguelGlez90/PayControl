<!doctype html>
<%@ page contentType="text/html;charset=UTF-8" %>
<html lang="en" class="no-js">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta http-equiv="x-ua-compatible" content="ie=edge">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <title> <g:layoutTitle default="Grails"/> </title>
    <!-- INIT-Calendar-->
    <asset:stylesheet src="jquery-ui 1.12.1.2/jquery-ui.css"></asset:stylesheet>
    <asset:stylesheet src="jquery-ui 1.12.1.2/jquery-ui.structure.css"></asset:stylesheet>
    <!-- FIN-Calendar-->
    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.11.2/css/all.css">
    <asset:stylesheet src="/css/bootstrap.min.css" ></asset:stylesheet>
    <asset:stylesheet src="/css/mdb.min.css" ></asset:stylesheet>
    <asset:stylesheet src="/css/style.min.css" ></asset:stylesheet>
    <asset:link rel="icon" href="homeIcon.ico.ico" type="image/x-ico"/>
    <style type="text/css">
    .map-container{
        overflow:hidden;
        padding-bottom:56.25%;
        position:relative;
        height:0;
    }
    .map-container iframe{
        left:0;
        top:0;
        height:100%;
        width:100%;
        position:absolute;
    }
    </style>
</head>

<body class="grey lighten-3">
<header>
    <nav class="navbar fixed-top navbar-expand-lg navbar-light white scrolling-navbar" style="background-color: #e3f2fd">
        <div class="container-fluid">
            <a class="navbar-brand waves-effect" href="${createLink(uri: '/')}"> <strong class="blue-text">PAY CONTROL</strong> </a>

            <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent"
                    aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>

            <div class="collapse navbar-collapse" id="navbarSupportedContent">
                <ul class="navbar-nav mr-auto">
                    <li class="nav-item">
                        <a class="nav-link waves-effect" href="${createLink(controller: 'lote')}">Lotes</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link waves-effect" href="${createLink(controller: 'cobro')}">Cobros</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link waves-effect" href="${createLink(controller: 'comprador')}">Compradores</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link waves-effect" href="${createLink(controller: 'vendedor')}">Vendedores</a>
                    </li>
                </ul>

                <ul class="navbar-nav nav-flex-icons">
                    <li class="nav-item text-danger">
                        <a href="${createLink(controller: 'logout')}">
                            <i class="fas fa-times"></i> Salir
                        </a>
                    </li>
                </ul>

            </div>

        </div>
    </nav>

    <div class="sidebar-fixed position-fixed" style="background-image: linear-gradient(to right, #141e30, #243b55) !important; ">
        <a class="logo-wrapper waves-effect">
            <asset:image src="home4.png" alt="AdminLTE Logo" class="img-fluid"/>
        </a>

        <div class="list-group list-group-flush">
            <a href="#" class="list-group-item active waves-effect">
                <i class="fas fa-chart-pie mr-3"></i>Dashboard
            </a>
            <a href="${createLink(controller: 'empresa')}" class="list-group-item list-group-item-action waves-effect">
                <i class="fas fa-building mr-3"></i>Empresa</a>
            <a href="${createLink(controller: 'pagoRenta')}" class="list-group-item list-group-item-action waves-effect">
                <i class="fas fa-dollar-sign mr-3"></i>Pagos Renta</a>
            <a href="${createLink(controller: 'comprador')}" class="list-group-item list-group-item-action waves-effect">
                <i class="fas fa-user-tie mr-3"></i>Compradores</a>
            <a href="${createLink(controller: 'vendedor')}" class="list-group-item list-group-item-action waves-effect">
                <i class="fas fa-user-tag mr-3"></i>Vendedores</a>
            <a href="${createLink(controller: 'contrato')}" class="list-group-item list-group-item-action waves-effect">
                <i class="fas fa-file-signature mr-3"></i>Contratos</a>
            <a href="${createLink(controller: 'lote')}" class="list-group-item list-group-item-action waves-effect">
                <i class="fas fa-map mr-3"></i>Lotes</a>
            <a href="${createLink(controller: 'cobro')}" class="list-group-item list-group-item-action waves-effect">
                <i class="fas fa-hand-holding-usd mr-3"></i>Cobros</a>
        </div>
    </div>
</header>



<main class="pt-5 mx-lg-5">
    <div class="container-fluid mt-5">
        <g:layoutBody/>
    </div>
</main>




<br/><br/><br/><br/>


<footer class="page-footer text-center font-small primary-color mt-4 wow fadeIn">

    <!--Copyright-->
    <div class="footer-copyright py-3">
        © 2019 Copyright:
        <a href="javascrip: void(0);" target="_blank"> PayControl </a>
    </div>
    <!--/.Copyright-->

</footer>

<!--
<footer class="page-footer text-center font-small primary-color-dark darken-2 mt-4 wow fadeIn">
    <div class="footer-copyright py-3">
        © 2019 Copyright:
        <a href="https://mdbootstrap.com/education/bootstrap/" target="_blank"> MDBootstrap.com </a>
    </div>
</footer>
-->
<asset:stylesheet src="application.css" ></asset:stylesheet>

<asset:javascript src="/js/jquery-3.4.1.min.js"></asset:javascript>
<asset:javascript src="/js/popper.min.js"></asset:javascript>
<asset:javascript src="/js/bootstrap.min.js"></asset:javascript>
<asset:javascript src="/js/mdb.min.js"></asset:javascript>
<!-- INIT-Calendar-->
<asset:javascript src="/jquery-ui 1.12.1.2/jquery-ui.js"></asset:javascript>
<!-- FIN-Calendar-->
<!-- Initializations -->

<script type="text/javascript"> new WOW().init(); </script>
</body>
</html>
