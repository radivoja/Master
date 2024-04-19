package com.project.model;

public class Stereotype {
    private String id;
    private String name;
    private String base;
    private String length;

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
    public String getLength() {
        return length;
    }
    public void setLength(String length) {
        this.length = length;
    }
    @Override
    public String toString() {
        return "Stereotype [name=" + name + ", base=" + base + ", length=" + length + "]";
    } 

}
