<html xmlns:th="http://www.thymeleaf.org">
	<head>
		<title> Index Page</title>
[#list models as model]
	<h1 style="text-align: center;"> <a th:href="@{/[=model.name?lower_case]List}"> [=model.name?cap_first] </a> </h1>
[/#list]
	</head>	
</html>	