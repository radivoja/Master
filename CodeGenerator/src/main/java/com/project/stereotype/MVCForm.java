package com.project.stereotype;

public class MVCForm extends Stereotype {
    private String readonly;

    public String getReadonly() {
        return readonly;
    }
    public void setReadonly(String readonly) {
        this.readonly = readonly;
    }

    @Override
    public String toString() {
        return "MVCForm{" +
                "readonly='" + readonly + '\'' +
                '}';
    }
}
