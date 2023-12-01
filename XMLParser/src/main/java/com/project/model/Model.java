package com.candyshop;

import java.util.ArrayList;
import java.util.List;

public class Model {
    private String id;
    private String name;
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
}
