package com.project.gui;

import com.project.parser.Component;
import com.project.parser.Generator;
import freemarker.template.TemplateException;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

import static com.project.parser.TestConstants.PROJECT_ROOT;
import static com.project.parser.TestConstants.UML_PATH;

public class ConsoleApp {

    public static void main(String[] args) throws IOException, TemplateException, ParserConfigurationException, SAXException {

        Generator generator = new Generator(UML_PATH, PROJECT_ROOT);

        generator.generateComponent(Component.ENTITY);
        generator.generateComponent(Component.CONTROLLER);
        generator.generateComponent(Component.REPOSITORY);
        generator.generateComponent(Component.SERVICE);
        generator.generateComponent(Component.DAO);
        generator.generateComponent(Component.MAPPER);

        generator.generateComponent(Component.LIST);
        generator.generateComponent(Component.FORM);

        generator.generateIndex();
    }
}
