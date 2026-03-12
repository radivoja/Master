package com.project.parser;

public enum StereotypeName {
    ENTITY(XmiConstants.MY_META_MODEL_PROFILE + ":Entity"),
    KEY(XmiConstants.MY_META_MODEL_PROFILE + ":Key"),
    ENTITY_PROPERTY(XmiConstants.MY_META_MODEL_PROFILE + ":EntityProperty"),
    TO_STRING(XmiConstants.MY_META_MODEL_PROFILE + ":ToString"),
    UNIQUE(XmiConstants.MY_META_MODEL_PROFILE + ":Unique"),
    COMMON(XmiConstants.MY_META_MODEL_PROFILE + ":Common"),
    PAGEABLE(XmiConstants.MY_META_MODEL_PROFILE + ":Pageable");

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
