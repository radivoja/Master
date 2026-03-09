package com.project.entities;
import java.util.List;
import jakarta.persistence.*;

@Entity
public class [=model.name?cap_first] {
[@printProperty/]
[@printAccessor/]
    @Override
    public String toString() {
        return [=checkToString()]
    }
}
[#-- Print properties --]
[#macro printProperty]
    [#list model.properties as property]
	[=checkRelationship(property)][#if property.name?uncap_first == 'id']@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)[/#if]
    private [=property.type?no_esc] [=property.name?uncap_first];
    [/#list]
	
[/#macro]

[#-- Print getters and setters --]
[#macro printAccessor]
[#list model.properties as property]
	public [=property.type?no_esc] get[=property.name?cap_first?replace(" ","")](){
		return [=property.name?replace(" ", "")?uncap_first];
	}
	public void set[=property.name?cap_first]([=property.type?no_esc] [=property.name?uncap_first]){
		this.[=property.name?replace(" ", "")?uncap_first] = [=property.name?replace(" ", "")?uncap_first];
	}
[/#list]

[/#macro]

[#-- Relationship checking--]
[#function checkRelationship property]
    [#if property.relationship??]
        [#assign relation =  '@' + property.relationship]
        [#if property.mappedBy??]
            [#return '\n' + '\t' + relation + '(mappedBy="'?no_esc + property.mappedBy?uncap_first+ '")'?no_esc]
        [/#if]
        [#return relation]
    [#else]
        [#return ""]
    [/#if]
[/#function]

[#-- Check toString --]
[#function checkToString]
    [#assign retValue = ""]
    [#list model.properties as property]
        [#list property.stereotypes as stereo]
            [#if stereo.name == "MyMetaModel:ToString"]
                [#if retValue?has_content]
                    [#assign retValue+=" + "]
                [/#if]
                [#assign retValue = retValue + '"[=property.name]" + '?no_esc + property.name?uncap_first]
            [/#if]
        [/#list]
     [/#list]
     [#if retValue?has_content]
        [#return retValue + ";"]
     [/#if]

      [#return '"'?no_esc + model.name+ ':"+'?no_esc  + model.properties[0].name?uncap_first + ";"]
[/#function]
