package com.project.domain;

import com.project.stereotype.MVCList;
import com.project.stereotype.MVCForm;
import com.project.stereotype.SortBy;

import java.util.LinkedHashMap;
import java.util.Map;

public class DomainClass {
    private String name;
    private String uri;
    private Map<String, DomainProperty> properties = new LinkedHashMap<>();
    private MVCList listView;
    private MVCForm formView;
    private SortBy sortBy;

    public SortBy getSortBy() {
        return sortBy;
    }

    public void setSortBy(SortBy sortBy) {
        this.sortBy = sortBy;
    }

    public MVCForm getFormView() {
        return formView;
    }

    public void setFormView(MVCForm formView) {
        this.formView = formView;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public Map<String, DomainProperty> getProperties() {
        return properties;
    }

    public void setProperties(Map<String, DomainProperty> properties) {
        this.properties = properties;
    }

    public MVCList getListView() {
        return listView;
    }

    public void setListView(MVCList listView) {
        this.listView = listView;
    }

    @Override
    public String toString() {
        return "DomainClass{" +
                "name='" + name + '\'' +
                ", uri='" + uri + '\'' +
                ", properties=" + properties +
                ", listView=" + listView +
                ", formView=" + formView +
                '}';
    }
}
