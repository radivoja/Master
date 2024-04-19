package com.project.gui;

import com.project.parser.Component;
import com.project.parser.Loader;
import com.project.parser.XMLParser;
import freemarker.template.TemplateException;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

public class ConsoleApp {

    public static final String PATH = "D:\\WorkspacePapyrus\\Freehold\\Freehold.uml";

    public static final String DESTINATION = "D:\\Workspace\\Test-Project\\src\\main\\java\\com\\project\\";

    public static final File TEMPLATE_DIRECTORY = new File("D:\\Workspace\\Master\\XMLParser\\src\\main\\resources\\static");

    private static final String DESTINATION_THYMELEAF = "D:\\Workspace\\Test-Project\\src\\main\\resources\\templates\\";

    public static void main(String[] args) throws IOException, TemplateException, ParserConfigurationException, SAXException {
        XMLParser parser = new XMLParser(PATH, DESTINATION, TEMPLATE_DIRECTORY);
        parser.generateComponent(Component.ENTITY);
        parser.generateComponent(Component.CONTROLLER);
        parser.generateComponent(Component.REPOSITORY);

        parser.setDestination(DESTINATION_THYMELEAF);
        parser.generateComponent(Component.LIST);
        parser.generateComponent(Component.FORM);
        parser.generateIndex();
    }
}
