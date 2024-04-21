package com.project.model;

public class Stereotype {
    private String id;
    private String name;
    private String base;
    private String type;

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
    public String getBase() {
        return base;
    }
    public void setBase(String base) {
        this.base = base;
    }
    @Override
    public String toString() {
        return "Stereotype [name=" + name + ", id=" + id + ", base=" + base + ", type=" + type + "]";
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
}
