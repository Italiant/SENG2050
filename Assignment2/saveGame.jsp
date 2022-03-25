<!--  
 -	Name: Thomas Miller
 -  Student Number: C3279309
 -  Date: 12/05/2019
 -  Course: SENG2050, Assignment 2
 -  JSP: saveGame
-->

<!DOCTYPE html>
<html lang="en">
<head>
<title>Enter Username</title>
<link rel="stylesheet" type="text/css" href="style.css" />
<script src="validate.js"></script>
<!-- method to prevent user from refreshing the page or clicking back -->
<%@page language="java" import="game.GameState" %>
<jsp:useBean id="cases" scope="session" class="game.GameState" />
<%
		response.setHeader("Cache-Control","no-store");
		response.setHeader("Pragma","no-cache"); 
		response.setHeader ("Expires", "0"); 
%>
</head>
<body>
<h1>Deal or No Deal</h1>
<h2>Enter A Username: </h2>

<!-- input box for username and a clear and submit button -->
<!-- the username input is validated by the validate.js function -->
<form action="Game" method="post" onsubmit="return validate();">
<input type="text" id="username" name="username" required placeholder="Enter a username..." title="Letters only no numbers">
<input type="reset" value="Clear" />
<input type="submit" value="Submit" />
</form>

</body>
</html>