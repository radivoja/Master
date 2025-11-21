package com.project.dao;
import java.util.List;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;


public class [=model.name?cap_first]Dao {
[@printProperty/]

[@printAccessor/]
    @Override
    public String toString() {
        return [=checkToString()]
    }
}

[#macro printProperty]
    [#list model.properties as property]
    [@printValidation property=property/]

    [#if property.relationship??]
    [#if property.relationship == "ManyToOne"]
    private Integer [=property.name?uncap_first]Id;
    [#else]
    private List<Integer> [=property.name?uncap_first]Ids;
    [/#if]
    [#else]
    private [=property.type] [=property.name?uncap_first];
    [/#if]
    [/#list]
[/#macro]


[#macro printValidation property]
    [#list property.stereotypes as stereo]
       [#if stereo.name == 'MyMetaModel:EntityProperty']
          [#if property.type == 'String']
    @NotBlank
             [#if stereo.maxLength?? && stereo.minLength??]
    @Size(min = [=stereo.minLength], max = [=stereo.maxLength])
             [#elseif stereo.maxLength??]
    @Size(max = [=stereo.maxLength])
             [#elseif stereo.minLength??]
    @Size(min = [=stereo.minLength])
             [/#if]
          [/#if]
          [#if property.type == 'Integer' || property.type == 'Long']
    @NotNull
             [#if stereo.maxLength??]
    @Max([=stereo.maxLength])
             [/#if]
             [#if stereo.minLength??]
    @Min([=stereo.minLength])
             [/#if]
          [/#if]
       [/#if]
       [#if stereo.name == 'MyMetaModel:Key']
    @NotNull
       [/#if]
       [#if stereo.name == 'MyMetaModel:Unique']
          [#if property.type == 'String']
    @NotBlank
          [#else]
    @NotNull
          [/#if]
       [/#if]
    [/#list]
[/#macro]

[#-- Print getters and setters with CONSISTENT lowercase naming --]
[#macro printAccessor]
[#list model.properties as property]
    [#if property.relationship??]
    [#if property.relationship == "ManyToOne"]
    public Integer get[=property.name?cap_first?replace(" ","")]Id(){
       return [=property.name?uncap_first]Id;
    }
    public void set[=property.name?cap_first]Id(Integer [=property.name?lower_case]Id){
       this.[=property.name?uncap_first]Id = [=property.name?uncap_first]Id;
    }
    [#else]
    public List<Integer> get[=property.name?cap_first?replace(" ","")]Ids(){
       return [=property.name?uncap_first]Ids;
    }
    public void set[=property.name?cap_first]Ids(List<Integer> [=property.name?uncap_first]Ids){
       this.[=property.name?uncap_first]Ids = [=property.name?uncap_first]Ids;
    }
    [/#if]
    [#else]
    public [=property.type?no_esc] get[=property.name?cap_first?replace(" ","")](){
       return [=property.name?uncap_first];
    }
    public void set[=property.name?cap_first]([=property.type?no_esc] [=property.name?uncap_first]){
       this.[=property.name?uncap_first] = [=property.name?uncap_first];
    }
    [/#if]

[/#list]
[/#macro]

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

      [#return '"'?no_esc + model.name+ ':" + '?no_esc  + model.properties[0].name?uncap_first + ";"]
[/#function]