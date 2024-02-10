package com.project.parser;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.project.model.Model;
import com.project.model.Property;

public class XMIParser extends DefaultHandler{

	public Map<String, Model> models = new HashMap<>();
    public Model model = null;
    Property property = null;

    @Override
    public void startDocument() throws SAXException {
        super.startDocument();
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) {

        // Initialize the model object
        if (qName.equals("packagedElement") && attributes.getValue("xmi:type").equals("uml:Class")) {
            model = new Model();
            String id = attributes.getValue("xmi:id");
            String name = attributes.getValue("name");
            model.setId(id);
            model.setName(name);

            models.put(id, model);
        }

        // Initialize the properties
        if (qName.equals("ownedAttribute") && attributes.getValue("xmi:type").equals("uml:Property")) {
            property = new Property();
            model.getProperties().add(property);
            String id = attributes.getValue("xmi:id");
            String name = attributes.getValue("name");
            property.setId(id);
            property.setName(name);

            String association = attributes.getValue("association");
            if (association != null) {
                property.setAssociation(association);
                property.setType(attributes.getValue("type"));
            }
        }

        if (qName.equals("upperValue")) {
            property.setUpperValue(attributes.getValue("value"));
        }

        if (qName.equals("lowerValue")) {
            property.setLowerValue(attributes.getValue("value"));
        }

        // Property type
        if (qName.equals("type")) {
            property.setType(getType(attributes));
        }
    }

    @Override
    public void endDocument() {
        for (Model model : models.values()) {
            for (Property property : model.getProperties()) {
                if (property.getAssociation() != null) {
                    Model relatedModel = models.get(property.getType());
                    Property relatedProperty = getRelatedProperty(relatedModel, property.getAssociation());
                    // Set Property type field to match relationship type
                    property.setType(relatedModel.getName());
                    relatedProperty.setType(model.getName());      
                    if (property.getUpperValue() == null && relatedProperty.getUpperValue() == null) {
                        property.setRelationship("OneToOne");
                        relatedProperty.setRelationship("OneToOne");
                    } else if (property.getUpperValue() != null && relatedProperty.getUpperValue() != null) {
                        property.setRelationship("ManyToMany");
                        relatedProperty.setMappedBy(relatedProperty.getName());
                        relatedProperty.setRelationship("ManyToMany");
                    } else {                   
                        if (property.getUpperValue() != null) {
                            //Change property type field as List
                            property.setType("List<" + relatedModel.getName() + ">");
                            property.setRelationship("OneToMany");
							property.setMappedBy(model.getName());
                            relatedProperty.setRelationship("ManyToOne");
                        }
                        else{
                            relatedProperty.setType("List<" + model.getName() + ">");
                            property.setRelationship("ManyToOne");
                            relatedProperty.setRelationship("OneToMany");
                            relatedProperty.setMappedBy(relatedModel.getName());
                        }
                    }
                    // Set Property association fields to null to prevent additional checking
                    // since we resolve cardinality for both sides in single iteration 
                    property.setAssociation(null);
                    relatedProperty.setAssociation(null);
                }
            }
        }
        try {
            printInfo();
        } catch (IOException e) {
            e.printStackTrace();
        }
        setModelNameToLowerCase();
    }

    public void setModelNameToLowerCase(){
        for (Model model : models.values()) {
            model.setName(model.getName().toLowerCase());
        }          
    }

    public Property getRelatedProperty(Model model, String association) {
        Property property = model.getProperties().stream()
                .filter(prop -> prop.getAssociation() != null)
                .filter(prop -> prop.getAssociation().equals(association))
                .findFirst()
                .orElse(null);
        return property;
    }

    private void printInfo() throws IOException {
        String fileName = "C:\\Users\\User\\Desktop\\test.txt";
        PrintWriter out = new PrintWriter(new FileWriter(fileName));

        for (Model model : models.values()) {
            out.println(model.getName() + " " + model.getId());
            for (Property property : model.getProperties()) {
                out.println(property);
            }
        }
        out.close();
    }

    private String getType(Attributes attributes) {
        String type = attributes.getValue("href");
        if (type.endsWith("String")) {
            return "String";
        }
        if (type.endsWith("Integer")) {
            return "Integer";
        }
        if (type.endsWith("ELong")) {
            return "Long";
        }
        if (type.endsWith("EDate")) {
            return "Date";
        }
        if (type.endsWith("EDouble") || type.endsWith("Double")) {
            return "Double";
        }
        
        return type;
    }


}
