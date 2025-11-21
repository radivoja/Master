<html xmlns:th="http://www.thymeleaf.org">
[@topHeader item=model/]
	<body>
		<form th:object="${[=model.name]}" method="POST" class="container-fluid" style="margin-top: 20px">
[#list model.properties as property]
	[#if property.name?uncap_first == 'id']
	    [@idMacro/]
	[#elseif property.relationship?? && property.relationship == 'ManyToOne']
        [@manyToOne property=property/]
	[#elseif !property.relationship??]
	    [@normal property=property/]
	[#-- Skip OneToMany/ManyToMany in form --]
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
					    <input type="text" th:field="*{id}">
				    </th:block>
				<th:block th:unless="${[=model.name].id == null}">
					<input type="hidden" th:text="${[=model.name].id}" th:field="*{id}" />
				</th:block>
				<div style="margin-left:20px;" th:if="${#fields.hasErrors('id')}" th:errors="*{id}"> Error</div>
			</div>
[/#macro]

[#-- ManyToOne macro - now uses the ID field from DAO  --]
[#macro manyToOne property]
		<div class="form-group row">
			<label class="col-2 col-form-label">[=property.name?cap_first]:</label>
			<select th:field="*{[=property.name?uncap_first]Id}">
				<option value="">-- Select [=property.name?cap_first] --</option>
				<option th:each="dropDownItem : ${[=property.name?uncap_first]List}"
					th:value="${dropDownItem.id}"
					th:text="${dropDownItem}">
			</select>
			<div style="margin-left:20px;" th:if="${#fields.hasErrors('[=property.name?uncap_first]Id')}" th:errors="*{[=property.name?uncap_first]Id}">Error</div>
		</div>
[/#macro]

[#-- normal macro  --]
[#macro normal property]
		<div class="form-group row">
			<label class="col-2 col-form-label">[=property.name?cap_first]:</label>
			<input type="text" th:field="*{[=property.name?uncap_first]}"/>
			<div style="margin-left:20px;" th:if="${#fields.hasErrors('[=property.name?uncap_first]')}" th:errors="*{[=property.name?uncap_first]}">Error</div>
		</div>
[/#macro]