package com.project.service;

import com.project.dao.[=model.name?cap_first]Dao;
import com.project.entities.[=model.name?cap_first];
import com.project.repository.[=model.name?cap_first]Repository;
import com.project.mapper.[=model.name?cap_first]Mapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class [=model.name?cap_first]Service {
    private final [=model.name?cap_first]Repository [=model.name]Repository;
    private final [=model.name?cap_first]Mapper [=model.name]Mapper;

    public [=model.name?cap_first]Service([=model.name?cap_first]Repository [=model.name]Repository, [=model.name?cap_first]Mapper [=model.name]Mapper) {
        this.[=model.name]Repository = [=model.name]Repository;
        this.[=model.name]Mapper = [=model.name]Mapper;
    }

    public List<[=model.name?cap_first]Dao> get[=model.name?cap_first]List() {
        List<[=model.name?cap_first]> entities = [=model.name]Repository.findAll();
        return [=model.name]Mapper.toDaoList(entities);
    }

    [#if pageable(model)]
    public Page<[=model.name?cap_first]Dao> get[=model.name?cap_first]Page(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo-1, pageSize);
        Page<[=model.name?cap_first]> entityPage = [=model.name]Repository.findAll(pageable);
        return entityPage.map([=model.name]Mapper::toDao);
    }
    [/#if]

    public boolean hasId(int id) {
        return [=model.name]Repository.findById(id).isPresent();
    }

    public void save[=model.name?cap_first]([=model.name?cap_first]Dao dao) {
        [=model.name?cap_first] entity = [=model.name]Mapper.toEntity(dao);
        [=model.name]Repository.save(entity);
    }

    public Optional<[=model.name?cap_first]Dao> find[=model.name?cap_first]ById(int id) {
        return [=model.name]Repository.findById(id)
                .map([=model.name]Mapper::toDao);
    }

    public void delete[=model.name?cap_first](Optional<Integer> id) {
        id.ifPresent(integer -> [=model.name]Repository.deleteById(id.get()));
    }
}

[#function pageable model]
    [#if model.pageable??]
        [#return true]
    [#else]
        [#return false]
    [/#if]
[/#function]