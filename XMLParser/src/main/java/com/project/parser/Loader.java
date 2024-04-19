package com.project.parser;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.util.StringUtils;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;

import com.project.model.Model;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class Loader {

    private String path;
    private String destination;
    private File templateDirectory;
    private Configuration configuration;

    public Loader(String path, String destination, File templateDirectory) throws IOException {
        this.path = path;
        this.destination = destination;
        this.templateDirectory = templateDirectory;
        configuration = setConfiguration();
    }

    private Configuration setConfiguration() throws IOException {
        Configuration cfg = new Configuration(Configuration.VERSION_2_3_32);
        cfg.setInterpolationSyntax(Configuration.SQUARE_BRACKET_INTERPOLATION_SYNTAX);
        cfg.setTagSyntax(Configuration.SQUARE_BRACKET_TAG_SYNTAX);
        cfg.setDirectoryForTemplateLoading(templateDirectory);
        return cfg;
    }

    private Collection<Model> getModel() throws ParserConfigurationException, SAXException, IOException {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser saxParser = factory.newSAXParser();
        XMIParser xmiParser = new XMIParser();
        saxParser.parse(path, xmiParser);
        return xmiParser.models.values();
    }
    public void generateComponent(Component component) throws ParserConfigurationException, IOException, SAXException, TemplateException {
        Map<String, Object> map = new HashMap<>();
        Template temp = configuration.getTemplate(component.toString().toLowerCase() + ".ftlh");
        for(Model model : getModel()) {
            Writer fileOut = chooseComponent(component, model);
            map.put("model", model);
            temp.process(map, fileOut);
        }
    }

    private Writer chooseComponent(Component component, Model model) throws IOException {
        String componentName = component.toString().toLowerCase();
        if(component.equals(Component.CONTROLLER)) {
            return new FileWriter(destination + componentName + "\\" + StringUtils.capitalize(model.getName()) + StringUtils.capitalize(componentName) + ".java");
        } else if (component.equals(Component.LIST) || component.equals(Component.FORM)) {
            return new FileWriter(destination + model.getName() + StringUtils.capitalize(componentName) + ".html");
        } else {
            if((component.equals(Component.ENTITY))){
                return new FileWriter(destination + componentName + "\\" + StringUtils.capitalize(model.getName()) +  ".java");
            } else {
                return new FileWriter(destination + componentName + "\\" + StringUtils.capitalize(model.getName()) + StringUtils.capitalize(componentName) + ".java");
            }
        }
    }

    public void generateIndex() throws ParserConfigurationException, IOException, SAXException, TemplateException {
        Template temp = configuration.getTemplate( "index.ftlh");
        Map<String, Object> map = new HashMap<>();
        Writer fileOut = new FileWriter(destination + "index.html");
        map.put("models", getModel());
        temp.process(map, fileOut);
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }
}
