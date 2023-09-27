package com.candyshop;

import java.util.ArrayList;
import java.util.List;

public class Model {
	
	private String className;
	private List<Property> properties = new ArrayList<>();
	private List<Association> associations = new ArrayList<>();
	
	public List<Property> getProperties() {
		return properties;
	}
	public void setProperties(List<Property> properties) {
		this.properties = properties;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	
	public List<Association> getAssociations() {
		return associations;
	}
	public void setAssociations(List<Association> associations) {
		this.associations = associations;
	}
	@Override
	public String toString() {
		return "Model [className=" + className  + ", properties=" + properties + "]";
	}

}
