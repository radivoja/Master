package com.project.parser;

public enum StereotypeName {
    ENTITY("Entity"),
    KEY("Key"),
    ENTITY_PROPERTY("EntityProperty"),
    TO_STRING("ToString"),
    UNIQUE("Unique"),
    COMMON("Common"),
    MVC_PROPERTY("MVCProperty"),
    ID("Id"),
    MVC_LIST("MVCList"),
    MVC_FORM("MVCForm");

    private final String qName;

    StereotypeName(String localName) {
        this.qName = XmiConstants.PROFILE + ":" + localName;
    }

    public String getQName() {
        return qName;
    }

    public static boolean contains(String qName) {
        for (StereotypeName type : values()) {
            if (type.qName.equals(qName)) {
                return true;
            }
        }
        return false;
    }
}
