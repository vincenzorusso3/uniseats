
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>


<%
    // Check logged
    Boolean loggato = (Boolean) session.getAttribute("logged");
    if ((loggato == null) || (!loggato)) {
        response.sendRedirect(request.getContextPath()+"/view/login/LoginView.jsp");
        return;
    }

%>

<!DOCTYPE html>
<html>
<head>
    <title>LandingPage</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="${pageContext.servletContext.contextPath}/css/LandingPage.css" rel="stylesheet" type="text/css">

</head>
<body>
<%@include file="/view/HeaderView.jsp" %>

<div class="background"></div>


<div class="row">
    <div class="column">
        <div class="card">
            <p class="title">PRENOTAZIONI EFFETTUATE</p>
            <p><h6>Visualizza le prenotazioni effettuate</h6></p>
            <p class="b1"><a href="${pageContext.servletContext.contextPath}/managePrenotazione?action=visualizzaPrenotazioni" class="buttonC">  Visualizza </a></p>
        </div>
    </div>

    <div class="column">
        <div class="card">
            <p class="title">NUOVA PRENOTAZIONE</p>
            <p><h6>Effettua una nuova prenotazione</h6></p>
            <p class="b1"><a href="${pageContext.servletContext.contextPath}/view/prenotazione/NuovaPrenotazioneView.jsp" class="buttonC">  Prenota </a></p>
        </div>
    </div>

    <div class="column">
        <div class="card">
            <p class="title">PROFILO UTENTE</p>
            <p><h6>Visualizza dati personali</h6></p>
            <p class="b1"><a href="${pageContext.servletContext.contextPath}/view/profiloutente/ProfiloUtenteView.jsp" class="buttonC"> Visualizza </a></p>
        </div>
    </div>


</div>

</body>
</html>
