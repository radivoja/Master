package com.project.parser;

import java.util.Map;

public class XmiToJavaTypeMapper {

    private static final Map<String, String> ECORE_TO_JAVA = Map.of(
            "String", "String",
            "Integer", "Integer",
            "ELong", "Long",
            "EDate", "Date",
            "EDouble", "Double",
            "Double", "Double",
            "EBoolean", "Boolean",
            "EFloat", "Float",
            "EInt", "Integer"
    );

    public String map(String href) {
        for (Map.Entry<String, String> entry : ECORE_TO_JAVA.entrySet()) {
            if (href.endsWith(entry.getKey())) {
                return entry.getValue();
            }
        }
        return href;
    }
}