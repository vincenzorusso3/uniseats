<%--
  Created by IntelliJ IDEA.
  User: benedettosimone
  Date: 29/12/20
  Time: 22:27
  To change this template use File | Settings | File Templates.
--%>
<%@page import="java.util.Collection" %>
<%@ page import="java.util.Iterator" %>
<%@ page import="it.uniseats.model.beans.StudenteBean" %>

<%
    String errore = (String) request.getAttribute("errore");
%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<head>
    <title>Profilo Utente</title>
    <link href="${pageContext.servletContext.contextPath}/css/ProfiloUtente.css" rel="stylesheet" type="text/css">
    <script src='https://kit.fontawesome.com/a076d05399.js'></script>
</head>
<body>

<%@include file="../HeaderView.jsp" %>

<div class="background">

    <h1>Profilo utente</h1>

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

<div class="list-group col-md-6 pull-right pad">

    <p class="list-group-item highlight">Nome<span class="pull-right right"><%= request.getSession().getAttribute("nome")%></span></p>
    <p class="list-group-item highlight">Cognome<span class="pull-right right"><%=request.getSession().getAttribute("cognome")%></span></p>
    <p class="list-group-item highlight">Matricola<span class="pull-right right"><%=request.getSession().getAttribute("matricola")%></span></p>
    <p class="list-group-item highlight">Dipartimento<span class="pull-right right"><%=request.getSession().getAttribute("dipartimento")%></span></p>

    <p class="list-group-item highlight">Anno<a class="modificaButton" href="#popup2" ><i class="fas fa-pen-square"></i></a>
        <span class="pull-right right"><%=request.getSession().getAttribute("anno")%></span>
    </p>

    <p class="list-group-item highlight">Email<span class="pull-right right"><%=request.getSession().getAttribute("email")%></span></p>


    <br />


    <div class="pull-right">

        <a class="btn btn-danger btn-md" href="#popup1" >Elimina Profilo</a>
        </form>
    </div>
</div>
</div>
<br/>


</div>

<!--POPUP elimina profilo---->

<div id="popup1" class="overlay">
    <div class="popup">
        <h2>Sei sicuro di voler eliminare il profilo?</h2>
        <a class="close" href="#">&times;</a>

        <div class="content">
            <a class="btn btn-success btn-md" href="#" >No</a>
            <form action="${pageContext.servletContext.contextPath}/profilo" method="post">
                <input type="hidden" value="confermaDelete" name="action">
                <button class="btn btn-danger btn-md" >Si</button>
            </form>
        </div>
    </div>
</div>


<!--POPUP modifica data---->

<div id="popup2" class="overlay">
    <div class="popup">
        <h2>Modifica anno di corso</h2>

        <form action="${pageContext.servletContext.contextPath}/profilo" method="post">

        <select class="md-textfield-input" id="anno" name="annomod" required >
            <option value="1">Primo anno</option>
            <option value="2">Secondo anno</option>
            <option value="3">Terzo anno</option>
            <option value="4">Quarto anno</option>
            <option value="5">Quinto anno</option>
        </select>

        <a class="close" href="#">&times;</a>

        <div class="content">

                <input type="hidden" value="confermaUpdate" name="action">
                <button class="btn btn-danger btn-md">Conferma</button>

        </div>
        </form>
    </div>
</div>



</body>
</html>
