<html>
<head>
    <meta name="layout" content="login"/>
    <title><g:message code="springSecurity.login.title"/></title>
    <asset:link rel="icon" href="support.ico" type="image/x-ico"/>
    <style>
    #intro {
        background-image: url(https://mdbootstrap.com/img/new/fluid/city/008.jpg);
        height: 100vh;
    }

    /* Height for devices larger than 576px */
    @media (min-width: 992px) {
        #intro {
            margin-top: -58.59px;
        }
    }

    .navbar .nav-link {
        color: #fff !important;
    }
    </style>
</head>

<body>

<header>
    <style>
    #intro {
        background-image: url(https://mdbootstrap.com/img/new/fluid/city/008.jpg);
        height: 100vh;
    }

    /* Height for devices larger than 576px */
    @media (min-width: 992px) {
        #intro {
            margin-top: -58.59px;
        }
    }

    .navbar .nav-link {
        color: #fff !important;
    }
    </style>



    <!-- Background image -->
    <div id="intro" class="bg-image shadow-2-strong">
        <div class="mask d-flex align-items-center h-100" style="background-color: rgba(0, 0, 0, 0.8);">
            <div class="container">
                <div class="row justify-content-center">
                    <div class="col-xl-5 col-md-8">
                        <g:if test='${flash.message}'>
                            <br/>
                            <div class="alert alert-info" role="alert" data-mdb-color="info">
                                <i class="fas fa-chevron-circle-right me-3"></i> <strong>${flash.message}</strong>
                            </div>
                        </g:if>

                        <form class="bg-white  rounded-5 shadow-5-strong p-5" action=${postUrl} method='POST' id='loginForm' autocomplete='off'>

                            <!-- Email input -->
                            <div class="form-outline mb-4">
                                <input type="text" id="username" name="username" required="required" class="form-control">
                                <label class="form-label" for="username" style="margin-left: 0px;">Usuario </label>
                                <div class="form-notch"><div class="form-notch-leading" style="width: 9px;"></div>
                                    <div class="form-notch-middle" style="width: 88.80000000000001px;"></div>
                                    <div class="form-notch-trailing"></div>
                                </div>
                            </div>

                            <!-- Password input -->
                            <div class="form-outline mb-4">
                                <input type="password" id="password" name="password" required="required" class="form-control">
                                <label class="form-label" for="password" style="margin-left: 0px;">Contraseña</label>
                                <div class="form-notch">
                                    <div class="form-notch-leading" style="width: 9px;"></div>
                                    <div class="form-notch-middle" style="width: 64.80000000000001px;"></div>
                                    <div class="form-notch-trailing"></div>
                                </div>
                            </div>

                            <!-- Submit button -->
                            <button type="submit" class="btn btn-primary btn-block">Iniciar sessión</button>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <!-- Background image -->
</header>
<!--Main Navigation-->

<!--Footer-->
<footer class="bg-light text-lg-start">

    <hr class="m-0">

    <!-- Copyright -->
    <div class="text-center p-3" style="background-color: rgba(0, 0, 0, 0.2);">
        © 2021 Copyright - PayControl
    </div>
    <!-- Copyright -->
</footer>
<!--Footer-->



</body>
</html>