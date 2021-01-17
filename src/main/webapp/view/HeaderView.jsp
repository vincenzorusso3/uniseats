<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>

    <title> Header </title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="${pageContext.servletContext.contextPath}/css/Header.css" rel="stylesheet" type="text/css">
    <script src='https://kit.fontawesome.com/a076d05399.js'></script>
</head>
<body>

<div class="topnav" id="myTopnav">
    <div class="left">


        <!--per il percorso dell'immagine usiamo il contesto-->
        <a id="linkhome" href="${pageContext.servletContext.contextPath}">
            <img class="logo" src="${pageContext.servletContext.contextPath}/img/LogoUniseats.png">
        </a>


        <!--se loggato visualizzo le opzioni-->
        <%

            Boolean logged = (Boolean) request.getSession().getAttribute("logged");
            if (logged == null || !logged) {
        %>

    </div>

    <div class="right">
        <a href="${pageContext.servletContext.contextPath}/view/login/LoginView.jsp" class="button">Login</a>

        <%
        } else if(logged==true){
        %>

    </div>

    <div class="left">

        <a class="link hover" href="${pageContext.servletContext.contextPath}/view/prenotazione/NuovaPrenotazioneView.jsp">Prenota</a>
        <a class="link hover" href="${pageContext.servletContext.contextPath}/view/prenotazioni_effettuate/VisualizzaPrenotazioniView.jsp">Prenotazioni effettuate</a>
        <a class="link hover" href="${pageContext.servletContext.contextPath}/logout">Logout</a>
    </div>


    <div class="right">

        <a id="profile" href="${pageContext.servletContext.contextPath}/view/profilo_utente/ProfiloUtenteView.jsp">

            <i class='fas fa-user-alt'></i>

        </a>

        <%
            }
        %>

        <a href="javascript:void(0);" class="icon hamburger_menu" onclick="myFunction()">&#9776;</a>

    </div>
</div>

<script src="${pageContext.servletContext.contextPath}/js/Header.js"></script>

</body>
</html>





