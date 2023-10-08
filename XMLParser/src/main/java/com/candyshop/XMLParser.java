package com.candyshop;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class XMLParser {
	
	
	static String FILE_DIRECTORY = "C:\\Users\\FTN\\Desktop\\Generated\\";
	
	public static void main(String[] args) throws SAXException, IOException, ParserConfigurationException, TemplateException {
		SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser saxParser = factory.newSAXParser();
        MyCustomXMIParser saxHandler = new MyCustomXMIParser();
        saxParser.parse("D:\\workspace\\CirculationModel\\CirculationModel.uml", saxHandler);
        
		Configuration cfg = new Configuration(Configuration.VERSION_2_3_32);
		
		cfg.setDirectoryForTemplateLoading(new File("D:\\Workspace\\Master\\XMLParser\\src\\main\\resources\\static"));
		Map<String, Object> map = new HashMap<>();
				
	    // Controller
        Template temp = cfg.getTemplate("controller.ftlh");
		Writer fileOut = new FileWriter(new File(FILE_DIRECTORY + "AppController.java"));
        map.put("list", saxHandler.models); 
        temp.process(map, fileOut);
        

		// Entities
        temp = cfg.getTemplate("entity.ftlh");
        for(Model model : saxHandler.models) {
        //	System.out.println(model);
        	map.put("model", model); 
            fileOut = new FileWriter(new File(FILE_DIRECTORY + "Entities\\" + model.getClassName() + ".java"));
        	temp.process(map, fileOut);
        }
        
        // Repositories
        temp = cfg.getTemplate("repo.ftlh");
        for(Model model : saxHandler.models) {
        	map.put("model", model); 
            fileOut = new FileWriter(new File(FILE_DIRECTORY + model.getClassName() + "Repository.java"));
        	temp.process(map, fileOut);
        }
        
        // Index View
        temp = cfg.getTemplate("index.ftlh");
	    fileOut = new FileWriter(new File(FILE_DIRECTORY + "index.html"));
        map.put("list", saxHandler.models); 
        temp.process(map, fileOut);
        
        // List View
        temp = cfg.getTemplate("list.ftlh");
        for(Model model : saxHandler.models) {
        	map.put("model", model); 
        	fileOut = new FileWriter(new File(FILE_DIRECTORY + model.getClassName().toLowerCase() + "List.html"));
        	temp.process(map, fileOut);
        }
        
        // Form View
        temp = cfg.getTemplate("form.ftlh");
        for(Model model : saxHandler.models) {
        	map.put("model", model); 
            fileOut = new FileWriter(new File(FILE_DIRECTORY + model.getClassName().toLowerCase() + "Form.html"));
        	temp.process(map, fileOut);
        }          
	}
}
