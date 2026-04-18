package com.project.parser;

import com.project.domain.DomainClass;
import com.project.domain.DomainProperty;
import com.project.model.UmlClass;
import com.project.model.UmlProperty;
import com.project.stereotype.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class UmlModelResolver {
    Reader reader = new Reader();

    public UmlModelResolver(String umlPath) throws IOException, SAXException, ParserConfigurationException {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser saxParser = factory.newSAXParser();
        saxParser.parse(umlPath, reader);
    }

    public Map<String, DomainClass> prepareDomainModel(){
        sortAssociation(reader.getUmlClasses());
        Map<String, DomainClass> domainModel = mapUmlToDomain(reader.getUmlClasses());
        attachStereotypesToProperties(domainModel);
        applyClassStereotypes(domainModel);
        return domainModel;
    }


    public UmlProperty getRelatedProperty(UmlClass model, String association) {
        return model.getProperties().stream()
                .filter(prop -> prop.getAssociation() != null)
                .filter(prop -> prop.getAssociation().equals(association))
                .findFirst()
                .orElse(null);
    }

    public void sortAssociation(Map<String, UmlClass> umlClasses) {
        for (UmlClass model : umlClasses.values()) {
            for (UmlProperty property : model.getProperties()) {
                if (property.getAssociation() != null) {
                    UmlClass relatedModel = umlClasses.get(property.getType());
                    UmlProperty relatedProperty = getRelatedProperty(relatedModel, property.getAssociation());
                    // Set Property type field to match relationship type
                    property.setType(relatedModel.getName());
                    relatedProperty.setType(model.getName());
                    if (property.getUpperValue() == null && relatedProperty.getUpperValue() == null) {
                        property.setRelationship("OneToOne");
                        relatedProperty.setRelationship("OneToOne");
                    } else if (property.getUpperValue() != null && relatedProperty.getUpperValue() != null) {
                        property.setRelationship("ManyToMany");
                        property.setType("List<" + relatedModel.getName() + ">");
                        relatedProperty.setMappedBy(relatedModel.getName());
                        relatedProperty.setRelationship("ManyToMany");
                        relatedProperty.setType("List<" + model.getName() + ">");
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
    }


    public Map<String, DomainClass> mapUmlToDomain(Map<String, UmlClass> umlModel){
        Map<String, DomainClass> domainModel = new HashMap<>();
        for(UmlClass umlClass : umlModel.values()) {
            DomainClass domainClass = new DomainClass();
            domainClass.setName(umlClass.getName());
            domainClass.setUri(umlClass.getUri());
            for (UmlProperty umlProperty : umlClass.getProperties()) {
                DomainProperty domainProperty = new DomainProperty();
                domainProperty.setName(umlProperty.getName());
                domainProperty.setType(umlProperty.getType());
                domainProperty.setRelationship(umlProperty.getRelationship());
                domainProperty.setMappedBy(umlProperty.getMappedBy());
                domainClass.getProperties().put(umlProperty.getXmiId(), domainProperty);
            }
            domainModel.put(umlClass.getXmiId(), domainClass);
        }

        return domainModel;
    }

    public void applyClassStereotypes(Map<String, DomainClass> model){
        for (Stereotype stereotype : reader.getStereotypes()) {
            if(!stereotype.isClass())
                continue;
            if (stereotype instanceof MVCList listView) {
                model.get(stereotype.getBaseXmiId()).setListView(listView);
            } else if (stereotype instanceof MVCForm formView) {
                model.get(stereotype.getBaseXmiId()).setFormView(formView);
            }
        }
    }

    public void attachStereotypesToProperties(Map<String, DomainClass> model) {
        for(DomainClass domainClass : model.values()) {
            for(Stereotype stereotype : reader.getStereotypes()) {
                // Skip if stereotype is class one and if stereotype doesnt exist in current class
                if(stereotype.isClass() || !domainClass.getProperties().containsKey(stereotype.getBaseXmiId()))
                    continue;
                if(stereotype instanceof Id id) {
                    domainClass.getProperties().get(stereotype.getBaseXmiId()).setId(id);
                } else if (stereotype instanceof SortBy sortBy) {
                    domainClass.setSortBy(sortBy);
                    sortBy.setPropertyName(domainClass.getProperties().get(sortBy.getBaseXmiId()).getName());
                } else if (stereotype instanceof MVCProperty mvcProperty) {
                    domainClass.getProperties().get(stereotype.getBaseXmiId()).setUnique(mvcProperty.getUnique());
                    domainClass.getProperties().get(stereotype.getBaseXmiId()).setMinLength(mvcProperty.getMinLength());
                    domainClass.getProperties().get(stereotype.getBaseXmiId()).setMaxLength(mvcProperty.getMaxLength());
                    domainClass.getProperties().get(stereotype.getBaseXmiId()).setNullable(mvcProperty.getNullable());
                }
            }
        }
    }

}
