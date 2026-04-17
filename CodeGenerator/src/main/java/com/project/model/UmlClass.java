package com.project.model;

import com.project.stereotype.Id;
import com.project.stereotype.ListView;

import java.util.ArrayList;
import java.util.List;

public class UmlClass {
    private String xmiId;
    private String name;
    private ListView listView;
    private String uri;
    private List<UmlProperty> properties = new ArrayList<>();
    private Id idProperty;

    public Id getIdProperty() {
        return idProperty;
    }

    public void setIdProperty(Id idProperty) {
        this.idProperty = idProperty;
    }

    public String getXmiId() {
        return xmiId;
    }
    public void setXmiId(String xmiId) {
        this.xmiId = xmiId;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public List<UmlProperty> getProperties() {
        return properties;
    }
    public void setProperties(List<UmlProperty> properties) {
        this.properties = properties;
    }

    public String getUri() {
        return uri;
    }
    public void setUri(String uri) {
        this.uri = uri;
    }

    public ListView getListView() {
        return listView;
    }
    public void setListView(ListView listView) {
        this.listView = listView;
    }

    @Override
    public String toString() {
        return "UmlClass{" +
                "xmiId='" + xmiId + '\'' +
                ", name='" + name + '\'' +
                ", listView=" + listView +
                ", uri='" + uri + '\'' +
                ", properties=" + properties +
                ", idProperty=" + idProperty +
                '}';
    }
}
