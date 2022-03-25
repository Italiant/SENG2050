<%@page language="java" import="myPackage.Issue" import="myPackage.KnowledgeBaseBean" import="myPackage.GeneralUser" import="myPackage.Person" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
<title>New Article</title>
<link rel="stylesheet" type="text/css" href="css/style.jsp"/>
<script src="js/validate_newArticle.jsp"></script>
<%
		response.setHeader("Cache-Control","no-store");
		response.setHeader("Pragma","no-cache"); 
		response.setHeader ("Expires", "0"); 
%>
</head>
<body>
	<div id="total">
		<h1>New Article</h1>

		<div> 
			<p><b>Write a title and brief description of your issue:</b></p>

			<form id="loginform" method = "post" class = "detailsForm" action = "<%=request.getContextPath()%>/NewArticle" onsubmit="return validate()"> <!-- adding an action here? will go to newArticle.java -->
				Title<br>
				<input id = issueTitle type = "text" name = "issueTitle"/>
				<br><br>
				Issue Description
				<br>
				<textarea id = "issueDescription" name = "issueDescription" rows= "6" cols = "85"></textarea>
				<br><br>
				<button type = "submit" name = "issue" value = "new">Submit</button>
				<input type = "reset" name = "Clear" value = "Clear">
			</form>
		</div>

		<div id="SideMenu">
			<h2>Suggested Articles</h2>
			<table>
				<tr>
					<th>Date</th>
					<th>Category</th>
					<th>Title</th>
				</tr>
					<form class = "issueTable" method = "post" action = "<%=request.getContextPath()%>/ViewArticle">
						<c:forEach var="item" items="${suggestList}">
							<tr>
									<td><c:out value="${item.dateReported}"/></td>
									<td><c:out value="${item.category}"/></td>
									<td><button name = "issue" type = "submit" value = "${item.issueID}"><c:out value="${item.issueTitle}"/></button></td>
							</tr>
						</c:forEach>
					</form>	
			</table>
			<br>
		</div>
</body>
</html>