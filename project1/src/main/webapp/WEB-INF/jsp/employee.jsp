<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
	<title>Spring 4 MVC Hibernate4 - Employees</title>
	<style type="text/css">
		body {
			font-family: sans-serif;
		}
		.data, .data td {
			border-collapse: collapse;
			width: 100%;
			border: 1px solid #aaa;
			margin: 2px;
			padding: 2px;
		}
		.data th {
			font-weight: bold;
			background-color: #5C82FF;
			color: white;
		}
	</style>
</head>
<body>

<h2>Manage Employees</h2>

<form:form method="post" action="add.html" commandName="employee">

	<table>
	<tr>
		<td><form:label path="name"><spring:message code="label.name"/></form:label></td>
		<td><form:input path="name" /></td> 
	</tr>
	<tr>
		<td><form:label path="employeeId"><spring:message code="label.empid"/></form:label></td>
		<td><form:input path="employeeId" /></td>
	</tr>
	<tr>
		<td><form:label path="department"><spring:message code="label.dept"/></form:label></td>
		<td><form:input path="department" /></td>
	</tr>
	
	<tr>
		<td colspan="2">
			<input type="submit" value="<spring:message code="label.addemployee"/>"/>
		</td>
	</tr>
</table>	
</form:form>

	
<h3>Employees</h3>
<c:if  test="${!empty empList}">
<table class="data">
<tr>
	<th>Name</th>
	<th>EmpId</th>
	<th>Dept</th>
	<th>&nbsp;</th>
</tr>
<c:forEach items="${empList}" var="employee">
	<tr>
		<td>${employee.name} </td>
		<td>${employee.employeeId}</td>
		<td>${employee.department}</td>
		<td><a href="delete/${employee.id}">delete</a></td>
	</tr>
</c:forEach>
</table>
</c:if>


</body>
</html>
