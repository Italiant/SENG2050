<%@page language="java" import="myPackage.Issue" import="myPackage.KnowledgeBaseBean" import="myPackage.GeneralUser" import="myPackage.Person" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
<title>Details</title>
<link rel="stylesheet" type="text/css" href="css/style.jsp"/>
<%
		response.setHeader("Cache-Control","no-store");
		response.setHeader("Pragma","no-cache"); 
		response.setHeader ("Expires", "0"); 
%>
</head>
<body>
	<div id="total">
		<h1>Details</h1>

		<p><b>Select the category that your issue would fall under:</b></p>

		<div>
			<form id="loginform" method = "post" class="detailsForm" action="<%=request.getContextPath()%>/NewArticle">
				<b>Network</b><br><br>
	  			<input type="radio" name="numIssue" value="0" checked="checked"> Can't connect<br>
	  			<input type="radio" name="numIssue" value="1"> Speed<br>
	  			<input type="radio" name="numIssue" value="2"> Constant dropouts<br>
	  			<input type="radio" name="numIssue" value="3"> Other<br>
	  			<br>
	  			<b>Software</b><br><br>
	  			<input type="radio" name="numIssue" value="4"> Slow to load<br>
	  			<input type="radio" name="numIssue" value="5"> Won't load at all<br>
	  			<input type="radio" name="numIssue" value="6"> Other<br>
	  			<br>
	  			<b>Hardware</b><br><br>
	  			<input type="radio" name="numIssue" value="7"> Computer won't turn on<br>
	  			<input type="radio" name="numIssue" value="8"> Computer "blue screens"<br>
	  			<input type="radio" name="numIssue" value="9"> Disk drive<br>
	  			<input type="radio" name="numIssue" value="10"> Peripherals<br>
	  			<input type="radio" name="numIssue" value="11"> Other<br>
	  			<br>
	  			<b>Email</b><br><br>
	  			<input type="radio" name="numIssue" value="12"> Can't send<br>
	  			<input type="radio" name="numIssue" value="13"> Can't receive<br>
	  			<input type="radio" name="numIssue" value="14"> SPAM/Phishing<br>
	  			<input type="radio" name="numIssue" value="15"> Other<br>
	  			<br>
	  			<b>Account</b><br><br>
	  			<input type="radio" name="numIssue" value="16"> Password reset<br>
	  			<input type="radio" name="numIssue" value="17"> Wrong details<br>
	  			<input type="radio" name="numIssue" value="18"> Other<br>
	  			<br>
	  			<input type="submit" value="Submit"/>
			</form>
		</div>	
	</div>
</body>
</html>