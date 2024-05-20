package com.project.entities;
import java.util.List;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;

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
	[=relationship(property)][#if property.name?lower_case == 'id']@Id[='\n\t']@NotNull[/#if]
	[@printValidation property=property/]
    private [=property.type?no_esc] [=property.name];
    [/#list]
	
[/#macro]


[#macro printValidation property]
	[#list property.stereotypes as stereo]
		[#if stereo.name == 'MyMetaModel:EntityProperty']
			[#if property.type == 'String']
	@NotBlank
				[#if stereo.maxLenght?? && stereo.minLenght??]
		@Size([=stereo.minLength]??, [=stereo.maxLenght]??)
				[/#if]
			[/#if]
			[#if property.type =='Integer']
	@NotNull
				[#if stereo.maxLenght??]
		@Max([=stereo.maxLenght])
				[/#if]
				[#if stereo.minLenght??]
		@Min([=stereo.minLenght])
				[/#if]
			[/#if]
		[/#if]
	[/#list]
[/#macro]

[#-- Print getters and setters --]
[#macro printAccessor]
[#list model.properties as property]
	public [=property.type?no_esc] get[=property.name?cap_first?replace(" ","")](){
		return [=property.name?replace(" ", "")];
	}
	public void set[=property.name?cap_first]([=property.type?no_esc] [=property.name]){
		this.[=property.name?replace(" ", "")] = [=property.name?replace(" ", "")];
	}
[/#list]

[/#macro]

[#-- Relationship checking--]
[#function relationship property]
    [#if property.relationship??]
        [#assign relation =  '@' + property.relationship]
        [#if property.mappedBy??]
            [#return '\n' + '\t' + relation + '(mappedBy="'?no_esc + property.mappedBy?lower_case+ '")'?no_esc]
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
                [#assign retValue = retValue + '"[=property.name]" + '?no_esc + property.name]
            [/#if]
        [/#list]
     [/#list]
     [#if retValue?has_content]
        [#return retValue + ";"]
     [/#if]

      [#return '"'?no_esc + model.name+ ':"+'?no_esc  + model.properties[0].name + ";"]
[/#function]
