package com.project.parser;

import com.project.model.ListView;
import com.project.model.Property;
import com.project.model.Stereotype;
import com.project.model.UmlClass;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.project.parser.XmiConstants.*;

public class Reader extends DefaultHandler {
    private Map<String, UmlClass> umlClasses = new HashMap<>();
    private List<Stereotype> stereotypes = new ArrayList<>();
    private List<Property> propertiesList;
    private Property property;
    private final XmiToJavaTypeMapper typeMapper = new XmiToJavaTypeMapper();
    private String modelUri;

    @Override
    public void startDocument() {
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) {
        if(qName.equals(UML_MODEL)){
            modelUri = attributes.getValue(XMI_MODEL_URI);
        }

        // Initialize the model object
        if (isUmlClass(qName, attributes)) {
            UmlClass model = parseModel(attributes);
            propertiesList = new ArrayList<>();
            model.setProperties(propertiesList);
            umlClasses.put(model.getId(), model);
        }

        // Initialize the properties
        if (isUmlProperty(qName, attributes)) {
            property = parseProperty(attributes);
            propertiesList.add(property);
        }

        // Cardinality
        if (qName.equals("upperValue")) {
            property.setUpperValue(attributes.getValue("value"));
        }

        if (qName.equals("lowerValue")) {
            property.setLowerValue(attributes.getValue("value"));
        }

        // Property type
        if (qName.equals("type")) {
            property.setType(typeMapper.map(attributes.getValue("href")));
        }

        // Stereotypes
        if(StereotypeName.contains(qName)) {
            Stereotype stereotype = parseStereotype(qName, attributes);
            stereotypes.add(stereotype);
        }

    }

    public boolean isUmlClass(String qName, Attributes attributes) {
        if (qName.equals(XMI_PACKAGED_ELEMENT)
                && (attributes.getValue(XMI_TYPE).equals(UML_CLASS)))
        {
            return true;
        }
        return false;
    }

    public boolean isUmlProperty(String qName, Attributes attributes) {
        if (qName.equals(XMI_OWNER_ATTRIBUTE) && attributes.getValue(XMI_TYPE).equals(UML_PROPERTY)) {
            return true;
        }
        return false;
    }

    public UmlClass parseModel(Attributes attributes) {
        UmlClass model = new UmlClass();
        model.setId(attributes.getValue(XMI_ID));
        model.setName(attributes.getValue(XMI_MODEL_NAME));
        model.setUri(modelUri);
        return model;
    }

    public Stereotype parseStereotype(String qName, Attributes attributes) {
        Stereotype stereotype = new Stereotype();
        stereotype.setName(qName);

        if (attributes.getValue(BASE_CLASS) != null) {
            stereotype.setBase(attributes.getValue(BASE_CLASS));
            stereotype.setType(BASE_CLASS);
        } else {
            stereotype.setBase(attributes.getValue(BASE_PROPERTY));
            stereotype.setType(BASE_PROPERTY);
        }

        stereotype.setId(attributes.getValue(XMI_ID));

        if(attributes.getValue("minLength") != null){
            stereotype.setMinLength(attributes.getValue("minLength"));
        }

        if(attributes.getValue("maxLength") != null){
            stereotype.setMaxLength(attributes.getValue("maxLength"));
        }
        if(attributes.getValue("nullable") != null){
            stereotype.setNullable(attributes.getValue("nullable"));
        }

        if(StereotypeName.MVC_LIST.getQName().equals(qName)){
            ListView listView = new ListView();
            listView.setId(attributes.getValue(XMI_ID));
            listView.setDeleteEnabled(attributes.getValue(DELETE_ENABLED));
            listView.setEditEnabled(attributes.getValue(EDIT_ENABLED));

            if(attributes.getValue(PAGE_SIZE) != null){
                listView.setPageSize(attributes.getValue(PAGE_SIZE));
            }
            else {
                // temp fix
                listView.setPageSize("5");
            }
            umlClasses.get(stereotype.getBase()).setListView(listView);
        }
        System.out.println(stereotype);

        return stereotype;
    }

    public Property parseProperty(Attributes attributes) {
        Property property = new Property();
        String id = attributes.getValue(XMI_ID);
        String name = attributes.getValue("name");
        property.setId(id);
        property.setName(name);

        String association = attributes.getValue("association");
        if (association != null) {
            property.setAssociation(association);
            property.setType(attributes.getValue("type"));
        }
        return property;
    }


    @Override
    public void endDocument() {
        /*
        try {
            Printer.printInfo(models);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
         */
    }


    public Map<String, UmlClass> getUmlClasses(){
        return umlClasses;
    }

    public List<Stereotype> getStereotypes(){
        return stereotypes;
    }

}
