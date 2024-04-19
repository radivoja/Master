
<html xmlns:th="http://www.thymeleaf.org">
	<link th:href="@{/style.css}" rel="stylesheet" />
	<head>
		<title>[=model.name]</title>
	</head>	
	<body>
		<header class="header">
    		<a class="dd-toggle" th:href="@{index}">Index</a>
    		<a class="dd-toggle" th:href="@{[=model.name]Form}">[=model.name?cap_first] Form</a>
			<a class="dd-toggle" th:href="@{[=model.name]List}">[=model.name?cap_first] List</a>
		</header>
		<table class="table table-striped table-bordered table-hover">
			<thead>
				<tr>
[#list model.properties as property]
					<th> [=property.name] </th>
[/#list]
					<th> Edit </th>
					<th> Delete </th>
				</tr>
			</thead>
			<tbody>		
				<tr th:each="[=model.name] : ${[=model.name]List}">
		[#list model.properties as property]
[#if property.relationship?exists && property.relationship == 'ManyToOne']
        <td> <a th:href="@{/[=property.name]Edit/{id}(id=${[=model.name]?.[=property.name].id})} "> <div th:text="${[=model.name]?.[=property.name]}"></div> </a></td>

[#else]	
        			<td th:text="${[=model.name].[=property.name]}"></td>
[/#if]
[/#list]
        			<td><a th:href="@{/[=model.name]Form(para=${[=model.name].id})}"> Edit </a></td>
					<td><a th:href="@{/delete[=model.name?cap_first](id=${[=model.name].id})}"> Delete </a></td>
				<tr>
			</tbody>
		</table>
	</body>
</html>
