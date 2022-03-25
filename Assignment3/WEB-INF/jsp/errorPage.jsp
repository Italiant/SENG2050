<%@page language="java"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>Error Page</title>
<link rel="stylesheet" type="text/css" href="css/style.jsp"/>
<%
		response.setHeader("Cache-Control","no-store");
		response.setHeader("Pragma","no-cache"); 
		response.setHeader ("Expires", "0"); 
%>
</head>
<body>
	<div id="total">
<h2>Error:</h2> 
<div>
<p>
Sorry your credentials do not give you site access
</p> 
</div>

<div>
<form action="<%=request.getContextPath()%>/LoginPage" method="get">
<button class = "return button" type="submit" name="returnValue" value="true" > Return </button>
</form>
</div>
</div>

</body>
</html>