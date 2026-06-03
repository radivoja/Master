<html xmlns:th="http://www.thymeleaf.org">
[@topHeader item=model/]
	<body>
		<form th:object="${[=model.name?uncap_first]}" method="POST" class="container-fluid" style="margin-top: 20px">
[#list model.properties?values as property]
	[#if property.name?uncap_first == 'id']
	    [@idMacro/]
	[#elseif property.relationship?? && property.relationship == 'ManyToOne']
        [@manyToOne property=property/]
	[#elseif !property.relationship?? && property.type == 'LocalDate']
	    [@dateField property=property/]
	[#elseif !property.relationship??]
	    [@normal property=property/]
	[#-- Skip OneToMany/ManyToMany in form --]
	[/#if]
[/#list]
		<button type="submit" th:text="${[=model.name?uncap_first].id == null} ? 'Create' : 'Update'"></button>
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
    	<a class="dd-toggle" th:href="@{/}">Index</a>
    	<a class="dd-toggle" th:href="@{/[=item.name?uncap_first]Form}">[=item.name?cap_first] Form</a>
		<a class="dd-toggle" th:href="@{/[=item.name?uncap_first]List}">[=item.name?cap_first] List</a>
	</header>
[/#macro]

[#-- Id macro  --]
[#macro idMacro]
			<div class="form-group row" th:if="${[=model.name?uncap_first].id != null}">
				<label class="col-2 col-form-label">Id</label>
				<span th:text="${[=model.name?uncap_first].id}" style="padding: 7px 0;"></span>
				<input type="hidden" th:field="*{id}"/>
			</div>
[/#macro]

[#-- ManyToOne macro - now uses the ID field from DTO  --]
[#macro manyToOne property]
		<div class="form-group row">
			<label class="col-2 col-form-label">[=property.name?cap_first]:</label>
			<select th:field="*{[=property.name?uncap_first]}">
				<option value="">-- Select [=property.name?cap_first] --</option>
				<option th:each="dropDownItem : ${[=property.name?uncap_first]List}"
					th:value="${dropDownItem.id}"
					th:text="${dropDownItem}">
			</select>
			<div style="margin-left:20px;" th:if="${#fields.hasErrors('[=property.name?uncap_first]')}" th:errors="*{[=property.name?uncap_first]}">Error</div>
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


[#-- date macro  --]
[#macro dateField property]
		<div class="form-group row">
			<label class="col-2 col-form-label">[=property.name?cap_first]:</label>
			<input type="date" th:field="*{[=property.name?uncap_first]}"/>
			<div style="margin-left:20px;" th:if="${#fields.hasErrors('[=property.name?uncap_first]')}" th:errors="*{[=property.name?uncap_first]}">Error</div>
		</div>
[/#macro]