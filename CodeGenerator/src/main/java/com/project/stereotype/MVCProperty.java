package com.project.stereotype;

public class MVCProperty extends Stereotype {
    private String unique;
    private String minLength;
    private String maxLength;
    private String nullable;
    private String toString;

    public String getUnique() {
        return unique;
    }

    public void setUnique(String unique) {
        this.unique = unique;
    }

    public String getMinLength() {
        return minLength;
    }

    public void setMinLength(String minLength) {
        this.minLength = minLength;
    }

    public String getMaxLength() {
        return maxLength;
    }

    public void setMaxLength(String maxLength) {
        this.maxLength = maxLength;
    }

    public String getNullable() {
        return nullable;
    }

    public void setNullable(String nullable) {
        this.nullable = nullable;
    }

    public String getToString() {
        return toString;
    }
    public void setToString(String toString) {
        this.toString = toString;
    }

    @Override
    public String toString() {
        return "MVCProperty{" +
                "unique='" + unique + '\'' +
                ", minLength='" + minLength + '\'' +
                ", maxLength='" + maxLength + '\'' +
                ", nullable='" + nullable + '\'' +
                ", toString='" + toString + '\'' +
                '}';
    }
}
