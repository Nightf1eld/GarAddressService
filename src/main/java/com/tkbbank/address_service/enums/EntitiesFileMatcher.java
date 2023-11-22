package com.tkbbank.address_service.enums;

public enum EntitiesFileMatcher {
    ALL_OBJECTS("^(\\d*)\\D(AS_ADDR_OBJ|AS_ADM_HIERARCHY|AS_MUN_HIERARCHY|AS_HOUSES)(\\D)(\\d)(.*)$"),
    AS_ADDR_OBJ("^(\\d*)\\D(AS_ADDR_OBJ)(\\D)(\\d)(.*)$"),
    AS_ADM_HIERARCHY("^(\\d*)\\D(AS_ADM_HIERARCHY)(\\D)(\\d)(.*)$"),
    AS_MUN_HIERARCHY("^(\\d*)\\D(AS_MUN_HIERARCHY)(\\D)(\\d)(.*)$"),
    AS_HOUSES("^(\\d*)\\D(AS_HOUSES)(\\D)(\\d)(.*)$");

    private final String fileMatcher;

    EntitiesFileMatcher(String fileMatcher) {
        this.fileMatcher = fileMatcher;
    }

    public String getFileMatcher() {
        return fileMatcher;
    }
}
