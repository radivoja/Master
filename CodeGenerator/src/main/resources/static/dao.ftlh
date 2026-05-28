package [=model.uri].dao;
import java.util.List;
import java.util.Date;
import jakarta.validation.constraints.*;


public class [=model.name?cap_first]Dao {
[#list model.properties?values as property]
    [#if property.relationship??]
    [#if property.relationship == "ManyToOne"]
    private Integer [=property.name?uncap_first];
    [#else]
    private List<Integer> [=property.name?uncap_first];
    [/#if]
    [#else]
        [#if property.type == 'String']
    @NotBlank
            [#if property.maxLength?? && property.minLength??]
    @Size(min = [=property.minLength], max = [=property.maxLength])
            [#elseif property.maxLength??]
    @Size(max = [=property.maxLength])
            [#elseif property.minLength??]
    @Size(min = [=property.minLength])
            [/#if]
        [/#if]
        [#if property.type == 'Integer' || property.type == 'Long']
            [#if property.maxLength??]
    @Max([=property.maxLength])
            [/#if]
            [#if property.minLength??]
    @Min([=property.minLength])
            [/#if]
        [/#if]
    private [=property.type] [=property.name?uncap_first];
    [/#if]
	
[/#list]
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
