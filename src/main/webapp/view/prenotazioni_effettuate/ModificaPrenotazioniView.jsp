<%@ page import="it.uniseats.model.beans.PrenotazioneBean" %><%--
  Created by IntelliJ IDEA.
  User: alessiasabia
  Date: 13/01/2021
  Time: 12:18
  To change this template use File | Settings | File Templates.
--%>
<%

    PrenotazioneBean prenotazioneBean = (PrenotazioneBean) request.getAttribute("prenotazionemod");

    if(prenotazioneBean==null){
        response.sendRedirect(response.encodeRedirectURL("../../managePrenotazione?action=getSinglePren&cod="+request.getParameter("cod")));
        return;
    }
%>

<html>
<head>
    <title>ModificaPrenotazioni</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="${pageContext.servletContext.contextPath}/css/ModificaPrenotazioni.css" rel="stylesheet" type="text/css">
</head>
<body>
<%@include file="../HeaderView.jsp" %>

<div class="background">
    <h1>Modifica Prenotazione</h1>
</div>



<div class="card">
<form action="${pageContext.servletContext.contextPath}/managePrenotazione" method="post">
    <input type="text" name="action" value="modificaData" hidden/>


    <p class="title">codice Prenotazione:<br><label><%=prenotazioneBean.getCodice()%></label></p>

    <p><input type="date" name="data" value="date" placeholder="<%=prenotazioneBean.getData()%>"/><p/>


    <p>Aula: <label><%=prenotazioneBean.getCodiceAula()%></label></p>

    <p> Posto: <label><%=prenotazioneBean.getCodicePosto()%></label></p>

    <%
        String tipo="";
        if(prenotazioneBean.isSingolo()==true) {
             tipo = "Singolo";
        }else tipo="Gruppo";
    %>

    <p>Tipo: <select name="tipo" id="tipo" placeholder="<%=tipo%>"/>
            <option value="0">Singolo</option>
            <option value="2">Gruppo</option>
    </p>

    <input type="submit" value="Conferma"   class="buttonConf"/>

</form>
</div>
</body>
</html>
