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

<form action="${pageContext.servletContext.contextPath}/managePrenotazione" method="post">
    <input type="text" name="action" value="modificaData" hidden/>
    <label><%=prenotazioneBean.getCodice()%>"></label><br>
    <input type="date" name="data" value="date" placeholder="<%=prenotazioneBean.getData()%>"/><br>
    <label><%=prenotazioneBean.getCodiceAula()%></label>
    <label><%=prenotazioneBean.getCodicePosto()%></label>
    <%
        String tipo="";
        if(prenotazioneBean.isSingolo()==true) {
             tipo = "Singolo";
        }else tipo="Gruppo";
    %>
    <input type="text" name="tipo" value="tipo" placeholder="<%=tipo%>"/>
    <input type="subit" value="Conferma"/>









    <input type="submit" value="Submit">
</form>
</body>
</html>
