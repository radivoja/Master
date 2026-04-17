package com.project.model;

import java.util.ArrayList;
import java.util.List;

public class UmlClass {
    private String xmiId;
    private String name;
    private String uri;
    private List<UmlProperty> properties = new ArrayList<>();

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

    @Override
    public String toString() {
        return "UmlClass{" +
                "xmiId='" + xmiId + '\'' +
                ", name='" + name + '\'' +
                ", uri='" + uri + '\'' +
                ", properties=" + properties +
                '}';
    }
}
