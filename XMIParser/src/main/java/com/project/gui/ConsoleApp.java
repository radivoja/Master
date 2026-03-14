package com.project.gui;

import com.project.parser.Component;
import com.project.parser.Generator;
import com.project.parser.Loader;
import freemarker.template.TemplateException;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

public class ConsoleApp {

    public static final String PATH = "D:\\WorkspacePapyrus\\Freehold\\Freehold.uml";

    public static final String DESTINATION_JAVA = "D:\\Workspace\\Test-Project\\src\\main\\java\\com\\project\\";

    private static final String DESTINATION_THYMELEAF = "D:\\Workspace\\Test-Project\\src\\main\\resources\\templates\\";

    public static void main(String[] args) throws IOException, TemplateException, ParserConfigurationException, SAXException {

        // Load Java templates
        Generator javaGenerator = new Generator(PATH, DESTINATION_JAVA);
        javaGenerator.generateComponent(Component.ENTITY);
        javaGenerator.generateComponent(Component.CONTROLLER);
        javaGenerator.generateComponent(Component.REPOSITORY);
        javaGenerator.generateComponent(Component.SERVICE);
        javaGenerator.generateComponent(Component.DAO);
        javaGenerator.generateComponent(Component.MAPPER);

        // Load Thymeleaf templates
        Generator thymeleafGenerator = new Generator(PATH, DESTINATION_THYMELEAF);
        thymeleafGenerator.generateComponent(Component.LIST);
        thymeleafGenerator.generateComponent(Component.FORM);

        Generator indexGenerator = new Generator(PATH, DESTINATION_THYMELEAF);

        indexGenerator.generateIndex();
    }
}
