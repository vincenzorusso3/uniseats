<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>

    <title> Header </title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="jetbrains://idea/navigate/reference?project=UniSeats&path=css/Header.css" rel="stylesheet" type="text/css">
    <script src='https://kit.fontawesome.com/a076d05399.js'></script>
</head>
<body>

<div class="topnav" id="myTopnav">
    <div class="left">

        <a id="linkhome" href="">
            <img class="logo" src="jetbrains://idea/navigate/reference?project=UniSeats&path=img/LogoUniseats.png">
        </a>

        <a class="link hover" href="">Prenota</a>
        <a class="link hover" href="">Prenotazioni effettuate</a>

    </div>


    <div class="right">

        <a id="profile" href="">

            <i class='fas fa-user-alt'></i>

        </a>

        <button class="button">Login</button>

        <a href="javascript:void(0);" class="icon hamburger_menu" onclick="myFunction()">&#9776;</a>
    </div>
</div>

<script src="../js/Header.js"></script>

</body>
</html>





