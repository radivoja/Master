package com.project.model;

import java.util.ArrayList;
import java.util.List;

public class UmlClass {
    private String id;
    private String name;
    private boolean isEntity;
    private boolean formView;
    private boolean listView;
    private String uri;
    private List<Property> properties = new ArrayList<>();
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public List<Property> getProperties() {
        return properties;
    }
    public void setProperties(List<Property> properties) {
        this.properties = properties;
    }
    @Override
    public String toString() {
        return "Model [id=" + id + ", name=" + name + ", properties=" + properties + "]";
    }


    public boolean isEntity() {
        return isEntity;
    }

    public void setEntity(boolean entity) {
        isEntity = entity;
    }


    public String getUri() {
        return uri;
    }
    public void setUri(String uri) {
        this.uri = uri;
    }

    public boolean isFormView() {
        return formView;
    }
    public void setFormView(boolean formView) {
        this.formView = formView;
    }
    public boolean isListView() {
        return listView;
    }

    public void setListView(boolean listView) {
        this.listView = listView;
    }
}
