<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<link rel="stylesheet"
	href="https://stackpath.bootstrapcdn.com/bootstrap/4.2.1/css/bootstrap.min.css"
	integrity="sha384-GJzZqFGwb1QTTN6wy59ffF1BuGJpLSa9DkKMp0DgiMDm4iYMj70gZWKYbI706tWS"
	crossorigin="anonymous">
<title>Employee Management</title>
</head>
<body>
	<div class="container">
		<br>
		<h3>
			Welcome to Employee Management
			<c:choose>
				<c:when test="${loggedInUser != null}">
					<p style="text-align: right;">Hi, ${loggedInUser} &nbsp;&nbsp;&nbsp;
					<a href="/logout" class="btn btn-primary btn-sm mb-3 float-right">
						Logout </a></p>
				</c:when>
				<c:otherwise>
					<a href="/login" class="btn btn-primary btn-sm mb-3 float-right">
						Login </a>
				</c:otherwise>
			</c:choose>
		</h3>
		<hr>
		<a href="/swagger-ui.html" class="btn btn-primary btn-sm mb-3">
			Employee Management API </a> <a href="/h2-console"
			class="btn btn-primary btn-sm mb-3 float-right"> H2 - Console </a>
		<hr>

		<button type="button" class="collapsible" style="text-align: center;">View
			Users &amp; Roles</button>
		<div class="content">
			<table class="table table-bordered table-striped"
				style="text-align: center;">
				<thead class="thead-dark">
					<tr>
						<th colspan="2">Users</th>
						<th colspan="1">Roles</th>
					</tr>
					<tr>
						<th>Username (Id)</th>
						<th>Password</th>
						<th>Name (Id)</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${Users}" var="user">
						<tr>
							<td><c:out value="${user.username} (${user.id})" /></td>
							<td><c:out value="${user.password}" /></td>
							<td><c:forEach items="${user.userRoles}" var="userRole">
									<c:out value="${userRole.name} (${userRole.id})" />
									<br>
								</c:forEach></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
			<p style="text-align: center;">
				<b><i>Note: Default username and password is same</i></b>
			</p>
		</div>
		<br>
		<button type="button" class="collapsible" style="text-align: center;">View
			Employees</button>
		<div class="content">
			<table class="table table-bordered table-striped"
				style="text-align: center;">
				<thead class="thead-dark">
					<tr>
						<th>Id</th>
						<th>First Name</th>
						<th>Last Name</th>
						<th>Email</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${Employees}" var="employee">
						<tr>
							<td><c:out value="${employee.id}" /></td>
							<td><c:out value="${employee.firstName}" /></td>
							<td><c:out value="${employee.lastName}" /></td>
							<td><c:out value="${employee.email}" /></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</div>
</body>
<style>
.collapsible {
	background-color: #777;
	color: white;
	cursor: pointer;
	padding: 18px;
	width: 100%;
	border: none;
	text-align: left;
	outline: none;
	font-size: 15px;
}

.active, .collapsible:hover {
	background-color: #555;
}

.collapsible:after {
	content: '\002B';
	color: white;
	font-weight: bold;
	float: right;
	margin-left: 5px;
}

.active:after {
	content: "\2212";
}

.content {
	padding: 0 18px;
	max-height: 0;
	overflow: hidden;
	transition: max-height 0.2s ease-out;
	background-color: #f1f1f1;
}
</style>

<script>
	var coll = document.getElementsByClassName("collapsible");
	var i;

	for (i = 0; i < coll.length; i++) {
		coll[i].addEventListener("click", function() {
			this.classList.toggle("active");
			var content = this.nextElementSibling;
			if (content.style.maxHeight) {
				content.style.maxHeight = null;
			} else {
				content.style.maxHeight = content.scrollHeight + "px";
			}
		});
	}
</script>
</html>