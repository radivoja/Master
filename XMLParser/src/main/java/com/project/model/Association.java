package com.project.model;

public class Association {
	private String id;
	private String leftEntity;
	private String leftCardinality;
	private String rightEntity;
	private String rightCardinality;
	private String mappedBy;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getLeftEntity() {
		return leftEntity;
	}
	public void setLeftEntity(String leftEntity) {
		this.leftEntity = leftEntity;
	}
	public String getLeftCardinality() {
		return leftCardinality;
	}
	public void setLeftCardinality(String leftCardinality) {
		this.leftCardinality = leftCardinality;
	}
	public String getRightEntity() {
		return rightEntity;
	}
	public void setRightEntity(String rightEntity) {
		this.rightEntity = rightEntity;
	}
	public String getRightCardinality() {
		return rightCardinality;
	}
	public void setRightCardinality(String rightCardinality) {
		this.rightCardinality = rightCardinality;
	}
	
	public String getMappedBy() {
		return mappedBy;
	}
	public void setMappedBy(String mappedBy) {
		this.mappedBy = mappedBy;
	}
	@Override
	public String toString() {
		return "Association [leftEntity=" + leftEntity + ", leftCardinality=" + leftCardinality
				+ ", rightEntity=" + rightEntity + ", rightCardinality=" + rightCardinality + ", mappedBy=" + mappedBy
				+ "]";
	}
}
