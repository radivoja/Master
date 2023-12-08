<#assign formActionParam = "@" + "{"+ "/"+ "${model.name?lower_case}Post" + "}">
<#assign formModelName = "$" + "{"+ "${model.name}" + "}">

<html xmlns:th="http://www.thymeleaf.org">
	<head>
		<h1 style="text-align: center;">${model.name}</h1>
		<title style="text-align: center;">${model.name}</title>
	</head>
	<body style="background-color:aquamarine;">
		<form th:action="${formActionParam}" th:object="${formModelName?lower_case}" method="POST">
<#list model.properties as property>
			<div>			
			<label>${property.name?cap_first}:</label>
			<input type="text" th:field="*{${property.name}}"/>
			</div>

</#list>
            	<button type="submit">Create</button>
		</form> 
	</body>
</html>

<style>
	div {
	  margin-bottom: 10px;
	}
	label {
	  display: inline-block;
	  width: 150px;
	  text-align: right;
	}
 </style>