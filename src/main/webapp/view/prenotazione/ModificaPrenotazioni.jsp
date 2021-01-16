<%@ page import="it.uniseats.model.beans.PrenotazioneBean" %><%--
  Created by IntelliJ IDEA.
  User: alessiasabia
  Date: 13/01/2021
  Time: 12:18
  To change this template use File | Settings | File Templates.
--%>
<%
    PrenotazioneBean prenotazioneBean = (PrenotazioneBean) request.getAttribute("codice");
%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>

<form action="../../managePrenotazione">
    <input type="text" name="action" value="modificaPrenotazione" hidden>
    <input type="date" name="data" value="date" placeholder="<%=prenotazioneBean.getData()%>"><br>
    <label><%=prenotazioneBean.getEdificio()%></label>
    <label><%=prenotazioneBean.getCodiceAula()%></label>
    <label><%=prenotazioneBean.getCodicePosto()%></label>
    //TODO mostrare il tipo di prenotazione


    <input type="submit" value="Submit">
</form>
</body>
</html>
