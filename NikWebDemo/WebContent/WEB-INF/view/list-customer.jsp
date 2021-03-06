<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<!-- //jstl tag lib -->
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Show Customer Records</title>
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
					<c:forEach items="${customers}" var="Customer">
						<tr>
							<td>${Customer.firstName}</td>
							<td>${Customer.lastName}</td>
							<td>${Customer.email}</td>

						</tr>

					</c:forEach>
				</table>

			</div>

		</div>
	</div>


</body>
</html>