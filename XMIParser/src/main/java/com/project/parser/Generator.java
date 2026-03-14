package com.project.parser;

import com.project.model.Model;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.util.StringUtils;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static com.project.parser.XmiConstants.*;

public class Generator {
    private Loader loader = new Loader();
    private XmiReader xmiReader = new XmiReader();
    private String umlPath;
    private String destination;
    private Collection<Model> models;

    public Generator(String umlPath, String destination) throws IOException, ParserConfigurationException, SAXException {
        this.umlPath = umlPath;
        this.destination = destination;
        this.models = xmiReader.getModels(umlPath);
    }

    public void generateComponent(Component component) throws IOException, TemplateException {
        Map<String, Object> map = new HashMap<>();
        Template template = loader.loadTemplate(component);
        for(Model model : models) {
            Writer fileOut = chooseComponents(component, model);
            map.put(MODEL, model);
            template.process(map, fileOut);
        }
    }

    public void generateIndex() throws IOException, TemplateException, ParserConfigurationException, SAXException {
        Template template = loader.loadTemplate(Component.INDEX);
        Map<String, Object> map = new HashMap<>();
        Writer fileOut = new FileWriter(destination + INDEX + HTML);
        map.put(MODELS, xmiReader.getModels(umlPath));
        template.process(map, fileOut);
    }

    private Writer chooseComponents(Component component, Model model) throws IOException {
        String modelName = StringUtils.capitalize(model.getName());
        String fileName = modelName + component.getSuffix() + component.getExtension();
        String fullPath = destination + component.getSubfolder() + fileName;

        File file = new File(fullPath);
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        return new FileWriter(file);
    }
}