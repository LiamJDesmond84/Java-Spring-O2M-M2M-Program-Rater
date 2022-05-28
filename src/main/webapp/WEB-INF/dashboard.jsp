<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"  %>
    <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"  %>
    <%@ page isErrorPage="true"  %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Insert title here</title>
	<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
	<div class="bg-success text-white">
		<h2>Welcome ${userLog.name}!</h2>
	<a href="/logout" class="btn btn-info mb-2">Logout</a>
	</div>
	<div class="d-flex flex-column justify-content-center align-items-center">
		<h1>TV Shows</h1>
	</div>
	<div class="container">
	
			<button onclick="location.href=`/newSerfForm`" class="btn btn-success mb-2">Add Show</button>



		<table class="table table-dark">
			<thead>
			<tr>
				<th>Id</th>
				<th>Title</th>
				<th>Network</th>
				<th>Ratings</th>
				<th>Average Rating</th>
				<th>Creator</th>
				<th>Action</th>
			</tr>
			</thead>
			
			<tbody> <!-- Loop through all dogs -->
			<c:forEach items="${allSerfs}" var="x">
			
			<tr>
				<td>${x.id}</td>

				<td><a href="/serf/show/${x.id}">${x.title}</a></td>
				<td>${x.network}</td>
				

 				<td>${x.ratings.size()}</td>




<%-- -----  RATINGGGGGGGG--%>		

		
				<c:choose>

					<c:when test="${x.ratings.isEmpty()}">
						<td>0</td>
					</c:when>
					<c:otherwise>
											<td>
					<c:set var="avg" value="${0}"/>
					<c:forEach items="${x.ratings}" var="y">
					<c:if test="${x.ratings.size() != 0}">
					<c:set var="avg" value="${avg + y.score}"/>
					</c:if>
					</c:forEach>
					<c:if test="${avg > 0 }">
					<c:set var="avg" value="${avg / x.ratings.size()}"/>
					</c:if>
					${avg}
					</td>
					</c:otherwise>
					
				</c:choose>
<%-- ENDDDDD  -----  RATINGGGGGGGG--%>				

				<td>${x.owner.name}</td>

				<c:if test="${x.owner.id == userLog.id}">
					<td><a href="/delete/serf/${x.id}">Delete</a></td>
				</c:if>
						


				
			</tr>
			
			</c:forEach>
			</tbody>	
		</table>


	</div>
	
</body>
</html>