<!--  
 -	Name: Thomas Miller
 -  Student Number: C3279309
 -  Date: 12/05/2019
 -  Course: SENG2050, Assignment 2
 -  JSP: DoND
-->

<!DOCTYPE html>
<html lang="en">
<head>
<title>Deal or No Deal</title>
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
<!-- Displays money won after clicking on deal or the last unopened case -->
<%if(request.getAttribute("winMoney").equals("true")) {%> 
<h2>Congratulations</h2>
You won: &dollar;<jsp:getProperty name="cases" property="bankOffer"/>
<form action="index.jsp" method="post">
<input type="submit" value="Main Menu" />
</form>
<% } else { %>
<!-- display final message -->
<%if(cases.getRound().equals("win")) { %>

Pick the last case to see how much money you have won

<% } else { %>

Pick <jsp:getProperty name="cases" property="pickCases"/> cases: </br>
Round: <jsp:getProperty name="cases" property="round"/> </br>
Number Opened: <jsp:getProperty name="cases" property="numOpened"/> </br>

<% } %>

<!-- displays all cases as buttons in a table of 6 rows and 2 columns
     for each case: class = true or false depending on if it is opened or unopened which through css changes the colour of the case to hide the money if unopened, red = unopened 
     				value = the number of the case to return to the game controller		
					onClick = returns false if the case is clicked which prevents the user from clicking on it again and resubmitting the form or false if the deal or no deal buttons are displayed 
					in each button it displays the amount of money in that case 
-->
<table>
<form action = "Game" method = "post">

<tr>
<td>
<button name="openCase" class='<jsp:getProperty name="cases" property="case1"/>' value = "1" onClick='return !(<jsp:getProperty name="cases" property="case1"/> || <jsp:getProperty name="cases" property="offerRound"/>)' >&dollar;<jsp:getProperty name="cases" property="caseA" /></button>
</td><td>
<button name="openCase" class='<jsp:getProperty name="cases" property="case2"/>' value = "2" onClick='return !(<jsp:getProperty name="cases" property="case2"/> || <jsp:getProperty name="cases" property="offerRound"/>)' >&dollar;<jsp:getProperty name="cases" property="caseB" /></button>
</td>
</tr>
<tr>
<td>
<button name="openCase" class='<jsp:getProperty name="cases" property="case3"/>' value = "3" onClick='return !(<jsp:getProperty name="cases" property="case3"/> || <jsp:getProperty name="cases" property="offerRound"/>)' >&dollar;<jsp:getProperty name="cases" property="caseC" /></button>
</td><td>
<button name="openCase" class='<jsp:getProperty name="cases" property="case4"/>' value = "4" onClick='return !(<jsp:getProperty name="cases" property="case4"/> || <jsp:getProperty name="cases" property="offerRound"/>)' >&dollar;<jsp:getProperty name="cases" property="caseD" /></button>
</td>
</tr>
<tr>
<td>
<button name="openCase" class='<jsp:getProperty name="cases" property="case5"/>' value = "5" onClick='return !(<jsp:getProperty name="cases" property="case5"/> || <jsp:getProperty name="cases" property="offerRound"/>)' >&dollar;<jsp:getProperty name="cases" property="caseE" /></button>
</td><td>
<button name="openCase" class='<jsp:getProperty name="cases" property="case6"/>' value = "6" onClick='return !(<jsp:getProperty name="cases" property="case6"/> || <jsp:getProperty name="cases" property="offerRound"/>)' >&dollar;<jsp:getProperty name="cases" property="caseF" /></button>
</td>
</tr>
<tr>
<td>
<button name="openCase" class='<jsp:getProperty name="cases" property="case7"/>' value = "7" onClick='return !(<jsp:getProperty name="cases" property="case7"/> || <jsp:getProperty name="cases" property="offerRound"/>)' >&dollar;<jsp:getProperty name="cases" property="caseG" /></button>
</td><td>
<button name="openCase" class='<jsp:getProperty name="cases" property="case8"/>' value = "8" onClick='return !(<jsp:getProperty name="cases" property="case8"/> || <jsp:getProperty name="cases" property="offerRound"/>)' >&dollar;<jsp:getProperty name="cases" property="caseH" /></button>
</td>
</tr>
<tr>
<td>
<button name="openCase" class='<jsp:getProperty name="cases" property="case9"/>' value = "9" onClick='return !(<jsp:getProperty name="cases" property="case9"/> || <jsp:getProperty name="cases" property="offerRound"/>)' >&dollar;<jsp:getProperty name="cases" property="caseI" /></button>
</td><td>
<button name="openCase" class='<jsp:getProperty name="cases" property="case10"/>' value = "10" onClick='return !(<jsp:getProperty name="cases" property="case10"/> || <jsp:getProperty name="cases" property="offerRound"/>)' >&dollar;<jsp:getProperty name="cases" property="caseJ" /></button>
</td>
</tr>
<tr>
<td>
<button name="openCase" class='<jsp:getProperty name="cases" property="case11"/>' value = "11" onClick='return !(<jsp:getProperty name="cases" property="case11"/> || <jsp:getProperty name="cases" property="offerRound"/>)' >&dollar;<jsp:getProperty name="cases" property="caseK" /></button>
</td><td>
<button name="openCase" class='<jsp:getProperty name="cases" property="case12"/>' value = "12" onClick='return !(<jsp:getProperty name="cases" property="case12"/> || <jsp:getProperty name="cases" property="offerRound"/>)' >&dollar;<jsp:getProperty name="cases" property="caseL" /></button>
</td>
</tr>
</form>
</table>

<!-- always give option to save and quit -->
<form action = "Game" method = "post">
<button name="saveState" class="deal" value="save"> Save and Exit </button>
</form>

<!-- display deal or no deal buttons as well as the current bank offer and the largest unopened case value -->
<% if(cases.isOfferRound()) { %>

Banker Offer: &dollar;<jsp:getProperty name="cases" property="bankOffer"/> </br>
Largest Case Unrevealed: &dollar;<jsp:getProperty name="cases" property="largest"/> </br>
<form action = "Game" method = "post">
<button name="choice" class="deal" value="deal"> Deal </button>
<button name="choice" class="noDeal" value="noDeal"> No Deal </button>
</form>
<% } %>
<% } %>
</body>
</html>