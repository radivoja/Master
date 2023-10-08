package com.candyshop;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

public class MyCustomXMIParser extends DefaultHandler{

	List<Model> models = new ArrayList<>();
	List<Association> associations = new ArrayList<>();
	Map<String, Stereotype> stereotypes = new HashMap<>();
	Model model = null;
	Association association = null;
	Property property = null;
	Stereotype stereotype = null;
	
	@Override
	public void startDocument() throws SAXException {
	}

	@Override
	public void endDocument() throws SAXException {
		sortAssociations();
		
		for(Model model : models) {
			for(Association association : associations) {
				if(model.getClassName().toLowerCase().equals(association.getLeftEntity()))
					model.getAssociations().add(association);
			}

			for(int i = 0; i < model.getProperties().size(); i++) {
				String id = model.getProperties().get(i).getId();
				Stereotype stereo = stereotypes.get(id);
				if(stereo != null) {
					model.getProperties().get(i).setStereotype(stereo.getName());
					model.getProperties().get(i).setLength(stereo.getLength());
				}
			}
		}
	}

	public void sortAssociations() {
		for(int i = 0; i < associations.size()-1; ++i) {
			for(int j = i+1; j < associations.size(); ++j) {
				if(associations.get(i).getRightCardinality() == null) {
					if(associations.get(i).getId().equals(associations.get(j).getId())) {
						associations.get(i).setRightCardinality(associations.get(j).getLeftCardinality());
						associations.get(j).setRightCardinality(associations.get(i).getLeftCardinality());
						
						if(associations.get(j).getLeftCardinality().equals("Many") && associations.get(j).getRightCardinality().equals("One"))
							continue;						
						associations.get(j).setMappedBy("map");
					
					}
				}
			}
		}
		var count = 1;
		for(Association association : associations) {
			System.out.println(count++ + "." +association);
		}
	}

	@Override
	public void startElement(String uri, String localName, String qualifiedName, Attributes attributes) throws SAXException {

		if(qualifiedName.equals("packagedElement") && attributes.getValue("xmi:type").equals("uml:Class")) {	
			model = new Model();
			model.setClassName(attributes.getValue("name"));
			models.add(model);
		}	
		if(qualifiedName.equals("ownedAttribute")) { 
			property = new Property();
			property.setId(attributes.getValue("xmi:id"));
			property.setName(attributes.getValue("name"));

			if(attributes.getValue("association") != null) {
				association = new Association();
				association.setId(attributes.getValue("association"));
				association.setLeftEntity(model.getClassName().toLowerCase());
				association.setLeftCardinality("One");
				association.setRightEntity(attributes.getValue("name"));
				associations.add(association);
			}
			else {
				model.getProperties().add(property);
			}
		}

		if(qualifiedName.equals("upperValue") ) { 
			association.setLeftCardinality("Many");
		}

		if(qualifiedName.equals("type")) {
			if(attributes.getValue("href").endsWith("String")) {
				property.setType("String");
			}
			if(attributes.getValue("href").endsWith("Integer")) {
				property.setType("Integer");
			}
			if(attributes.getValue("href").endsWith("ELong")) {
				property.setType("Long");
			}
			if(attributes.getValue("href").endsWith("EDate")) {
				property.setType("Date");
			}	
			if(attributes.getValue("href").endsWith("EDouble")) {
				property.setType("Double");
			}
		}

		if(qualifiedName.startsWith("MyMetaModel:")){
			stereotype = new Stereotype();
			stereotype.setLength(attributes.getValue("length"));
			stereotype.setName(stereotypeName(qualifiedName));
			stereotypes.put(attributes.getValue("base_Property"), stereotype);
		}
	}
	
	public String propertyType(String name) {
		switch(name) {
			case "ELong" :
				return "Long";
			case "EDate" :
				return "Date";
			case "EDouble" :
				return "String";
		}
		return name;
	}
		
	public String stereotypeName(String name) {
		switch(name){
			case "MyMetaModel:Key":
				return "Id";
			case "MyMetaModel:Unique":
			    return "Unique";
			case "MyMetaModel:Common":
			    return "Column";
		}
		return "";
	}

	@Override
	public void endElement(String uri, String localName, String qualifiedName) throws SAXException {

	}

}
