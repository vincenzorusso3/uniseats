<%--
  Created by IntelliJ IDEA.
  User: benedettosimone
  Date: 30/12/20
  Time: 12:56
  To change this template use File | Settings | File Templates.
--%>

<html>
<head>
    <title>Login</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="../../css/Login.css" rel="stylesheet" type="text/css">

</head>
<body>
<%@include file="../HeaderView.jsp" %>
    <div class="background">

        <h1>Accedi</h1>

    </div>

<div class="form">

    <div class="message">

    </div>
    <form action="../../LoginServlet" method="post">
        <input type="hidden" value="login" name="action">
        <input id="email" type="text" name="email" placeholder="Email" required/>
        <input id="password" type="password" name="password" placeholder="Password" required/>

        <div class="new">
            <h6>Non hai un account?</h6>
            <a href="Registrazione.jsp"> Registrati </a>
        </div>

        <div class="bottoni" align="center">
            <input type="submit" value="Login">
        </div>
    </form>




</div>



</body>
</html>
