package com.project.entities;
import java.util.List;
import jakarta.persistence.*;

@Entity
public class ${model.name} {
<#list model.properties as property>
	<#if property.relationship?exists>@${property.relationship}</#if><#if property.mappedBy?exists>(mappedBy="${property.mappedBy?lower_case}")</#if>
	<#if property.name?lower_case == 'id'> @Id</#if>
	private ${property.type?no_esc} ${property.name?replace(" ", "")};
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
		return "${model.name}[" + <#list model.properties as property>"${property.name}=" + ${property.name?replace(" ", "")} + </#list>"]";
	}
}
