package com.project.stereotype;

public class MVCProperty extends Stereotype {
    private String unique;


    public String getUnique() {
        return unique;
    }

    public void setUnique(String unique) {
        this.unique = unique;
    }

    @Override
    public String toString() {
        return "MVCProperty{" +
                "unique='" + unique + '\'' +
                '}';
    }
}
