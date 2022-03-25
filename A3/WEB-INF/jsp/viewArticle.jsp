<%@page language="java" import="myPackage.Issue" import="myPackage.KnowledgeBaseBean" import="myPackage.GeneralUser" import="myPackage.Person" %>
<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
<title>View Article</title>
<link rel="stylesheet" type="text/css" href="css/style.jsp"/>
<script src="js/validate_viewArticle.jsp"></script>
<%
		response.setHeader("Cache-Control","no-store");
		response.setHeader("Pragma","no-cache"); 
		response.setHeader ("Expires", "0"); 
%>
<jsp:useBean id="generalUser" scope="session" class="myPackage.GeneralUser"/>
<jsp:useBean id="staffUser" scope="session" class="myPackage.Staff"/>
<jsp:useBean id="issue" scope="session" class="myPackage.Issue"/>
</head>
<body>
	<div id ="total">
	<h1>View Article</h1>
	<div id ="Articles">
		<h3 id = "viewTitle">Title: <c:out value="${issue.issueTitle}"/></h3>
		<br>
		<p>
		<%if(request.getAttribute("article") == null){%>
		State: <c:out value="${issue.state}"/>
		<br>	
		<%}%>
		
		Category: <c:out value="${issue.category}"/>
		<br>
		Report Date: <c:out value="${issue.dateReported}"/>
		<br>
		<%if(request.getAttribute("article") != null){%>
		Date Resolved: <c:out value="${issue.dateResolved}"/>
		<br>
		<%}%>
		Description: <c:out value="${issue.issueDescription}"/>
		<br>
		<%--Solution Shown here --%> 
		<br>
		
		<%if(request.getAttribute("article") == null){%>
		Type Comments Here...</p>
		<form action="<%=request.getContextPath()%>/ViewArticle" method="post" onsubmit="return validate()">
			<textarea id = "commentArea" name="commentInfo" rows="5" cols="100"></textarea>
			<br><br>
			<button class = "viewSubmit" name="newComment" type="submit" value="Submit">Submit</button>
			&nbsp
			<button type="reset" value="Reset">Reset</button>
		</form>
		<%}%>
		
		<c:forEach var="comment" items="${issue.comments}">
			<p class='${comment.solution}' >
				<c:out value="${comment.username}"/><br>
				<c:out value="${comment.commentInfo}"/><br>
				<c:out value="${comment.commentDate}"/><br>
			</p>
			<%if(request.getAttribute("article") == null){%>
				<c:choose>
				<c:when test="${staffUser.username != null}">	
				<form action="<%=request.getContextPath()%>/ViewArticle" method="post">
					<button id = "solutionButton" name="solution" type="submit" value="${comment.commentID}">Set Solution</button><br>
				</form>
				</c:when> 
				<c:when test="${generalUser.username != null}">
				<c:choose>
				<c:when test="${comment.solution == true}">
					<form action="<%=request.getContextPath()%>/ViewArticle" method="post">
						<button id = "solutionButton" name="choice" type="submit" value="accept">Accept Solution</button><br>
						<button id = "solutionButton" name="choice" type="submit" value="reject">Reject Solution</button><br>
					</form>
				</c:when>
				</c:choose>
				</c:when> 
				<c:otherwise>  </c:otherwise>
				</c:choose>
			<%}%>	
		</c:forEach>
		
		<form action="<%=request.getContextPath()%>/Index" method="post">
			<button class = "returnButton" type="submit" name="returnValue" value="true" > Return </button>
		</form>
	</div>
	<%if(request.getAttribute("article") == null){%>
	<c:choose>
	<c:when test="${staffUser.username != null}">
	<div id = "SideMenu">
		<%-- Staff Menu --%>
				<form action="<%=request.getContextPath()%>/ViewArticle" method="post">
					<select name="state">
					  <option value="reporter">Waiting on Reporter</option>
					  <option value="party">Waiting on Third Party</option>
					</select>
				<button class = "returnButton" type="submit"> Accept </button>	
				</form>	
				<c:choose>
				<c:when test="${(issue.state == 'Completed') or (issue.state == 'Resolved')}">
					<form action="<%=request.getContextPath()%>/ViewArticle" method="post">
					<button class = "returnButton" name = "send" value = "true" type="submit"> Send to Knowledge Base </button>	
					</form>
				</c:when>
				</c:choose>
	</div>
	</c:when>    
			 <c:otherwise>  </c:otherwise>
	</c:choose>
	<%}%>
	</div>
</body>
</html>