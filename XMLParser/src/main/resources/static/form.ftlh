<html xmlns:th="http://www.thymeleaf.org">
	<head>
		<title style="text-align: center;">[=model.name?cap_first]</title>
		<link th:href="@{/style.css}" rel="stylesheet" />
	</head>

	<body>
		<header class="header">
			<a class="dd-toggle" th:href="@{index}">Index</a>
			<a class="dd-toggle" th:href="@{[=model.name]Form}">[=model.name?cap_first] Form</a>
			<a class="dd-toggle" th:href="@{[=model.name]List}">[=model.name?cap_first] List</a>
		</header>

		<form th:action="@{/[=model.name]Post}" th:object="${[=model.name]}" method="POST" class="container-fluid" style="margin-top: 20px">
[#list model.properties as property]
	[#if property.name == 'Id']
			<div class="form-group row">
				<label class="col-2 col-form-label">Id</label>
				<th:block th:if="${param.para == null}">
					<input type="text" th:field="*{Id}">
				</th:block>

				<th:block th:unless="${param.para == null}">
					<input type="text" th:field="*{Id}" disabled/>
					<input type="hidden" name="para" value="1" />
				</th:block>
				<div style="margin-left:20px;" th:if="${#fields.hasErrors('Id')}" th:errors="*{Id}"> Error</div>
			</div>

	[#elseif property.relationship?exists && property.relationship == 'ManyToOne']
		<div class="form-group row">
			<label class="col-2 col-form-label">Domacin:</label>
			<select th:field="*{[=property.name]}">
				<option th:each="dropDownItem : ${[=property.name]}"
					th:value="${dropDownItem.Id}"
					th:text="${dropDownItem}">
			</select>
		</div>
	[#else]
		<div class="form-group row">
			<label class="col-2 col-form-label">[=property.name]:</label>
			<input type="text" th:field="*{[=property.name]}"/>
			<div style="margin-left:20px;" th:if="${#fields.hasErrors('[=property.name]')}" th:errors="*{[=property.name]}">Error</div>
		</div>
	[/#if]
[/#list]
		<button type="submit" th:text="${param.para == null} ? 'Create' : 'Update'"></button>
		</form> 
	</body>
</html>