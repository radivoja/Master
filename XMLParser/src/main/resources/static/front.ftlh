<style>
table {
  font-family: arial, sans-serif;
  border-collapse: collapse;
  width: 100%;
}

td, th {
  border: 1px solid #dddddd;
  text-align: left;
  padding: 8px;
}

td:nth-child(even) {
  background-color: #dddddd;
}
</style>
<#assign listSting = "$" + "{"+ "${model.className}List" + "}">
<html xmlns:th="http://www.thymeleaf.org">
	<head>
		<title> ${model.className}</title>
	</head>	
	<body>
	<a th:href="@{${model.className}}"> ${model.className} </a> </div>
	<table border='1'>
		<thead>
			<tr>
<#list model.properties as property>
				<th> ${property.name} </th>
</#list>
			</tr>
		</thead>
		<tbody>
			<tr th:each="${model.className} : ${listSting}">
<#list model.properties as property>
<#assign name = "$" + "{"+ "${model.className}.${property.name}" + "}">
				<td th:text="${name?replace(" ", "")}"></td>
</#list>
			<tr>
		</tbody>
	</table>
	</body>
</html>
