<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>

<%
    String errore = (String) request.getAttribute("errore");
%>

<html>
<head>
    <title>Home</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="../css/Home.css" rel="stylesheet" type="text/css">

</head>
<body>
<%@include file="HeaderView.jsp" %>

    <%
        if (errore != null) {

    %>
        <h3><%=errore%></h3>
    <%
        }
    %>

    <div class="background">

        <h1>Prenota il tuo posto
        in aula con Uniseats</h1>

        <button class="buttonHome">Inizia ora</button>

    </div>

</div>

</body>
</html>
