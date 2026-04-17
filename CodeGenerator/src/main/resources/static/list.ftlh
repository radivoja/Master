<html xmlns:th="http://www.thymeleaf.org">
	<link th:href="@{/style.css}" rel="stylesheet" />
	<head>
		<title>[=model.name?cap_first]</title>
	</head>
	[#-- Top header--]
	<header class="header">
    	<a class="dd-toggle" th:href="@{index}">Index</a>
    	<a class="dd-toggle" th:href="@{[=model.name]Form}">[=model.name?cap_first] Form</a>
		<a class="dd-toggle" th:href="@{[=model.name]List}">[=model.name?cap_first] List</a>
	</header>
    <body>
	[#-- Pageable links --]
        <span><a th:if="${pageNo > 1}" th:href="@{'?pageNo='} + ${pageNo-1} + '&pageSize=' + ${pageSize}">Previous Page</a></span>
        <span text-align="middle"><a th:if="${[=model.name]List.number < [=model.name]List.totalPages-1}" th:href="@{'?pageNo='} + ${pageNo+1} + '&pageSize=' + ${pageSize}">Next Page</a></span>

        <table class="table table-striped table-bordered table-hover">
[#-- Table header--]
            <thead>
                <tr>
[#list model.properties?values as property]
    [#-- Only show non-OneToMany/ManyToMany properties in list view --]
    [#if !property.relationship?? || property.relationship == 'ManyToOne']
				    <th> [=property.name] </th>
    [/#if]
[/#list]
[#if ((model.listView.editEnabled)!"") == "true"]
				    <th> Edit </th>
[/#if]
[#if ((model.listView.deleteEnabled)!"") == "true"]
					<th> Delete </th>
[/#if]
			    </tr>
		    </thead>
[#-- Table body--]
		    <tbody>
				<tr th:each="[=model.name] : ${[=model.name]List}">
[@displayProperties model=model/]
[#if ((model.listView.editEnabled)!"") == "true"]
[@editLink model=model/]
[/#if]
[#if model.listView??]
    [#if ((model.listView.deleteEnabled)!"") == "true"]
[@delete model=model/]
    [/#if]
[/#if]
				<tr>
			</tbody>
		</table>
	</body>
</html>

[#-- Display properties --]
[#macro displayProperties model]
[#list model.properties?values as property]
    [#if !property.relationship??]
        [#-- Regular property: just display it --]
        			<td th:text="${[=model.name].[=property.name?uncap_first]}"></td>
    [#elseif property.relationship == 'ManyToOne']
        [#-- ManyToOne: DAO has the ID, display as link or just the ID --]
        			<td th:text="${[=model.name].[=property.name?uncap_first]Id}"></td>
    [#-- Skip OneToMany and ManyToMany relationships in list view --]
    [/#if]
[/#list]
[/#macro]

[#-- Link to form to edit  --]
[#macro editLink model]
        			<td><a th:href="@{/[=model.name]Form/__${[=model.name].id}__/edit}"> Edit </a></td>
[/#macro]

[#-- Delete model  --]
[#macro delete model]
					<td><a th:href="@{/delete[=model.name?cap_first](id=${[=model.name].id})}"> Delete </a></td>
[/#macro]