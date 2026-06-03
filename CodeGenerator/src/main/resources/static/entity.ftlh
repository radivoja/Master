package [=model.uri].entities;
import java.util.List;
import java.time.LocalDate;
import jakarta.persistence.*;

@Entity
public class [=model.name?cap_first] {
[#list model.properties?values as property]
	[=checkRelationship(property)][#if property.id??]@Id
    @GeneratedValue(strategy = GenerationType.[=(property.id.strategy)!"IDENTITY"])[/#if]
    private [=property.type?no_esc] [=property.name?uncap_first];
[/#list]

[#list model.properties?values as property]
	public [=property.type?no_esc] get[=property.name?cap_first?replace(" ","")](){
		return [=property.name?replace(" ", "")?uncap_first];
	}
	public void set[=property.name?cap_first]([=property.type?no_esc] [=property.name?uncap_first]){
		this.[=property.name?replace(" ", "")?uncap_first] = [=property.name?replace(" ", "")?uncap_first];
	}
[/#list]
    @Override
    public String toString() {
    return "[=model.name]{" +
[#list model.properties?values as property]
        "[=property.name?uncap_first]=" + [=property.name?uncap_first][#if property?has_next] + ", " +[/#if]
[/#list] 
        +"}";
    }
}

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
