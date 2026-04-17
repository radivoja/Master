package com.project.stereotype;

public class SortBy extends Stereotype {
    private String sortDirection;

    public String getSortDirection() {
        return sortDirection;
    }

    public void setSortDirection(String sortDirection) {
        this.sortDirection = sortDirection;
    }

    @Override
    public String toString() {
        return "SortBy{" +
                "sortDirection='" + sortDirection + '\'' +
                '}';
    }
}
