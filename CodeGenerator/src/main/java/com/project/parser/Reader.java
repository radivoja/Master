package com.project.parser;

import com.project.model.*;
import com.project.stereotype.*;
import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.project.parser.XmiConstants.*;
import static com.project.parser.XmiConstants.UNIQUE;

public class Reader extends DefaultHandler {
    private Map<String, UmlClass> umlClasses = new HashMap<>();
    private List<Stereotype> stereotypes = new ArrayList<>();
    private List<UmlProperty> propertiesList;
    private UmlProperty property;
    private final XmiToJavaTypeMapper typeMapper = new XmiToJavaTypeMapper();
    private String modelUri;
    private UmlClass umlClass;

    public Map<String, UmlClass> getUmlClasses(){
        return umlClasses;
    }

    public List<Stereotype> getStereotypes(){
        return stereotypes;
    }

    @Override
    public void startDocument() {
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) {
        if(qName.equals(UML_MODEL)){
            modelUri = attributes.getValue(XMI_MODEL_URI);
        }

        // Initialize the uml class model object
        if (isUmlClass(qName, attributes)) {
            umlClass = parseUmlClassForModel(attributes);
            propertiesList = new ArrayList<>();
            umlClass.setProperties(propertiesList);
            umlClasses.put(umlClass.getXmiId(), umlClass);
        }

        // Initialize the properties
        if (isUmlProperty(qName, attributes)) {
            property = parseProperty(attributes);
            propertiesList.add(property);
        }

        // Cardinality
        if (qName.equals(UPPER_VALUE)) {
            property.setUpperValue(attributes.getValue(VALUE));
        }

        if (qName.equals(LOWER_VALUE)) {
            property.setLowerValue(attributes.getValue(VALUE));
        }

        // Property type
        if (qName.equals(TYPE)) {
            property.setType(typeMapper.map(attributes.getValue(HREF)));
        }

        // Stereotypes
        if(StereotypeName.contains(qName)) {
            Stereotype stereotype = parseStereotype(qName, attributes);
            stereotypes.add(stereotype);
        }

    }

    public boolean isUmlClass(String qName, Attributes attributes) {
        if (qName.equals(XMI_PACKAGED_ELEMENT) && (attributes.getValue(XMI_TYPE).equals(UML_CLASS)))
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

    public UmlClass parseUmlClassForModel(Attributes attributes) {
        UmlClass model = new UmlClass();
        model.setXmiId(attributes.getValue(XMI_ID));
        model.setName(attributes.getValue(XMI_MODEL_NAME));
        model.setUri(modelUri);
        return model;
    }

    public Stereotype parseStereotype(String qName, Attributes attributes) {
        Stereotype stereotype;
        StereotypeName stereotypeName = StereotypeName.fromQName(qName);

        switch (stereotypeName) {
            case ID -> {
                Id id = new Id();
                id.setGenerated(attributes.getValue(GENERATED));
                id.setStrategy(attributes.getValue(GENERATION_STRATEGY));
                stereotype = id;
            }
            case MVC_LIST -> {
                MVCList listView = new MVCList();
                listView.setId(attributes.getValue(XMI_ID));
                listView.setDeleteEnabled(attributes.getValue(DELETE_ENABLED));
                listView.setEditEnabled(attributes.getValue(EDIT_ENABLED));
                listView.setPageSize(attributes.getValue(PAGE_SIZE));
                stereotype = listView;
            }
            case SORT_BY -> {
                SortBy sortBy = new SortBy();
                sortBy.setDirection(attributes.getValue(SORT_DIRECTION));
                stereotype = sortBy;
            }
            case MVC_PROPERTY -> {
                MVCProperty mvcProperty = new MVCProperty();
                mvcProperty.setUnique(attributes.getValue(UNIQUE));
                mvcProperty.setMinLength(attributes.getValue(MIN_LENGTH));
                mvcProperty.setMaxLength(attributes.getValue(MAX_LENGTH));
                mvcProperty.setNullable(attributes.getValue(NULLABLE));
                mvcProperty.setToString(attributes.getValue(TO_STRING));
                stereotype = mvcProperty;
            }

            case MVC_FORM -> {
                MVCForm mvcForm = new MVCForm();
                mvcForm.setReadonly(attributes.getValue(READONLY));
                stereotype = mvcForm;
            }

            default -> {
                stereotype = null;
            }
        }

        if (attributes.getValue(BASE_CLASS) != null) {
            stereotype.setIsClass(true);
            stereotype.setBaseXmiId(attributes.getValue(BASE_CLASS));
        } else {
            stereotype.setIsClass(false);
            stereotype.setBaseXmiId(attributes.getValue(BASE_PROPERTY));

        }
        stereotype.setXmiId(attributes.getValue(XMI_ID));
        stereotype.setName(StereotypeName.fromQName(qName));

        System.out.println(stereotype);

        return stereotype;
    }

    public UmlProperty parseProperty(Attributes attributes) {
        UmlProperty property = new UmlProperty();
        String xmiId = attributes.getValue(XMI_ID);
        String name = attributes.getValue(NAME);
        property.setXmiId(xmiId);
        property.setName(name);

        String association = attributes.getValue(ASSOCIATION);
        if (association != null) {
            property.setAssociation(association);
            property.setType(attributes.getValue(TYPE));
        }
        return property;
    }

    @Override
    public void endDocument() {
    }

}
