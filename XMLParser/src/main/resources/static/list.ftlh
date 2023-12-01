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
<#assign listSting = "$" + "{"+ "${model.name?lower_case}List" + "}">
<#assign editPar = "$" + "{"+ "${model.name?lower_case}" + ".id"+"})}">

<html xmlns:th="http://www.thymeleaf.org">
	<head>
		<title> ${model.name}</title>
	</head>	
	<body>
	<a th:href="@{${model.name?lower_case}Form}"> ${model.name} </a> </div>
	<table border='1'>
		<thead>
			<tr>
<#list model.properties as property>
				<th> ${property.name} </th>
</#list>


				<th> Edit </th>
			</tr>
		</thead>
		<tbody>
			<tr th:each="${model.name?lower_case} : ${listSting}">
<#list model.properties as property>
<#assign name = "$" + "{"+ "${model.name?lower_case}.${property.name}" + "}">
				<td th:text="${name?replace(" ", "")}"></td>
</#list>
				<td><a th:href="@{/${model.name?lower_case}Edit/{id}(id=${editPar}">Edit</a></td>
			<tr>
		</tbody>
	</table>
	</body>
</html>
