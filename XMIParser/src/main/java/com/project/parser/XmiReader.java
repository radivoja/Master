package com.project.parser;

import com.project.model.Model;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.util.Collection;

public class XmiReader {
    private Reader reader = new Reader();
    // Parses the UML file and returns all parsed models that are marked as entities
    public Collection<Model> getModels(String umlFilePath) throws ParserConfigurationException, SAXException, IOException {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser saxParser = factory.newSAXParser();
        saxParser.parse(umlFilePath, reader);
        return reader.getEntityModels().values();
    }
}
