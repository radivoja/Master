package com.project.stereotype;

import com.project.parser.StereotypeName;

public class Stereotype {
    private String xmiId;
    private String baseXmiId;
    private boolean isClass;
    private String minLength;
    private String maxLength;
    private String nullable;
    private StereotypeName name;


    public boolean isClass() {
        return isClass;
    }

    public void setIsClass(boolean isClass) {
        this.isClass = isClass;
    }

    public String getXmiId() {
        return xmiId;
    }
    public void setXmiId(String xmiId) {
        this.xmiId = xmiId;
    }

    public StereotypeName getName() {
        return name;
    }
    public void setName(StereotypeName name) {
        this.name = name;
    }
    public String getBaseXmiId() {
        return baseXmiId;
    }
    public void setBaseXmiId(String baseXmiId) {
        this.baseXmiId = baseXmiId;
    }

    @Override
    public String toString() {
        return "Stereotype{" +
                "xmi:id='" + xmiId + '\'' +
                ", name='" + name + '\'' +
                ", base='" + baseXmiId + '\'' +
                ", minLength='" + minLength + '\'' +
                ", maxLength='" + maxLength + '\'' +
                ", nullable='" + nullable + '\'' +
                '}';
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
