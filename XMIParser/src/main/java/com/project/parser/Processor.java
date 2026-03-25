package com.project.parser;

import com.project.model.Property;
import com.project.model.Stereotype;
import com.project.model.UmlClass;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.project.parser.XmiConstants.BASE_CLASS;

public class Processor {

    public Map<String, UmlClass> process(Map<String, UmlClass> umlClasses, List<Stereotype> stereotypes) {
        for (UmlClass umlClass : umlClasses.values()) {
            sortAssociation(umlClass, umlClasses);
            sortStereotype(umlClass, stereotypes);
        }

        sortStereotypeForEntity(umlClasses, stereotypes);
        normalizeNames(umlClasses);
        return umlClasses;

    }

    private void normalizeNames(Map<String, UmlClass> umlClasses) {
        for (UmlClass umlClass : umlClasses.values()) {
            umlClass.setName(umlClass.getName().toLowerCase());
        }
    }


    public Property getRelatedProperty(UmlClass model, String association) {
        return model.getProperties().stream()
                .filter(prop -> prop.getAssociation() != null)
                .filter(prop -> prop.getAssociation().equals(association))
                .findFirst()
                .orElse(null);
    }

    public void sortAssociation(UmlClass model, Map<String, UmlClass> umlClasses) {
        for (Property property : model.getProperties()) {
            if (property.getAssociation() != null) {
                UmlClass relatedModel = umlClasses.get(property.getType());
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

    public void sortStereotype(UmlClass model, List<Stereotype> stereotypes) {
        for (Property property : model.getProperties()) {
            List<Stereotype> stereoList = stereotypes.stream()
                    .filter(stereo -> stereo.getBase().equals(property.getId()))
                    .collect(Collectors.toList());
            property.setStereotypes(stereoList);
        }
    }

    public void sortStereotypeForEntity(Map<String, UmlClass> umlClasses, List<Stereotype> stereotypes) {
        for(Stereotype stereotype : stereotypes){
            if(stereotype.getType().equals(BASE_CLASS)){
                UmlClass model = umlClasses.get(stereotype.getBase());
                model.setEntity(true);
            }

            if(stereotype.getName().equals(StereotypeName.MVC_FORM.getQName())){
                umlClasses.get(stereotype.getBase()).setFormView(true);
            }

        }
    }



}
