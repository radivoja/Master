package com.project.gui;

import com.project.parser.Component;
import com.project.parser.Loader;
import freemarker.template.TemplateException;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

public class ConsoleApp {

    public static final String PATH = "D:\\WorkspacePapyrus\\Freehold\\Freehold.uml";

    public static final String DESTINATION_JAVA = "D:\\Workspace\\Test-Project\\src\\main\\java\\com\\project\\";

    public static final File TEMPLATE_DIRECTORY = new File("D:\\Workspace\\Master\\XMLParser\\src\\main\\resources\\static");

    private static final String DESTINATION_THYMELEAF = "D:\\Workspace\\Test-Project\\src\\main\\resources\\templates\\";

    public static void main(String[] args) throws IOException, TemplateException, ParserConfigurationException, SAXException {

        // Load Java templates
        Loader javaLoader = new Loader(PATH, DESTINATION_JAVA, TEMPLATE_DIRECTORY);
        javaLoader.generateComponent(Component.ENTITY);
        javaLoader.generateComponent(Component.CONTROLLER);
        javaLoader.generateComponent(Component.REPOSITORY);
        javaLoader.generateComponent(Component.SERVICE);

        // Load Thymeleaf templates
        Loader thymeleafLoader = new Loader(PATH, DESTINATION_THYMELEAF, TEMPLATE_DIRECTORY);
        thymeleafLoader.generateComponent(Component.LIST);
        thymeleafLoader.generateComponent(Component.FORM);
        thymeleafLoader.generateIndex();
    }
}
