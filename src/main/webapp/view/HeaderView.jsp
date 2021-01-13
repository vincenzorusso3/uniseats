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
        <a id="linkhome" href="">
            <img class="logo" src="${pageContext.servletContext.contextPath}/img/LogoUniseats.png">
        </a>


        <!--se loggato visualizzo le opzioni-->
        <%
            Boolean logged = (Boolean) request.getSession().getAttribute("logged");
            if (logged) {
        %>

        <a class="link hover" href="../prenotazione/NuovaPrenotazione.jsp">Prenota</a>
        <a class="link hover" href="<%=response.encodeURL("ManagePrenotazioneServlet?action=visualizzaPrenotazioni") %>">Prenotazioni effettuate</a>

        <%
            }
        %>

    </div>


    <div class="right">

        <a id="profile" href="">

            <i class='fas fa-user-alt'></i>

        </a>

        <%
            if (logged == null || !logged) {
        %>

        <button class="button">Login</button>

        <%
            } else{

        %>

        <button class="button">Logout</button>

        <%
            }
        %>

        <a href="javascript:void(0);" class="icon hamburger_menu" onclick="myFunction()">&#9776;</a>
    </div>
</div>

<script src="../js/Header.js"></script>

</body>
</html>





