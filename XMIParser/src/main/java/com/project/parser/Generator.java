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

import static com.project.parser.TestConstants.JAVA_ROOT;
import static com.project.parser.TestConstants.TEMPLATE_ROOT;
import static com.project.parser.XmiConstants.*;

public class Generator {
    private Loader loader = new Loader();
    private XmiReader xmiReader = new XmiReader();
    private String umlPath;
    private String projectRoot;
    private Collection<Model> models;


    public Generator(String umlPath, String projectRoot) throws IOException, ParserConfigurationException, SAXException {
        this.umlPath = umlPath;
        this.projectRoot = ensureEndsWithSlash(projectRoot);
        this.models = xmiReader.getModels(umlPath);
    }

    public void generateComponent(Component component) throws IOException, TemplateException {
        Map<String, Object> map = new HashMap<>();
        Template template = loader.loadTemplate(component);
        for(Model model : models) {
            if (component.equals(Component.FORM) && !(model.isFormView())){
                return;
            }

            if (component.equals(Component.LIST) && !(model.isListView())){
                return;
            }


            Writer fileOut = createWriter(component, model);
            map.put(MODEL, model);
            template.process(map, fileOut);
        }
    }

    public void generateIndex() throws IOException, TemplateException {
        Template template = loader.loadTemplate(Component.INDEX);
        Map<String, Object> map = new HashMap<>();

        String fullPath = projectRoot + TEMPLATE_ROOT + INDEX + HTML;
        File file = new File(fullPath);
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }

        try (Writer fileOut = new FileWriter(file)) {
            map.put(MODELS, models);
            template.process(map, fileOut);
        }
    }

    private Writer createWriter(Component component, Model model) throws IOException {
        String modelName = StringUtils.capitalize(model.getName());
        String fileName = modelName + component.getSuffix() + component.getExtension();


        String basePath = resolveBasePath(component);
        String fullPath = basePath + component.getSubfolder() + fileName;

        File file = new File(fullPath);
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        return new FileWriter(file);
    }


    private String resolveBasePath(Component component) {
        if (isTemplateComponent(component)) {
            return projectRoot + TEMPLATE_ROOT;
        }
        return projectRoot + JAVA_ROOT;
    }

    private boolean isTemplateComponent(Component component) {
        return component == Component.LIST
                || component == Component.FORM
                || component == Component.INDEX;
    }

    private String ensureEndsWithSlash(String path) {
        if (path.endsWith("\\") || path.endsWith("/")) {
            return path;
        }
        return path + "\\";
    }
}