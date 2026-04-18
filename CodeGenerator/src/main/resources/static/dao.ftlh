package [=model.uri].dao;
import java.util.List;
import java.util.Date;
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
    return "[=model.name]{" +
[#list model.properties?values as property]
        "[=property.name?uncap_first]=" + [=property.name?uncap_first][#if property?has_next] + ", " +[/#if]
[/#list]
        +"}";
    }
}

[#macro printProperty]
    [#list model.properties?values as property]
   [#-- [@printValidation property=property/]  --]

    [#if property.relationship??]
    [#if property.relationship == "ManyToOne"]
    private Integer [=property.name?uncap_first];
    [#else]
    private List<Integer> [=property.name?uncap_first];
    [/#if]
    [#else]
    private [=property.type] [=property.name?uncap_first];
    [/#if]
    [/#list]
[/#macro]


[#macro printValidation property]
    [#list property.stereotypes?values as stereo]
       [#if stereo.name == 'SpringBootMVCProfile:MVCProperty']
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
[#list model.properties?values as property]
    [#if property.relationship??]
    [#if property.relationship == "ManyToOne"]
    public Integer get[=property.name?cap_first?replace(" ","")](){
       return [=property.name?uncap_first];
    }
    public void set[=property.name?cap_first](Integer [=property.name?lower_case]Id){
       this.[=property.name?uncap_first] = [=property.name?uncap_first];
    }
    [#else]
    public List<Integer> get[=property.name?cap_first?replace(" ","")](){
       return [=property.name?uncap_first];
    }
    public void set[=property.name?cap_first](List<Integer> [=property.name?uncap_first]){
       this.[=property.name?uncap_first] = [=property.name?uncap_first];
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
