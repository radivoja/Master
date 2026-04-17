package com.project.stereotype;

public class SortBy extends Stereotype {
    private String propertyName;

    private String direction;

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    @Override
    public String toString() {
        return "SortBy{" +
                "propertyName='" + propertyName + '\'' +
                ", direction='" + direction + '\'' +
                '}';
    }
}
