[#function relationship property]
    [#if property.relationship??]
        [#assign relation =  '@' + property.relationship]
        [#if property.mappedBy??]
            [#return relation + '(mappedBy="'?no_esc + property.mappedBy?lower_case+ '")'?no_esc]
        [/#if]
        [#return relation]
    [#else]
        [#return ""]
    [/#if]
[/#function]

package com.project.entity;
import java.util.List;
import jakarta.persistence.*;


@Entity
public class [=model.name?cap_first] {
[#list model.properties as property]
	[=relationship(property)][#if property.name?lower_case == 'id']@Id[/#if]
    private [=property.type?no_esc] [=property.name];

[/#list]
[#list model.properties as property]
	public [=property.type?no_esc] get[=property.name?cap_first?replace(" ","")](){
		return [=property.name?replace(" ", "")];
	}
	public void set[=property.name?cap_first]([=property.type?no_esc] [=property.name]){
		this.[=property.name?replace(" ", "")] = [=property.name?replace(" ", "")];
	}
[/#list]

	@Override
	public String toString() {
		return "[=model.name]: " + [=model.properties[1].name];
	}
}