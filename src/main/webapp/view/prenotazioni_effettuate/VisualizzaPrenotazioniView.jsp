<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
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
        response.sendRedirect(response.encodeRedirectURL(request.getContextPath()+"/managePrenotazione?action=visualizzaPrenotazioni"));
        return;
    }


%>

<html>
<head>
    <title>Visualizza Prenotazioni</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <script src='https://kit.fontawesome.com/a076d05399.js'></script>
    <link href="${pageContext.servletContext.contextPath}/css/VisualizzaPrenotazioni.css" rel="stylesheet" type="text/css">
</head>
<body>

<%@include file="../HeaderView.jsp" %>

    <div class="background">

         <h1> Prenotazioni Effettuate </h1>

    </div>

<div class="cardContainer">


        <%
            if(prenotazioni.size()>0){
                Iterator <?> it = prenotazioni.iterator();
                while(it.hasNext()){
                    PrenotazioneBean bean = (PrenotazioneBean) it.next();

        %>

    <div class="card">

        <h5 class="date"><%=bean.getData()%></h5>

        <div class="leftC">
            <h6 class="edificio">Aula: <%=bean.getCodiceAula()%></h6>
            <h6 class="posto">Posto: <%=bean.getCodicePosto()%></h6>
            <%
                String tipo="";
                if(bean.isSingolo()==true) {
                    tipo = "Singolo";
                }else tipo="Gruppo";
            %>
            <h6 class="tipo"><%=tipo%></h6>
        </div>

        <h6 class="code">Cod:<%=bean.getCodice()%></h6>







            <a href="${pageContext.servletContext.contextPath}/view/prenotazioni_effettuate/ModificaPrenotazioniView.jsp?cod=<%=bean.getCodice()%>">
                <input type="button" name="modifica" value="Modifica">
            </a>


    </div>

                <%
                         }
                     }

                %>

</div>


</body>
</html>
