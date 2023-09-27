package com.candyshop;

public class Information {
	private String type;
	private String id;
	private String name;
	private String stereotype;
	
	public Information(String type, String id, String name) {
		super();
		this.type = type;
		this.id = id;
		this.name = name;	
	}
	public String getStereotype() {
		return stereotype;
	}
	public void setStereotype(String stereotype) {
		this.stereotype = stereotype;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
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
	@Override
	public String toString() {
		return "Information [type=" + type + ", id=" + id + ", name=" + name + ", stereotype=" + stereotype + "]";
	}
	
	

}
