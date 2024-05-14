<html xmlns:th="http://www.thymeleaf.org">
[@topHeader item=model/]
	<body>
		<form th:object="${[=model.name]}" method="POST" class="container-fluid" style="margin-top: 20px">
[#list model.properties as property]
	[#if property.name == 'Id']
	    [@idMacro/]
	[#elseif property.relationship?exists && property.relationship == 'ManyToOne']
        [@manyToOne property=property/]
	[#else]
	    [@normal property=property/]
	[/#if]
[/#list]
		<button type="submit" th:text="${[=model.name].id == null} ? 'Create' : 'Update'"></button>
		</form> 
	</body>
</html>

[#-- Top header--]
[#macro topHeader item]
	<link th:href="@{/style.css}" rel="stylesheet" />
	<head>
		<title>[=item.name?cap_first]</title>
	</head>
	<header class="header">
    	<a class="dd-toggle" th:href="@{/index}">Index</a>
    	<a class="dd-toggle" th:href="@{/[=item.name]Form}">[=item.name?cap_first] Form</a>
		<a class="dd-toggle" th:href="@{/[=item.name]List}">[=item.name?cap_first] List</a>
	</header>
[/#macro]

[#-- Id macro  --]
[#macro idMacro]
			<div class="form-group row">
				<label class="col-2 col-form-label">Id</label>
				    <th:block th:if="${[=model.name].id == null}">
					    <input type="text" th:field="*{Id}">
				    </th:block>
				<th:block th:unless="${[=model.name].id == null}">
					<input type="hidden" th:text="${[=model.name].Id}" th:field="*{Id}" />
				</th:block>
				<div style="margin-left:20px;" th:if="${#fields.hasErrors('Id')}" th:errors="*{Id}"> Error</div>
			</div>
[/#macro]

[#-- ManyToOne macro  --]
[#macro manyToOne property]
		<div class="form-group row">
			<label class="col-2 col-form-label">Domacin:</label>
			<select th:field="*{[=property.name]}">
				<option th:each="dropDownItem : ${[=property.name]}"
					th:value="${dropDownItem.Id}"
					th:text="${dropDownItem}">
			</select>
		</div>
[/#macro]

[#-- normal macro  --]
[#macro normal property]
		<div class="form-group row">
			<label class="col-2 col-form-label">[=property.name]:</label>
			<input type="text" th:field="*{[=property.name]}"/>
			<div style="margin-left:20px;" th:if="${#fields.hasErrors('[=property.name]')}" th:errors="*{[=property.name]}">Error</div>
		</div>
[/#macro]