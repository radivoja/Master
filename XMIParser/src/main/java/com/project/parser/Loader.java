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
    private Collection<Model> models;

    public Loader(String path, String destination, File templateDirectory) throws IOException, ParserConfigurationException, SAXException {
        this.path = path;
        this.destination = destination;
        this.templateDirectory = templateDirectory;
        configuration = setConfiguration();
        this.models = getModel();
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
        Reader reader = new Reader();
        saxParser.parse(path, reader);
        return reader.getModels().values();
    }

    public void generateComponent(Component component) throws IOException, TemplateException {
        Map<String, Object> map = new HashMap<>();
        Template temp = configuration.getTemplate(component.toString().toLowerCase() + ".ftlh");
        for(Model model : models) {
            Writer fileOut = chooseComponent(component, model);
            map.put("model", model);
            temp.process(map, fileOut);
        }
    }

    private Writer chooseComponent(Component component, Model model) throws IOException {
        String modelName = StringUtils.capitalize(model.getName());
        String fileName  = modelName + component.getSuffix() + component.getExtension();
        String fullPath  = destination + component.getSubfolder() + fileName;

        File file = new File(fullPath);
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        return new FileWriter(file);
    }

    public void generateIndex() throws IOException, TemplateException {
        Template temp = configuration.getTemplate( "index.ftlh");
        Map<String, Object> map = new HashMap<>();
        Writer fileOut = new FileWriter(destination + "index.html");
        map.put("models", models);
        temp.process(map, fileOut);
    }
}
