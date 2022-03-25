<%@page language="java"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>Login</title>
<link rel="stylesheet" type="text/css" href="css/style.jsp"/>
<%
		response.setHeader("Cache-Control","no-store");
		response.setHeader("Pragma","no-cache"); 
		response.setHeader ("Expires", "0"); 
%>
</head>
<body>
	<div id="total">
		<h1>Login Page</h1>
		<script src="js/validate_login.jsp"></script>
		<form id="loginform" action = "<%=request.getContextPath()%>/Index" method = "post" onsubmit="return validate()">
		<label for="userId">User ID</label>
		<input type="text" name="userId" id="userId"/>
		<br>
		<br>
		<label for="password">Password</label>
		<input type="password" name="password" id="password"/>
		<br>
		<br>
		<button name="home" id="home" type="submit" >Log In</button>
		<button type="reset" value="Reset"> Clear </button>
		</form>
		<br>
	</div>
</body>
</html>