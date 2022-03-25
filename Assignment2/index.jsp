<!--  
 -	Name: Thomas Miller
 -  Student Number: C3279309
 -  Date: 12/05/2019
 -  Course: SENG2050, Assignment 2
 -  JSP: index
-->

<!DOCTYPE html>
<html lang="en">
<head>
<title>Main Menu</title> <!-- the main index page -->
<link rel="stylesheet" type="text/css" href="style.css" />
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
<h2>Start a New Game or Load an Existing One:</h2>

<!-- start new game and load existing game buttons -->
<form action="Game" method="post">
<button class = "button one" type="submit" name="newGame" value="new" > Start New Game </button>
</form>
<form action="Game" method="post">
<button class = "button one" type="submit" name="newGame" value="old" > Load Existing Game </button>
</form>

</body>
</html>