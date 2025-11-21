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
        <span text-align="middle"><a th:if="${[=model.name]List.number < [=model.name]List.totalPages-1}" th:href="@{'?pageNo='} + ${pageNo+1} + '&pageSize=' + ${pageSize}">Next Page</a></span>
[/#if]
[/#macro]

[#-- Table header--]
[#macro tableHeader item]
            <thead>
                <tr>
[#list item.properties as property]
    [#-- Only show non-OneToMany/ManyToMany properties in list view --]
    [#if !property.relationship?? || property.relationship == 'ManyToOne']
				    <th> [=property.name] </th>
    [/#if]
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
[@displayProperties item=model/]
[@editLink item=model/]
[@delete item=model/]
				<tr>
			</tbody>
[/#macro]

[#-- Display properties --]
[#macro displayProperties item]
[#list item.properties as property]
    [#if !property.relationship??]
        [#-- Regular property: just display it --]
        			<td th:text="${[=item.name].[=property.name?uncap_first]}"></td>
    [#elseif property.relationship == 'ManyToOne']
        [#-- ManyToOne: DAO has the ID, display as link or just the ID --]
        			<td th:text="${[=item.name].[=property.name?uncap_first]Id}"></td>
    [#-- Skip OneToMany and ManyToMany relationships in list view --]
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