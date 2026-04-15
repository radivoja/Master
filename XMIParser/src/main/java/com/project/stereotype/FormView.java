package com.project.stereotype;

public class FormView  extends Stereotype{
    private boolean readOnly;

    public boolean isReadOnly() {
        return readOnly;
    }

    public void setReadOnly(boolean readOnly) {
        this.readOnly = readOnly;
    }

    @Override
    public String toString() {
        return "FormView{" +
                "readOnly=" + readOnly +
                '}';
    }
}
