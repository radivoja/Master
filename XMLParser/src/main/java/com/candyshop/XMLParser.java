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
	
	
	static String FILE_DIRECTORY = "C:\\Users\\User\\Desktop\\Generated";
	
	public static void main(String[] args) throws SAXException, IOException, ParserConfigurationException, TemplateException {
		SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser saxParser = factory.newSAXParser();
        MyCustomXMIParser saxHandler = new MyCustomXMIParser();
        saxParser.parse("D:\\WorkspacePapyrus\\SellerProject\\SellerProject.uml", saxHandler);
        
		Configuration cfg = new Configuration(Configuration.VERSION_2_3_32);
		cfg.setDirectoryForTemplateLoading(new File("D:\\Workspace\\Master\\XMLParser\\src\\main\\resources\\static"));
		Map<String, Object> map = new HashMap<>();
				
	    // C
        Template temp = cfg.getTemplate("controller.ftlh");
		Writer fileOut = new FileWriter(new File(FILE_DIRECTORY + "AppController.java"));
        map.put("list", saxHandler.models); 
        temp.process(map, fileOut);
        

		// B
        temp = cfg.getTemplate("bean.ftlh");
        for(Model model : saxHandler.models) {
        //	System.out.println(model);
        	map.put("model", model); 
            fileOut = new FileWriter(new File(FILE_DIRECTORY + model.getClassName() + ".java"));
        	temp.process(map, fileOut);
        }
        
        // F
        temp = cfg.getTemplate("front.ftlh");
        for(Model model : saxHandler.models) {
        	map.put("model", model); 
        	fileOut = new FileWriter(new File(FILE_DIRECTORY + "view" + model.getClassName() + "Front.html"));
        	temp.process(map, fileOut);
        }
        
        // R
        temp = cfg.getTemplate("repo.ftlh");
        for(Model model : saxHandler.models) {
        	map.put("model", model); 
            fileOut = new FileWriter(new File(FILE_DIRECTORY + model.getClassName() + "Repository.java"));
        	temp.process(map, fileOut);
        }       
	}
}
