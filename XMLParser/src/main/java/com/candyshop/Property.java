package com.candyshop;

public class Property {
	private String name;
	private String stereotype;
	private String id;
	private String type;
	private String cardinality;
	private String length;
	
	public String getName() {
		return name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getStereotype() {
		return stereotype;
	}
	public void setStereotype(String stereotype) {
		this.stereotype = stereotype;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCardinality() {
		return cardinality;
	}
	public void setCardinality(String cardinality) {
		this.cardinality = cardinality;
	}

	public String getLength() {
		return length;
	}
	public void setLength(String length) {
		this.length = length;
	}
	@Override
	public String toString() {
		return "Property [name=" + name + ", stereotype=" + stereotype + ", id=" + id + ", type=" + type
				+ ", cardinality=" + cardinality + ", length=" + length + "]";
	}
	
}
