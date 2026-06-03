package [=model.uri].mapper;

import [=model.uri].dto.[=model.name?cap_first]Dto;
import [=model.uri].entities.[=model.name?cap_first];
[#list model.properties?values as property]
[#if property.relationship?? && property.relationship != "ManyToOne"]
import [=model.uri].entities.[=property.type?replace("List<","")?replace(">","")];
[/#if]
[/#list]
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class [=model.name?cap_first]Mapper {

    public [=model.name?cap_first]Dto toDto([=model.name?cap_first] entity) {
        if (entity == null) {
            return null;
        }

        [=model.name?cap_first]Dto dto = new [=model.name?cap_first]Dto();
[#list model.properties?values as property]
[#if !property.relationship??]
        dto.set[=property.name?cap_first](entity.get[=property.name?cap_first]());
[/#if]
[/#list]

[#list model.properties?values as property]
[#if property.relationship??]
[#if property.relationship == "ManyToOne"]
        // Map [=property.name] to ID
        if (entity.get[=property.name?cap_first]() != null) {
            dto.set[=property.name?cap_first](entity.get[=property.name?cap_first]().getId());
        }
[#else]
        if (entity.get[=property.name?cap_first]() != null) {
            List<Integer> [=property.name?lower_case]Ids = entity.get[=property.name?cap_first]().stream()
                    .map([=property.type?replace("List<","")?replace(">","")]::getId)
                    .collect(Collectors.toList());
            dto.set[=property.name?cap_first]([=property.name?lower_case]Ids);
        }
[/#if]
[/#if]
[/#list]

        return dto;
    }

    public [=model.name?cap_first] toEntity([=model.name?cap_first]Dto dto) {
        if (dto == null) {
            return null;
        }

        [=model.name?cap_first] entity = new [=model.name?cap_first]();
[#list model.properties?values as property]
[#if !property.relationship??]
        entity.set[=property.name?cap_first](dto.get[=property.name?cap_first]());
[/#if]
[/#list]
        return entity;
    }
    public List<[=model.name?cap_first]Dto> toDtoList(List<[=model.name?cap_first]> entityList) {
        if (entityList == null) {
            return null;
        }
        return entityList.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public List<[=model.name?cap_first]> toEntityList(List<[=model.name?cap_first]Dto> dtoList) {
        if (dtoList == null) {
            return null;
        }
        return dtoList.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }
}