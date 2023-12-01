<html xmlns:th="http://www.thymeleaf.org">
	<head>
		<title> Index Page</title>
<#list list as item>
	<h1 style="text-align: center;"> <a th:href="@{/${item.name?lower_case}List}"> ${item.name} </a> </h1>
</#list>		
	</head>	
</html>	