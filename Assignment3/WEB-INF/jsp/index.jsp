
<%@page language="java" import="myPackage.Issue" import="myPackage.KnowledgeBaseBean" import="myPackage.GeneralUser" import="myPackage.Person" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
<title>Index</title>
<link rel="stylesheet" type="text/css" href="css/style.jsp"/>
<%
		response.setHeader("Cache-Control","no-store");
		response.setHeader("Pragma","no-cache"); 
		response.setHeader ("Expires", "0"); 
%>
<jsp:useBean id="kb" scope="session" class="myPackage.KnowledgeBaseBean"/>
<jsp:useBean id="generalUser" scope="session" class="myPackage.GeneralUser"/>
<jsp:useBean id="staffUser" scope="session" class="myPackage.Staff"/>
</head>

<body>
	<div id="total">
		<h1>Index</h1> 
	
		<div id="Articles">
			<h2>My Issues</h2>
			<c:choose>
			<c:when test="${staffUser.username != null}">
				<p>Welcome user: ${staffUser.username}</p> 
			</c:when>    
			<c:when test="${generalUser.username != null}">
				<p>Welcome user: ${generalUser.username}</p> 
			</c:when>
			 <c:otherwise> <p>Welcome user: ${generalUser.username}</p> </c:otherwise>
		</c:choose>

		<div id = "filter">
			<form id="filterForm" method = "post" action="<%=request.getContextPath()%>/Index">
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

			<table>
				<tr>
					<th>Date</th>
					<th>Category</th>
					<th>Title</th>
					<c:choose>
					<c:when test ="${staffUser.username != null}">
					<th>Delete Issue</th>
					</c:when>
					</c:choose>
				</tr>

				<%String temp = (String) session.getAttribute("sorted");

				if(temp == null)
				{temp = "";}

				if(!temp.equals("true"))
				{%>
					<form class = "issueTable" method = "post" action = "<%=request.getContextPath()%>/ViewArticle">
						<c:forEach var="item" items="${kb.issues}">
							<tr>
								<c:choose>
								<c:when test ="${item.userName == generalUser.username}">
									<td class="${item.resolved}"><c:out value="${item.dateReported}"/></td>
									<td class="${item.resolved}"><c:out value="${item.category}"/></td>
									<td class="${item.resolved}"><button name="issue" type="submit" value = "${item.issueID}"><c:out value="${item.issueTitle}"/></button></td>
								</c:when>
								<c:when test ="${staffUser.username != null}">
									<td class="${item.state}"><c:out value="${item.dateReported}"/></td>
									<td class="${item.state}"><c:out value="${item.category}"/></td>
									<td class="${item.state}"><button name="issue" type="submit" value = "${item.issueID}"><c:out value="${item.issueTitle}"/></button></td>
									<td class="${item.state}"><button class = "returnButton" name = "delete" value = "${item.issueID}" type="submit" formaction="<%=request.getContextPath()%>/Index"> Delete Issue </button></td>
								</c:when>
								</c:choose>
							</tr>
						</c:forEach>
					</form>
				<%}
				else
				{%>
					<form class = "issueTable" method = "post" action = "<%=request.getContextPath()%>/ViewArticle">
						<c:forEach var="item" items="${sortedList}">
							<tr>
								<c:choose>
								<c:when test ="${item.userName == generalUser.username}">
									<td class="${item.resolved}" ><c:out value="${item.dateReported}"/></td>
									<td class="${item.resolved}" ><c:out value="${item.category}"/></td>
									<td class="${item.resolved}" ><button name = "issue" type = "submit" value = "${item.issueID}"><c:out value="${item.issueTitle}"/></button></td>
								</c:when>
								<c:when test ="${staffUser.username != null}">
									<td class="${item.state}"><c:out value="${item.dateReported}"/></td>
									<td class="${item.state}"><c:out value="${item.category}"/></td>
									<td class="${item.state}"><button name = "issue" type = "submit" value = "${item.issueID}"><c:out value="${item.issueTitle}"/></button></td>
									<td class="${item.state}"><button class = "returnButton" name = "delete" value = "${item.issueID}" type="submit" formaction="<%=request.getContextPath()%>/Index"> Delete Issue </button></td>
								</c:when>
								</c:choose>
							</tr>
						</c:forEach>
					</form>
				<%}
				session.setAttribute("sorted", null);%>

			</table>
			<br>
		</div>
		<div id="SideMenu">
			<h2>Side Menu</h2>
			<c:choose>
			<c:when test ="${generalUser.username != null}">
			<form action = "<%=request.getContextPath()%>/NewArticle" method = "post">
				<button name="new" type="submit" value="first">New Article</button>
			</form>
			</c:when>
			</c:choose>
			<form action = "<%=request.getContextPath()%>/KnowledgeBase" method = "post">
				<button type="submit" name="returnValue" value="knowledge"> View Knowledge Base </button>
			</form>
			<form action = "<%=request.getContextPath()%>/LoginPage" method = "post">
				<button type="submit" name="returnValue" value="logout"> Log Out </button>
			</form>
			<br>
		</div>
		<br>
		<br>
	</div>
</body>
</html>