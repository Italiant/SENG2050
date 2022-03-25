<%@page language="java" import="myPackage.Issue" import="myPackage.KnowledgeBaseBean"%>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
<title>Knowledge Base</title>
<link rel="stylesheet" type="text/css" href="css/style.jsp"/>

<jsp:useBean id="kb" scope="session" class="myPackage.KnowledgeBaseBean" />

<%
		response.setHeader("Cache-Control","no-store");
		response.setHeader("Pragma","no-cache"); 
		response.setHeader ("Expires", "0"); 
%>
</head>
<body>
	<div id="total">
		<h1>Knowledge Base</h1>

		<div id="Articles">
			<h1>Articles</h1>
		</div>

		<div id = "filter">
			<form id="filterForm" method = "post" action="<%=request.getContextPath()%>/KnowledgeBase">
				<b>Filter</b><br><br>
				Issue Category:<br><br>
				<input type="radio" name="category" value="All" checked="checked"> All Categories
	  			<input type="radio" name="category" value="Network"> Network
	  			<input type="radio" name="category" value="Software"> Software
	  			<input type="radio" name="category" value="Hardware"> Hardware
	  			<input type="radio" name="category" value="Email"> Email
	  			<input type="radio" name="category" value="Account"> Account
	  			<br><br>
				Sort by Date:<br><br>
	  			<input type="radio" name="date" value="oldToNew" checked="checked"> Oldest to Newest
	  			<input type="radio" name="date" value="newToOld"> Newest to Oldest
	  			<br><br>
	  			<button type="submit" value="Submit">Filter</button>
	  			<br><br>
	  		</form>
	  	</div>

		<div>  	
		<%String temp = (String) session.getAttribute("sorted");
		if(temp == null)
		{temp = "";}
		if(!temp.equals("true"))
		{%>
				<table>
					<form action = "<%=request.getContextPath()%>/ViewArticle" method = "post">
					<c:forEach items="${kb.articles}" var="articles"> 
						<tr>
							<td><c:out value="${articles.dateReported}"/></td>
							<td><c:out value="${articles.category}"/></td>
							<td><button name="issue" type="submit" value="${articles.issueID}"><c:out value="${articles.issueTitle}"/></button></td>
						</tr>
					</c:forEach>
					</form>
				</table>
		<%}
		else
		{%>
			<table>
				<tr>
					<th>Date</th>
					<th>Category</th>
					<th>Title</th>
				</tr>
					<form class = "issueTable" method = "post" action = "<%=request.getContextPath()%>/ViewArticle">
						<c:forEach var="item" items="${sortedList}">
							<tr>
									<td><c:out value="${item.dateReported}"/></td>
									<td><c:out value="${item.category}"/></td>
									<td><button name = "issue" type = "submit" value = "${item.issueID}"><c:out value="${item.issueTitle}"/></button></td>
							</tr>
						</c:forEach>
					</form>	
			</table>
		<%}
		session.setAttribute("sorted", null);%>
		</div>
	</div>
		<form action="<%=request.getContextPath()%>/Index" method="Post">
			<button class = "return button" type="submit" name="returnValue" value="true" > Return </button>
		</form>
		
</body>
</html>