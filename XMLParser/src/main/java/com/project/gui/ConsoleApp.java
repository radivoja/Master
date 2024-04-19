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

    public static final String DESTINATION = "D:\\Workspace\\Test-Project\\src\\main\\java\\com\\project\\";

    public static final File TEMPLATE_DIRECTORY = new File("D:\\Workspace\\Master\\XMLParser\\src\\main\\resources\\static");

    private static final String DESTINATION_THYMELEAF = "D:\\Workspace\\Test-Project\\src\\main\\resources\\templates\\";

    public static void main(String[] args) throws IOException, TemplateException, ParserConfigurationException, SAXException {
        Loader loader = new Loader(PATH, DESTINATION, TEMPLATE_DIRECTORY);
        loader.generateComponent(Component.ENTITY);
        loader.generateComponent(Component.CONTROLLER);
        loader.generateComponent(Component.REPOSITORY);

        loader.setDestination(DESTINATION_THYMELEAF);
        loader.generateComponent(Component.LIST);
        loader.generateComponent(Component.FORM);
        loader.generateIndex();
    }
}
