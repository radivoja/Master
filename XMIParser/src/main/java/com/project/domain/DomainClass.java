package com.project.domain;

import com.project.stereotype.FormView;
import com.project.stereotype.ListView;

import java.util.LinkedHashMap;
import java.util.Map;

public class DomainClass {
    private String name;
    private String uri;
    private Map<String, DomainProperty> properties = new LinkedHashMap<>();
    private ListView listView;
    private FormView formView;

    public FormView getFormView() {
        return formView;
    }

    public void setFormView(FormView formView) {
        this.formView = formView;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public Map<String, DomainProperty> getProperties() {
        return properties;
    }

    public void setProperties(Map<String, DomainProperty> properties) {
        this.properties = properties;
    }

    public ListView getListView() {
        return listView;
    }

    public void setListView(ListView listView) {
        this.listView = listView;
    }

    @Override
    public String toString() {
        return "DomainClass{" +
                "name='" + name + '\'' +
                ", uri='" + uri + '\'' +
                ", properties=" + properties +
                '}';
    }
}
