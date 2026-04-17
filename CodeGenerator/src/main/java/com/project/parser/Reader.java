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

    public UmlClass parseUmlClassForModel(Attributes attributes) {
        UmlClass model = new UmlClass();
        model.setXmiId(attributes.getValue(XMI_ID));
        model.setName(attributes.getValue(XMI_MODEL_NAME));
        model.setUri(modelUri);
        return model;
    }

    public Stereotype parseStereotype(String qName, Attributes attributes) {
       /* Stereotype stereotype = new Stereotype();
        stereotype.setXmiId(attributes.getValue(XMI_ID));
        stereotype.setStereotypeName(StereotypeName.fromQName(qName));
        */
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
                ListView listView = new ListView();
                listView.setId(attributes.getValue(XMI_ID));
                listView.setDeleteEnabled(attributes.getValue(DELETE_ENABLED));
                listView.setEditEnabled(attributes.getValue(EDIT_ENABLED));

                if (attributes.getValue(PAGE_SIZE) != null) {
                    listView.setPageSize(attributes.getValue(PAGE_SIZE));
                } else {
                    // temp fix
                    listView.setPageSize("5");
                }
                stereotype = listView;
                //     umlClasses.get(stereotype.getBaseXmiId()).setListView(listView);
            }

            case SORT_BY -> {
                SortBy sortBy = new SortBy();
                if(attributes.getValue(SORT_DIRECTION) == null){
                    sortBy.setDirection(attributes.getValue("ASC"));
                } else {
                    sortBy.setDirection(attributes.getValue(SORT_DIRECTION));
                }
                stereotype = sortBy;
            }

            case ENTITY, KEY, ENTITY_PROPERTY -> {
                stereotype = null;
            }
            case TO_STRING -> {
                stereotype = null;
            }
            case UNIQUE -> {
                stereotype = null;
            }
            case COMMON -> {
                stereotype = null;

            }
            case MVC_PROPERTY -> {
                MVCProperty mvcProperty = new MVCProperty();
                mvcProperty.setUnique(attributes.getValue(UNIQUE));
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


       /*
        if(attributes.getValue("minLength") != null){
            stereotype.setMinLength(attributes.getValue("minLength"));
        }

        if(attributes.getValue("maxLength") != null){
            stereotype.setMaxLength(attributes.getValue("maxLength"));
        }
        if(attributes.getValue("nullable") != null){
            stereotype.setNullable(attributes.getValue("nullable"));
        }

        */

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
