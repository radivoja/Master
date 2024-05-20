package com.project.model;

public class Stereotype {
    private String id;
    private String name;
    private String base;
    private String type;
    private String minLength;
    private String maxLength;
    private String nullable;

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
        return "Stereotype{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", base='" + base + '\'' +
                ", type='" + type + '\'' +
                ", minLength='" + minLength + '\'' +
                ", maxLength='" + maxLength + '\'' +
                ", nullable='" + nullable + '\'' +
                '}';
    }

    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }

    public String getMinLength() {
        return minLength;
    }

    public void setMinLength(String minLength) {
        this.minLength = minLength;
    }

    public String getMaxLength() {
        return maxLength;
    }

    public void setMaxLength(String maxLength) {
        this.maxLength = maxLength;
    }

    public String getNullable() {
        return nullable;
    }

    public void setNullable(String nullable) {
        this.nullable = nullable;
    }
}
