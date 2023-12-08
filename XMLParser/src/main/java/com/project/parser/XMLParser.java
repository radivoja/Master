package com.project.parser;

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

import com.project.model.Model;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class XMLParser {

    private static final String thymeleafPages = "D:\\Workspace\\Test-Project\\src\\main\\resources\\templates\\";
    public static void generate(String path, String destination) throws SAXException, IOException, ParserConfigurationException, TemplateException{
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser saxParser = factory.newSAXParser();
        XMIParser saxHandler = new XMIParser();
        saxParser.parse(path, saxHandler);


		Configuration cfg = new Configuration(Configuration.VERSION_2_3_32);
		
		cfg.setDirectoryForTemplateLoading(new File("D:\\Workspace\\Master\\XMLParser\\src\\main\\resources\\static"));
		Map<String, Object> map = new HashMap<>();
				
	    // Controller
        Template temp = cfg.getTemplate("controller.ftlh");
		Writer fileOut = new FileWriter(destination + "controller\\"+ "AppController.java");
        map.put("list", saxHandler.models.values()); 
        temp.process(map, fileOut);
        

		// Entities
        temp = cfg.getTemplate("entity.ftlh");
        for(Model model : saxHandler.models.values()) {
        //	System.out.println(model);
        	map.put("model", model); 
            fileOut = new FileWriter(destination + "entities\\" + model.getName()+ ".java");
        	temp.process(map, fileOut);
        }
        
        // Repositories
        temp = cfg.getTemplate("repo.ftlh");
        for(Model model : saxHandler.models.values()) {
        	map.put("model", model); 
            fileOut = new FileWriter(destination + "repositories\\" + model.getName() + "Repository.java");
        	temp.process(map, fileOut);
        }


        // Index View
        temp = cfg.getTemplate("index.ftlh");
	    fileOut = new FileWriter(thymeleafPages + "index.html");
        map.put("list", saxHandler.models.values()); 
        temp.process(map, fileOut);
        
        // List View
        temp = cfg.getTemplate("list.ftlh");
        for(Model model : saxHandler.models.values()) {
        	map.put("model", model); 
        	fileOut = new FileWriter(thymeleafPages + model.getName().toLowerCase() + "List.html");
        	temp.process(map, fileOut);
        }
        
        // Form View
        temp = cfg.getTemplate("form.ftlh");
        for(Model model : saxHandler.models.values()) {
        	map.put("model", model); 
            fileOut = new FileWriter(thymeleafPages + model.getName().toLowerCase() + "Form.html");
        	temp.process(map, fileOut);
        }        
    }
}
