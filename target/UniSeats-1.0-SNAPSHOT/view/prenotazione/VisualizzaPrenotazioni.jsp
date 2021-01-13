<%@page import="java.util.Collection" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="it.uniseats.model.beans.PrenotazioneBean" %><%--
  Created by IntelliJ IDEA.
  User: alessiasabia
  Date: 12/01/2021
  Time: 11:18
  To change this template use File | Settings | File Templates.
--%>

<%
    Collection<?> prenotazioni = (Collection<?>)  request.getAttribute("prenotazioni");
    if(prenotazioni==null){
        response.sendRedirect(response.encodeRedirectURL("./ManagePrenotazioneServlet?=visualizzaPrenotazioni"));
        return;
    }

    PrenotazioneBean prenotazioneBean = (PrenotazioneBean) request.getAttribute("prenotazioni");
%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Visualizza Prenotazioni</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <script src='https://kit.fontawesome.com/a076d05399.js'></script>
</head>
<body>

<%@include file="../HeaderView.jsp" %>

    <h1> Prenotazioni Effettuate </h1>

    <table>

        <%
            if(prenotazioni!= null && prenotazioni.size()>0){
                Iterator <?> it = prenotazioni.iterator();
                while(it.hasNext()){
                    PrenotazioneBean bean = (PrenotazioneBean) it.next();

        %>
        <tr>
            <td><%=bean.getData()%></td>
            <td><%=bean.getEdificio()%> - <%=bean.getCodiceAula()%>/td>
            <td><%=bean.getQrCode()%></td>
            <td><%=bean.getCodicePosto()%></td>
            <td><form method="POST" action="./ManagePrenotazioneServlet?action=modifica">
                <input type="button" name="modifica" value="<%= bean.getCodice()%>"/>
            </form></td>
            <td><button></button></td>
        </tr>

                <%
                         }
                     }

                %>
    </table>



</body>
</html>
