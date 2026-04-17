package com.project.domain;

import com.project.stereotype.Id;

public class DomainProperty {
    private String name;
    private String type;
    private String relationship;
    private String mappedBy;
    private Id id;
    private String unique;
    private String nullable;
    private String minLength;
    private String maxLength;

    public String getUnique() {
        return unique;
    }

    public void setUnique(String unique) {
        this.unique = unique;
    }

    public String getNullable() {
        return nullable;
    }

    public void setNullable(String nullable) {
        this.nullable = nullable;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRelationship() {
        return relationship;
    }

    public void setRelationship(String relationship) {
        this.relationship = relationship;
    }

    public String getMappedBy() {
        return mappedBy;
    }

    public void setMappedBy(String mappedBy) {
        this.mappedBy = mappedBy;
    }

    public Id getId() {
        return id;
    }

    public void setId(Id id) {
        this.id = id;
    }


    @Override
    public String toString() {
        return "DomainProperty{" +
                "name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", relationship='" + relationship + '\'' +
                ", mappedBy='" + mappedBy + '\'' +
                ", id=" + id +
                ", unique='" + unique + '\'' +
                ", nullable='" + nullable + '\'' +
                ", minLength='" + minLength + '\'' +
                ", maxLength='" + maxLength + '\'' +
                '}';
    }
}
