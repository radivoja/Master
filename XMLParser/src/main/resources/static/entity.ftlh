package com.project.entities;
import java.util.List;
import java.util.Date;
import jakarta.persistence.*;
import jakarta.annotation.Nonnull;


<#function checkType association>
	<#if association.rightCardinality == "rightCardinality" || association == "EBoolean">
  	<#return testSting?substring(1)>
  	<#else>
  	<#return association>
  	</#if>
</#function>



@Entity
public class ${model.className} {
<#list model.properties as property>
	<#if property.stereotype?exists>
	<#if property.stereotype == "Id">
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	</#if>
	</#if>
	<#if property.length?has_content>@Column(length = "${property.length}") </#if>	
	private ${property.type?no_esc} ${property.name?replace(" ", "")};
	</#list>
<#list model.associations as association>
	@${association.leftCardinality}To${association.rightCardinality}<#if association.mappedBy?exists>(mappedBy="${model.className?lower_case}")</#if>
	<#if association.rightCardinality == 'Many'>
	private List<${association.rightEntity?cap_first}> ${association.rightEntity};
	<#else>
	private ${association.rightEntity?cap_first} ${association.rightEntity};	
	</#if>
</#list>
	
<#list model.properties as property>
	public ${property.type?no_esc} get${property.name?cap_first?replace(" ","")}(){
		return ${property.name?replace(" ", "")};
	}
	public void set${property.name?cap_first?replace(" ", "")?no_esc}(${property.type?no_esc} ${property.name?replace(" ", "")}){
		this.${property.name?replace(" ", "")} = ${property.name?replace(" ", "")};
	}
	</#list>	

	@Override
	public String toString() {
		return "${model.className}[" + <#list model.properties as property>"${property.name}=" + ${property.name?replace(" ", "")} + </#list>"]";
	}
}
