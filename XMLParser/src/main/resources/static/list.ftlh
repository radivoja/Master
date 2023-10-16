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
<#assign listSting = "$" + "{"+ "${model.className?lower_case}List" + "}">
<#assign editPar = "$" + "{"+ "${model.className?lower_case}" + ".id"+"})}">

<html xmlns:th="http://www.thymeleaf.org">
	<head>
		<title> ${model.className}</title>
	</head>	
	<body>
	<a th:href="@{${model.className?lower_case}Form}"> ${model.className} </a> </div>
	<table border='1'>
		<thead>
			<tr>
<#list model.properties as property>
				<th> ${property.name} </th>
</#list>


<#list model.associations as association>
<#if association.leftCardinality == 'One'>
				<th> ${association.rightEntity?cap_first}</th>
</#if>				
</#list>

				<th> Edit </th>
			</tr>
		</thead>
		<tbody>
			<tr th:each="${model.className?lower_case} : ${listSting}">
<#list model.properties as property>
<#assign name = "$" + "{"+ "${model.className?lower_case}.${property.name}" + "}">
				<td th:text="${name?replace(" ", "")}"></td>
</#list>
				<td><a th:href="@{/${model.className?lower_case}Edit/{id}(id=${editPar}">Edit</a></td>
			<tr>
		</tbody>
	</table>
	</body>
</html>
