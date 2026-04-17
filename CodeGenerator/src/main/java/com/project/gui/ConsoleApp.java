package com.project.gui;

import com.project.parser.Component;
import com.project.parser.FileGenerator;

import static com.project.parser.TestConstants.CIRCULATION_UML;
import static com.project.parser.TestConstants.PROJECT_ROOT;

public class ConsoleApp {

    public static void main(String[] args) throws Exception {

        FileGenerator fileGenerator = new FileGenerator(CIRCULATION_UML, PROJECT_ROOT);

        fileGenerator.generateComponent(Component.ENTITY);
        fileGenerator.generateComponent(Component.CONTROLLER);
        fileGenerator.generateComponent(Component.REPOSITORY);
        fileGenerator.generateComponent(Component.SERVICE);
        fileGenerator.generateComponent(Component.DAO);
        fileGenerator.generateComponent(Component.MAPPER);

        fileGenerator.generateComponent(Component.LIST);
        fileGenerator.generateComponent(Component.FORM);

        fileGenerator.generateIndex();
    }
}
