package com.project.mapper;

import com.project.dao.[=model.name?cap_first]Dao;
import com.project.entities.[=model.name?cap_first];
[#list model.properties as property]
[#if property.relationship?? && property.relationship != "ManyToOne"]
import com.project.entities.[=property.type?replace("List<","")?replace(">","")];
[/#if]
[/#list]
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class [=model.name?cap_first]Mapper {

    public [=model.name?cap_first]Dao toDao([=model.name?cap_first] entity) {
        if (entity == null) {
            return null;
        }

        [=model.name?cap_first]Dao dao = new [=model.name?cap_first]Dao();
[#list model.properties as property]
[#if !property.relationship??]
        dao.set[=property.name?cap_first](entity.get[=property.name?cap_first]());
[/#if]
[/#list]

[#list model.properties as property]
[#if property.relationship??]
[#if property.relationship == "ManyToOne"]
        // Map [=property.name] to ID
        if (entity.get[=property.name?cap_first]() != null) {
            dao.set[=property.name?cap_first]Id(entity.get[=property.name?cap_first]().getId());
        }

[#else]
        if (entity.get[=property.name?cap_first]() != null) {
            List<Integer> [=property.name?lower_case]Ids = entity.get[=property.name?cap_first]().stream()
                    .map([=property.type?replace("List<","")?replace(">","")]::getId)
                    .collect(Collectors.toList());
            dao.set[=property.name?cap_first]Ids([=property.name?lower_case]Ids);
        }

[/#if]
[/#if]
[/#list]
        return dao;
    }

    public [=model.name?cap_first] toEntity([=model.name?cap_first]Dao dao) {
        if (dao == null) {
            return null;
        }

        [=model.name?cap_first] entity = new [=model.name?cap_first]();
[#list model.properties as property]
[#if !property.relationship??]
        entity.set[=property.name?cap_first](dao.get[=property.name?cap_first]());
[/#if]
[/#list]
        return entity;
    }
    public List<[=model.name?cap_first]Dao> toDaoList(List<[=model.name?cap_first]> entities) {
        if (entities == null) {
            return null;
        }
        return entities.stream()
                .map(this::toDao)
                .collect(Collectors.toList());
    }

    public List<[=model.name?cap_first]> toEntityList(List<[=model.name?cap_first]Dao> daos) {
        if (daos == null) {
            return null;
        }
        return daos.stream()
                .map(this::toEntity)
                .collect(Collectors.toList());
    }
}