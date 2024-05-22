package com.project.model;

public class Pageable {

    private String pageNo;
    private String pageSize;

    public String getPageNo() {
        return pageNo;
    }

    public void setPageNo(String pageNo) {
        this.pageNo = pageNo;
    }

    public String getPageSize() {
        return pageSize;
    }

    public void setPageSize(String pageSize) {
        this.pageSize = pageSize;
    }

    @Override
    public String toString() {
        return "Pageable{" +
                "pageNo='" + pageNo + '\'' +
                ", pageSize='" + pageSize + '\'' +
                '}';
    }
}
