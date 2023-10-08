<html xmlns:th="http://www.thymeleaf.org">
	<head>
		<title> Index Page</title>
<#list list as item>
	<h1 style="text-align: center;"> <a th:href="@{/${item.className?lower_case}List}"> ${item.className} </a> </h1>
</#list>		
	</head>	
</html>	