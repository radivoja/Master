<html xmlns:th="http://www.thymeleaf.org">
[@topHeader item=model/]
    <body>
[@pageableLinks/]
        <table class="table table-striped table-bordered table-hover">
[@tableHeader item=model/]
[@tableBody item=model/]
		</table>
	</body>
</html>

[#-- Top header--]
[#macro topHeader item]
	<link th:href="@{/style.css}" rel="stylesheet" />
	<head>
		<title>[=item.name?cap_first]</title>
	</head>
	<header class="header">
    	<a class="dd-toggle" th:href="@{index}">Index</a>
    	<a class="dd-toggle" th:href="@{[=item.name]Form}">[=item.name?cap_first] Form</a>
		<a class="dd-toggle" th:href="@{[=item.name]List}">[=item.name?cap_first] List</a>
	</header>
[/#macro]

[#-- Pageable links --]
[#macro pageableLinks]
[#if model.pageable??]
        <span><a th:if="${pageNo > 1}" th:href="@{'?pageNo='} + ${pageNo-1} + '&pageSize=' + ${pageSize}">Previous Page</a></span>
        <span text-align="middle"><a th:if="${[=model.name]List.number < [=model.name].totalPages-1}" th:href="@{'?pageNo='} + ${pageNo+1} + '&pageSize=' + ${pageSize}">Next Page</a></span>
[/#if]
[/#macro]

[#-- Table header--]
[#macro tableHeader item]
            <thead>
                <tr>
[#list item.properties as property]
				    <th> [=property.name] </th>
[/#list]
				    <th> Edit </th>
				    <th> Delete </th>
			    </tr>
		    </thead>
[/#macro]

[#-- Table body--]
[#macro tableBody item]
		    <tbody>
				<tr th:each="[=item.name] : ${[=item.name]List}">
[@linkedEntity item=model/]
[@editLink item=model/]
[@delete item=model/]
				<tr>
			</tbody>
[/#macro]

[#-- Check if theres exist composite element and writes his link  --]
[#macro linkedEntity item]
		[#list item.properties as property]
    [#if property.relationship?exists && property.relationship == 'ManyToOne']
        <td> <a th:href="@{/[=property.name]Edit/{id}(id=${[=item.name]?.[=property.name].id})} "> <div th:text="${[=item.name]?.[=property.name]}"></div> </a></td>
    [#else]
        			<td th:text="${[=item.name].[=property.name]}"></td>
    [/#if]
[/#list]
[/#macro]

[#-- Link to form to edit  --]
[#macro editLink item]
        			<td><a th:href="@{/[=item.name]Form/__${[=item.name].id}__/edit}"> Edit </a></td>
[/#macro]

[#-- Delete item  --]
[#macro delete item]
					<td><a th:href="@{/delete[=item.name?cap_first](id=${[=item.name].id})}"> Delete </a></td>
[/#macro]



