package com.project.parser;

import com.project.domain.DomainClass;
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

public class FileGenerator {
    private Loader loader = new Loader();
    private String projectRoot;
    private Reader reader = new Reader();
    private UmlModelResolver resolver;
    private Collection<DomainClass> domainClasses;

    public FileGenerator(String umlPath, String projectRoot) throws ParserConfigurationException, SAXException, IOException {
        this.projectRoot = ensureEndsWithSlash(projectRoot);
        this.resolver = new UmlModelResolver(umlPath);
        domainClasses = resolver.prepareDomainModel().values();
    }

    public void generateComponent(Component component) throws IOException, TemplateException {
        Map<String, Object> dataModel = new HashMap<>();
        Template template = loader.loadTemplate(component);

        for(DomainClass domainClass : domainClasses) {
            if(component.name().equals(Component.FORM) && domainClass.getFormView() == null) {
                continue;
            }

            if(component.name().equals(Component.LIST) && domainClass.getListView() == null) {
                continue;
            }

            Writer fileOut = createWriter(component, domainClass);
            dataModel.put(MODEL, domainClass);
            template.process(dataModel, fileOut);
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
            map.put(MODELS, domainClasses);
            template.process(map, fileOut);
        }
    }

    private Writer createWriter(Component component, DomainClass model) throws IOException {
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
        if (isThymeleafComponent(component)) {
            return projectRoot + TEMPLATE_ROOT;
        }
        return projectRoot + JAVA_ROOT;
    }

    private boolean isThymeleafComponent(Component component) {
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