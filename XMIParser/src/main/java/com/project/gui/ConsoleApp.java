package com.project.gui;

import com.project.parser.Component;
import com.project.parser.Generator;

import static com.project.parser.TestConstants.CIRCULATION_UML;
import static com.project.parser.TestConstants.PROJECT_ROOT;

public class ConsoleApp {

    public static void main(String[] args) throws Exception {

        Generator generator = new Generator(CIRCULATION_UML, PROJECT_ROOT);

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
