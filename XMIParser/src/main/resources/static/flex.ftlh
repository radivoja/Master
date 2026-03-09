
<html xmlns:th="http://www.thymeleaf.org">
	<head>
		<title> Essence Market</title>
	</head>	
	<a th:href="@{${model.className}}"> ${model.className} </a> </div>
	
	
	<#assign s = "$" + "{"+ "${model.className}List" + "}">
	
	}
	<tr th:each="${model.className} : "${s}">
<#list model.properties as property>
<#assign var = "$" + "{"+ "${model.className}.${property.name}" + "}">
		<td th:text="${var}"></td>
</#list>
	<tr>
</html>

