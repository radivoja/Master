package com.project.parser;

import freemarker.template.Configuration;
import freemarker.template.Template;

import java.io.File;
import java.io.IOException;

import static com.project.parser.XmiConstants.FREEMARKER_EXTENSION;
import static com.project.parser.XmiConstants.STATIC;

public class Loader {
    private Configuration configuration;

    public Loader() throws IOException {
        configuration = setConfiguration();
    }

    private Configuration setConfiguration() throws IOException {
        Configuration cfg = new Configuration(Configuration.VERSION_2_3_32);
        cfg.setInterpolationSyntax(Configuration.SQUARE_BRACKET_INTERPOLATION_SYNTAX);
        cfg.setTagSyntax(Configuration.SQUARE_BRACKET_TAG_SYNTAX);
        File file = new File(STATIC).getAbsoluteFile();
        cfg.setDirectoryForTemplateLoading(file);
        return cfg;
    }

    public Template loadTemplate(Component component) throws IOException {
        return configuration.getTemplate(component.toString().toLowerCase() + FREEMARKER_EXTENSION);
    }
}
