package com.project.parser;

public enum Component {

    ENTITY     ("entities\\",    "",           ".java"),
    CONTROLLER ("controller\\",  "Controller", ".java"),
    REPOSITORY ("repository\\",  "Repository", ".java"),
    SERVICE    ("service\\",     "Service",    ".java"),
    DAO        ("dao\\",         "Dao",        ".java"),
    MAPPER     ("mapper\\",      "Mapper",     ".java"),
    LIST       ("",              "List",       ".html"),
    FORM       ("",              "Form",       ".html"),
    INDEX      ("",              "index",      ".html");


    private final String subfolder;
    private final String suffix;
    private final String extension;

    Component(String subfolder, String suffix, String extension) {
        this.subfolder = subfolder;
        this.suffix = suffix;
        this.extension = extension;
    }

    public String getSubfolder()  { return subfolder; }
    public String getSuffix()     { return suffix; }
    public String getExtension()  { return extension; }
}