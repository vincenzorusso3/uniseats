<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@page import="java.util.Collection" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="it.uniseats.model.beans.PrenotazioneBean" %>
<%@ page import="it.uniseats.utils.DateUtils" %><%--
  Created by IntelliJ IDEA.
  User: alessiasabia
  Date: 12/01/2021
  Time: 11:18
  To change this template use File | Settings | File Templates.
--%>

<%
    // Check logged
    Boolean loggato = (Boolean) session.getAttribute("logged");
    if ((loggato == null) || (!loggato)) {
        response.sendRedirect(request.getContextPath()+"/view/login/LoginView.jsp");
        return;
    }

    String errore = (String) request.getSession().getAttribute("errorPrenotazione");

%>

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
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
    <script src="https://cdn.rawgit.com/davidshimjs/qrcodejs/gh-pages/qrcode.min.js"></script>

</head>
<body>

<%@include file="../HeaderView.jsp" %>

    <div class="background">

         <h1> Prenotazioni Effettuate </h1>

    </div>

<div class="message">

        <%
            if (errore != null) {

        %>
    <h3><%=errore%></h3>
        <%
            }
        %>
</div>

<div class="cardContainer">


        <%
            if(prenotazioni.size()>0){
                Iterator <?> it = prenotazioni.iterator();
                while(it.hasNext()){
                    PrenotazioneBean bean = (PrenotazioneBean) it.next();

        %>

    <div class="card">

        <h5 class="date"><%=DateUtils.englishToItalian(bean.getData().toString())%></h5>

        <div class="leftC">
            <h6 class="edificio">Aula: <%=bean.getCodiceAula()%></h6>
            <h6 class="posto">Posto: <%=bean.getCodicePosto()%></h6>
            <%
                String tipo="";
                if(bean.isSingolo()) {
                    tipo = "Singolo";
                }else tipo="Gruppo";
            %>
            <h6 class="tipo"><%=tipo%></h6>
        </div>

        <div id="qrcode<%=bean.getCodice()%>" class="qrcode">
        <input id="text<%=bean.getCodice()%>" type="text" value="<%=bean.getCodice()%>" hidden/>
        </div>

            <a href="${pageContext.servletContext.contextPath}/view/prenotazionieffettuate/ModificaPrenotazioniView.jsp?cod=<%=bean.getCodice()%>">
                <input type="button" name="modifica" value="Modifica">
            </a>

        <a href="${pageContext.servletContext.contextPath}/managePrenotazione?cod=<%=bean.getCodice()%>&action=eliminaPrenotazione">
            <input type="button" name="elimina" value="Elimina">
        </a>

        <script>

            var qrcode = new QRCode("qrcode<%=bean.getCodice()%>");
            var elText = document.getElementById("text<%=bean.getCodice()%>");
            function makeCode () {


                if (!elText.value) {
                    alert("Input a text");
                    elText.focus();
                    return;
                }

                qrcode.makeCode(elText.value);
            }

            makeCode();

            $("#text").
            on("blur", function () {
                makeCode();
            }).
            on("keydown", function (e) {
                if (e.keyCode == 13) {
                    makeCode();
                }
            });
        </script>
    </div>

                <%
                         }
                     }

                %>

    <%
      request.getSession().setAttribute("errorPrenotazione", null);
    %>

    </div>


</body>
</html>
