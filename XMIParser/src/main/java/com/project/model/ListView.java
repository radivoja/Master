package com.project.model;

public class ListView {
    private String id;
    private String pageSize;
    private String deleteEnabled;
    private String editEnabled;
    private String sortEnabled;

    public String getSortEnabled() {
        return sortEnabled;
    }
    public void setSortEnabled(String sortEnabled) {
        this.sortEnabled = sortEnabled;
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getPageSize() {
        return pageSize;
    }
    public void setPageSize(String pageSize) {
        this.pageSize = pageSize;
    }

    public String getDeleteEnabled() {
        return deleteEnabled;
    }
    public void setDeleteEnabled(String deleteEnabled) {
        this.deleteEnabled = deleteEnabled;
    }
    public String getEditEnabled() {
        return editEnabled;
    }
    public void setEditEnabled(String editEnabled) {
        this.editEnabled = editEnabled;
    }


    @Override
    public String toString() {
        return "ListView{" +
                "id='" + id + '\'' +
                ", pageSize='" + pageSize + '\'' +
                ", deleteEnabled=" + deleteEnabled +
                ", editEnabled=" + editEnabled +
                ", sortDirection='" + sortEnabled + '\'' +
                '}';
    }
}
