package com.project.entities;
import java.util.List;
import java.util.Date;
import jakarta.persistence.*;
import jakarta.annotation.Nonnull;

@Entity
public class ${model.className} {
<#list model.properties as property>
	<#if property.stereotype?exists && property.stereotype == "Id">
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	</#if>
	<#if property.length?has_content>@Column(length = "${property.length}") </#if>	
	private ${property.type?no_esc} ${property.name?replace(" ", "")};
	</#list>
<#list model.associations as association>
	@${association.leftCardinality}To${association.rightCardinality}<#if association.mappedBy?exists || association.leftCardinality= 'One'>(mappedBy="${model.className?lower_case}")</#if>
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

<#list model.associations as association>
<#if association.rightCardinality == 'Many'>
	public List<${association.rightEntity?cap_first}> get${association.rightEntity?cap_first?replace(" ","")}(){
		return ${association.rightEntity?replace(" ", "")};
	}
	public void set${association.rightEntity?cap_first?replace(" ", "")?no_esc}(List<${association.rightEntity?cap_first}> ${association.rightEntity}){
		this.${association.rightEntity?replace(" ", "")} = ${association.rightEntity?replace(" ", "")};
	}			
<#else>
	public ${association.rightEntity?cap_first} get${association.rightEntity?cap_first}(){
		return ${association.rightEntity};
	}
	public void set${association.rightEntity?cap_first?replace(" ", "")?no_esc}(${association.rightEntity?cap_first} ${association.rightEntity}){
		this.${association.rightEntity?replace(" ", "")} = ${association.rightEntity?replace(" ", "")};
	}		
</#if>
</#list>	

	@Override
	public String toString() {
		return "${model.className}[" + <#list model.properties as property>"${property.name}=" + ${property.name?replace(" ", "")} + </#list>"]";
	}
}
