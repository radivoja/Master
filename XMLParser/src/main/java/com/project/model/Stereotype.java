package com.project.model;

public class Stereotype {
	private String name;
	private String length;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLength() {
		return length;
	}
	public void setLength(String length) {
		this.length = length;
	}
	@Override
	public String toString() {
		return "Stereotype [name=" + name + ", length=" + length + "]";
	}
}
