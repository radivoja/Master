package com.project.service;

import com.project.entities.[=model.name?cap_first];
import com.project.repositories.[=model.name?cap_first]Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class [=model.name?cap_first]Service {

    private final [=model.name?cap_first]Repository [=model.name]Repository;

    public [=model.name?cap_first]Service([=model.name?cap_first]Repository [=model.name]Repository) {
        this.[=model.name]Repository = [=model.name]Repository;
    }

    public List<[=model.name?cap_first]> get[=model.name?cap_first]List() {
        return [=model.name]Repository.findAll();
    }

    [#if pageable(model)]
    public Page<[=model.name?cap_first]> get[=model.name?cap_first]Page(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo-1, pageSize);
        return [=model.name]Repository.findAll(pageable);
    }
    [/#if]
    public boolean hasId(int id) {
        return [=model.name]Repository.findById(id).isPresent();
    }

    public void save[=model.name?cap_first]([=model.name?cap_first] [=model.name]) {
        [=model.name]Repository.save([=model.name]);
    }

    public Optional<[=model.name?cap_first]> find[=model.name?cap_first]ById(int id) {
        return [=model.name]Repository.findById(id);
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