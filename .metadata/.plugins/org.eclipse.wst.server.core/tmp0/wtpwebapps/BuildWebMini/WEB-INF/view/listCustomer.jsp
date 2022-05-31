<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
	

<!-- //jstl tag lib -->
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>List Of Customer</title>
</head>
<body>
	<div id="wrapper">
		<div id="header">
			<h2>CRM - Customer RelationShip Manager</h2>
		</div>
		<div id="container">
			<div id="content">
				<table>
					<tr>
						<th>FirstName</th>
						<th>lastName</th>
						<th>email</th>
					</tr>

					<!-- Loop over and add customer details to web page -->
					<c:forEach items="${user}" var="user">
						<tr>
							<td>${user.firstName}</td>
							<td>${user.lastName}</td>
							<td>${user.email}</td>

						</tr>

					</c:forEach>
				</table>

			</div>

		</div>
	</div>
</body>
</html>