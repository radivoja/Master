package com.project.parser;

public enum StereotypeName {
    ENTITY(XmiConstants.PROFILE + ":Entity"),
    KEY(XmiConstants.PROFILE + ":Key"),
    ENTITY_PROPERTY(XmiConstants.PROFILE + ":EntityProperty"),
    TO_STRING(XmiConstants.PROFILE + ":ToString"),
    UNIQUE(XmiConstants.PROFILE + ":Unique"),
    COMMON(XmiConstants.PROFILE + ":Common"),
    PAGEABLE(XmiConstants.PROFILE + ":Pageable"),
    MVC_PROPERTY(XmiConstants.PROFILE + ":MVCProperty"),
    MVC_FORM(XmiConstants.PROFILE + ":MVCForm");

    private final String qName;

    StereotypeName(String qName) {
        this.qName = qName;
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
