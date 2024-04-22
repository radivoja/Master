package com.project.model;

import java.util.List;

public class Property {
    private String id;
    private String name;
    private String type;
    private String association;
    private String lowerValue;
    private String upperValue;
    private String relationship;
	private String mappedBy;
    private List<Stereotype> stereotypes;
    
    public List<Stereotype> getStereotypes() {
        return stereotypes;
    }
    public void setStereotypes(List<Stereotype> stereotypes) {
        this.stereotypes = stereotypes;
    }
    public String getMappedBy() {
		return mappedBy;
	}
	public void setMappedBy(String mappedBy) {
		this.mappedBy = mappedBy;
	}
	public String getRelationship() {
        return relationship;
    }
    public void setRelationship(String relationship) {
        this.relationship = relationship;
    }
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
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public String getAssociation() {
        return association;
    }
    public void setAssociation(String association) {
        this.association = association;
    }
    public String getLowerValue() {
        return lowerValue;
    }
    public void setLowerValue(String lowerValue) {
        this.lowerValue = lowerValue;
    }
    public String getUpperValue() {
        return upperValue;
    }
    public void setUpperValue(String upperValue) {
        this.upperValue = upperValue;
    }
    @Override
    public String toString() {
        return "Property [id=" + id + ", name=" + name + ", type=" + type + ", association=" + association
                + ", lowerValue=" + lowerValue + ", upperValue=" + upperValue + ", relationship=" + relationship + " " + stereotypes + "]";
    }    
	
}
