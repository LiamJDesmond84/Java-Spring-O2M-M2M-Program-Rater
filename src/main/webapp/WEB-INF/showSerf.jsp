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
	<div class="d-flex flex-row align-items-center bg-success text-white">
	<div class="d-flex flex-column w-auto">
	
		<h4 class="text-danger">Logged In:</h4>
		<h4>${userLog.name}</h4>
		<a class="btn btn-info" href="/logout">Logout</a>
	</div>

<div class="d-flex justify-content-center flex-column align-items-center col-11 w-75">

<a class="btn btn-dark mb-1" href="/dashboard">Home</a>

<%--Edit ------------- Delete--%>
<c:if test="${show.owner.id == userLog.id}">

	<div class="container d-flex flex-row justify-content-center">
		<a href="/edit/serf/${show.id}" class="btn btn-warning">Edit</a>
		<button onclick="location.href=`/delete/serf/${show.id}`" class="btn btn-danger">Delete</button>
	</div>
</c:if>

	<hr/>
</div>
</div>

<div class="container d-flex flex-row">

<div class="container d-flex flex-column justify-content-center align-items-center">
<h2 class="bg-primary mt-1 rounded">${show.title}</h2>
	<table class="table table-dark mt-2">
		<tbody>
			<tr>
				<th scope="row">Title:</th>
				<td>${show.title}</<td>
			</tr>
			<tr>
				<th scope="row">Network:</th>
				<td>${show.network}</<td>
			</tr>

						<tr>
				<th scope="row">First Aired:</th>	
				<td><fmt:formatDate value="${show.createdAt}" pattern="MMM dd, yyyy" /></td>
			</tr>
			<tr>
				<th scope="row">Creator:</th>	
				<td>${show.owner.name}</td>
			</tr>
			<tr>
				
					<th scope="row">Rating:</th>
					<td>
					<c:set var="avg" value="${0}"/>
					<c:forEach items="${show.ratings}" var="x">
					<c:if test="${show.ratings.size() != 0}">
					<c:set var="avg" value="${avg + x.score}"/>
					</c:if>
					</c:forEach>
					<c:if test="${avg > 0 }">
					<c:set var="avg" value="${avg / show.ratings.size()}"/>
					</c:if>
					${avg}
					</td>
			</tr>
			<tr>
				<th scope="row">Description:</th>	
				<td>${show.description}</td>
			</tr>
		</tbody>
	</table>
	
	<%--Add Ratingggggggg--%>

	<c:set var="userRated" value="false"/>
	<c:forEach items="${show.ratings}" var="x">
	<c:if test="${x.userRating == userLog}">
		<c:set var="userRated" value="true"/>
		<p>You already gave this a rating.  Watch something else!!</p>
	</c:if>
		</c:forEach>


	<c:if test="${userRated == false}">
		<form:form class='showd p-3 bg-light' action="/addRating/" method="POST" modelAttribute="rating">
		 	<form:input type="hidden" value="${show.id}" path="mainRating" />
		 	<form:input type="hidden" value="${userLog.id}" path="userRating" />
			<form:label path="score">Rate</form:label>
			<form:input path="score"/>
			<br/>
			<form:errors path="score" class="text-danger"/>
			<input type="hidden" name="show" value="${show.id}"/>
			<button>Rate</button>
		</form:form>
	</c:if>
	
	<table class="table table-striped mt-2">
	<thead>
		<tr>
			<th>Name</th>
			<th>Rating</th>
			<th>Action</th>
		</tr>
	</thead>
	<tbody>
		<c:forEach items="${show.ratings}" var="x">
		<tr>
			<td>${x.userRating.name}</td>

			<td>${x.score}</td>
			<td>
			<c:choose>
			<c:when test="${userLog == x.userRating}">
				<form action="/removeRating/${x.id}">
					<input type="hidden" name="show" value="${show.id}" />
					
					<button class="btn btn-danger">Delete</button>
				</form>
			</c:when>
			<c:otherwise>
			<td></td>
			</c:otherwise>
			</c:choose>
			</td>
		</tr>
		</c:forEach>
	</tbody>
	
	</table>
	

</div>
</div>

</body>
</html>