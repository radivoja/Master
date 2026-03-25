package com.project.parser;

import com.project.model.UmlClass;
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
    private String projectRoot;
    private Collection<UmlClass> umlClasses;
    private XmiReader xmiReader;


    public Generator(String umlPath, String projectRoot) throws IOException, ParserConfigurationException, SAXException {
        xmiReader = new XmiReader(umlPath);
        this.projectRoot = ensureEndsWithSlash(projectRoot);
        this.umlClasses = xmiReader.getUmlClasses();
    }

    public void generateComponent(Component component) throws IOException, TemplateException {
        Map<String, Object> map = new HashMap<>();
        Template template = loader.loadTemplate(component);

        for(UmlClass model : umlClasses) {
            if (component.equals(Component.FORM)) {
                if(!model.isFormView()){
                    continue;
                }

            }
            if (component.equals(Component.LIST)) {
                if(!model.isFormView()) {
                    continue;
                }
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
            map.put(MODELS, umlClasses);
            template.process(map, fileOut);
        }
    }

    private Writer createWriter(Component component, UmlClass model) throws IOException {
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