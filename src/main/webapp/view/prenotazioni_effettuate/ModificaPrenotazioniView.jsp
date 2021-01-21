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

<%
    // Check logged
    Boolean loggato = (Boolean) session.getAttribute("logged");
    if ((loggato == null) || (!loggato)) {
        response.sendRedirect(request.getContextPath()+"/view/login/LoginView.jsp");
        return;
    }

%>

<%
    String tipo="";
    if(prenotazioneBean.isSingolo()==true) {
        tipo = "Singolo";
    }else tipo="Gruppo";
%>

<html>
<head>
    <title>ModificaPrenotazioni</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="${pageContext.servletContext.contextPath}/css/ModificaPrenotazioni.css" rel="stylesheet" type="text/css">


    <script
            src="https://code.jquery.com/jquery-3.5.1.min.js"
            integrity="sha256-9/aliU8dGd2tb6OSsuzixeV4y/faTqgFtohetphbbj0="
            crossorigin="anonymous"></script>

</head>
<body>
<%@include file="../HeaderView.jsp" %>

<div class="background">
    <h1>Modifica Prenotazione</h1>
</div>



<div class="card">
<form action="${pageContext.servletContext.contextPath}/managePrenotazione" method="post">

    <input id="actionValue" type="hidden" name="action" value="modificaData" />


    <p class="title">codice Prenotazione:<br><label><%=prenotazioneBean.getCodice()%></label></p>


    <div class="form">

        <ul class="tab-group">
            <li class="tab active"><a href="#data">Modifica data</a></li>
            <li class="tab"><a href="#tipo">Modifica tipo</a></li>
        </ul>


        <!--modifica data-->
        <div class="tab-content">
            <div id="data">

                <p>Aula: <label><%=prenotazioneBean.getCodiceAula()%></label></p>
                <p>Posto: <label><%=prenotazioneBean.getCodicePosto()%></label></p>
                <p><input type="date" name="data" value="<%=prenotazioneBean.getData()%>"><p/>
                    <input type="hidden" name="codice" value="<%=prenotazioneBean.getCodice()%>">

                <input type="submit" value="ConfermaData" class="buttonConf"/>

            </div>


            <!--modifica data-->
            <div id="tipo">


                <p>Aula: <label><%=prenotazioneBean.getCodiceAula()%></label></p>
                <p> Posto: <label><%=prenotazioneBean.getCodicePosto()%></label></p>

                <div class="tipo">
                Tipo: <select name="tipologia" id="tipo1" placeholder="<%=tipo%>"/>
                <option value="singolo">Singolo</option>
                <option value="gruppo">Gruppo</option>

                </div>


                <input id="btnTipo" type="submit" value="ConfermaTipo" class="buttonConf"/>

                <script>

                    $("#btnTipo").on('click', () => {

                        $("#actionValue").attr("value","modificaPrenotazione");

                    });

                </script>

            </div>

        </div><!-- tab-content -->

    </div> <!-- /form -->







</form>
</div>


<script>



    $('.form').find('input, textarea').on('keyup blur focus', function (e) {

        var $this = $(this),
            label = $this.prev('label');

        if (e.type === 'keyup') {
            if ($this.val() === '') {
                label.removeClass('active highlight');
            } else {
                label.addClass('active highlight');
            }
        } else if (e.type === 'blur') {
            if( $this.val() === '' ) {
                label.removeClass('active highlight');
            } else {
                label.removeClass('highlight');
            }
        } else if (e.type === 'focus') {

            if( $this.val() === '' ) {
                label.removeClass('highlight');
            }
            else if( $this.val() !== '' ) {
                label.addClass('highlight');
            }
        }

    });

    $('.tab a').on('click', function (e) {

        e.preventDefault();

        $(this).parent().addClass('active');
        $(this).parent().siblings().removeClass('active');

        target = $(this).attr('href');

        $('.tab-content > div').not(target).hide();

        $(target).fadeIn(600);

    });
</script>
</body>
</html>
