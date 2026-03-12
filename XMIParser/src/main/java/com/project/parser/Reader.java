package com.project.parser;

import com.project.model.Model;
import com.project.model.Pageable;
import com.project.model.Property;
import com.project.model.Stereotype;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.project.parser.XmiConstants.*;

public class Reader extends DefaultHandler {
    private Map<String, Model> models = new HashMap<>();
    private List<Stereotype> stereotypes = new ArrayList<>();
    private List<Property> propertiesList;
    private Property property;
    private final XmiToJavaTypeMapper typeMapper = new XmiToJavaTypeMapper();

    @Override
    public void startDocument() throws SAXException {
        super.startDocument();
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) {

        // Initialize the model object
        if (checkModel(qName, attributes)) {
            Model model = parseModel(attributes);
            propertiesList = new ArrayList<>();
            model.setProperties(propertiesList);
            models.put(model.getId(), model);
        }

        // Initialize the properties
        if (checkProperty(qName, attributes)) {
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

    public boolean checkModel(String qName, Attributes attributes) {
        if (qName.equals("packagedElement") && (attributes.getValue(XMI_TYPE).equals(UML_CLASS))) {
            return true;
        }
        return false;
    }

    public boolean checkProperty(String qName, Attributes attributes) {
        if (qName.equals("ownedAttribute") && attributes.getValue(XMI_TYPE).equals(UML_PROPERTY)) {
            return true;
        }
        return false;
    }

    public Model parseModel(Attributes attributes) {
        Model model = new Model();
        model.setId(attributes.getValue(XMI_ID));
        model.setName(attributes.getValue("name"));
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
        if(attributes.getValue("pageNo") != null){
            stereotype.setPageNo(attributes.getValue("pageNo"));
        }
        if(attributes.getValue("pageSize") != null){
            stereotype.setPageSize(attributes.getValue("pageSize"));
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

    public void sortAssociation(Model model) {
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
                    } else {
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

    public void sortStereotype(Model model) {
        for (Property property : model.getProperties()) {
            List<Stereotype> stereoList = stereotypes.stream()
                    .filter(stereo -> stereo.getBase().equals(property.getId()))
                    .collect(Collectors.toList());
            property.setStereotypes(stereoList);
        }
    }

    public void sortStereotypeforEntity() {
        for(Stereotype stereotype : stereotypes){
            if(stereotype.getType().equals(BASE_CLASS)){
                Model model = models.get(stereotype.getBase());
                model.setEntity(true);
                if (StereotypeName.PAGEABLE.getQName().equals(stereotype.getName())) {
                    Pageable pageable = new Pageable();
                    pageable.setPageNo(stereotype.getPageNo());
                    pageable.setPageSize(stereotype.getPageSize());
                    model.setPageable(pageable);
                }
            }
        }
    }

    @Override
    public void endDocument() {
        for(Model model : models.values()){
            sortAssociation(model);
            sortStereotype(model);
        }

        sortStereotypeforEntity();
        try {
            printInfo();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        setModelNameToLowerCase();
    }

    public void setModelNameToLowerCase(){
        for (Model model : models.values()) {
            model.setName(model.getName().toLowerCase());
        }          
    }

    public Property getRelatedProperty(Model model, String association) {
        return model.getProperties().stream()
                .filter(prop -> prop.getAssociation() != null)
                .filter(prop -> prop.getAssociation().equals(association))
                .findFirst()
                .orElse(null);
    }

    // Testing methhod
    private void printInfo() throws IOException {
        String fileName = "C:\\Users\\Aleksandar\\Desktop\\testGenerated.txt";
        File file = new File(fileName);
        boolean exist = file.exists();
        if(!exist){
            file.createNewFile();
        }
        PrintWriter printWriter = new PrintWriter(new FileWriter(fileName));
        for (Model model : models.values()) {
            printWriter.println(model.getName() + " " + model.getId());
        }
        printWriter.println();
        for (Model model : models.values()) {
            for(Property property : model.getProperties()){
                if(property.getStereotypes().size() > 0){
                    printWriter.print(model.getName() + " " + property.getName()+ "--->");
                    for(Stereotype stereotype : property.getStereotypes()) {
                        printWriter.print(stereotype.getName()+ ", ");
                    }
                    printWriter.println();
                    printWriter.println();
                }
            }

        }
        printWriter.close();
    }

    public Map<String, Model> getModels() {
        return models.entrySet().
                stream().filter(x -> x.getValue().isEntity())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
}
