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
    <link href="../css/Header.css" rel="stylesheet" type="text/css">
    <script src='https://kit.fontawesome.com/a076d05399.js'></script>
</head>
<body>

<div class="topnav" id="myTopnav">
    <div class="left">

        <a id="linkhome" href="">
            <img class="logo" src="../img/LogoUniseats.png">
        </a>

        <a class="link hover" href="">Prenota</a>
        <a class="link hover" href="">Prenotazioni effettuate</a>

    </div>
</div>


    <div class="right">

        <a id="profile" href="">

            <i class='fas fa-user-alt'></i>

        </a>

        <button class="button">Login</button>

        <a href="javascript:void(0);" class="icon hamburger_menu" onclick="myFunction()">&#9776;</a>
    </div>


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
