package com.tkbbank.address_service.enums;

import lombok.RequiredArgsConstructor;
import lombok.Getter;

@RequiredArgsConstructor
@Getter
public enum EntitiesFileMatcher {
    ALL_OBJECTS("^(\\d*)\\D(AS_ADDR_OBJ|AS_ADM_HIERARCHY|AS_MUN_HIERARCHY|AS_HOUSES|AS_HOUSES_PARAMS)(\\D)(\\d)(.*)$", null),
    AS_ADDR_OBJ("^(\\d*)\\D(AS_ADDR_OBJ)(\\D)(\\d)(.*)$", null),
    AS_ADM_HIERARCHY("^(\\d*)\\D(AS_ADM_HIERARCHY)(\\D)(\\d)(.*)$", null),
    AS_MUN_HIERARCHY("^(\\d*)\\D(AS_MUN_HIERARCHY)(\\D)(\\d)(.*)$", null),
    AS_HOUSES("^(\\d*)\\D(AS_HOUSES)(\\D)(\\d)(.*)$", null),
    AS_HOUSES_PARAMS("^(\\d*)\\D(AS_HOUSES_PARAMS)(\\D)(\\d)(.*)$", null),
    ALL_DICTIONARIES("^(AS_OBJECT_LEVELS|AS_ADDHOUSE_TYPES|AS_ADDR_OBJ_TYPES|AS_HOUSE_TYPES|AS_PARAM_TYPES)(\\D)(\\d)(.*)$", null),
    AS_OBJECT_LEVELS("^(AS_OBJECT_LEVELS)(\\D)(\\d)(.*)$","OBJECTLEVEL"),
    AS_ADDHOUSE_TYPES("^(AS_ADDHOUSE_TYPES)(\\D)(\\d)(.*)$","HOUSETYPE"),
    AS_ADDR_OBJ_TYPES("^(AS_ADDR_OBJ_TYPES)(\\D)(\\d)(.*)$","ADDRESSOBJECTTYPE"),
    AS_HOUSE_TYPES("^(AS_HOUSE_TYPES)(\\D)(\\d)(.*)$","HOUSETYPE"),
    AS_PARAM_TYPES("^(AS_PARAM_TYPES)(\\D)(\\d)(.*)$","PARAMTYPE");

    private final String fileMatcher;
    private final String aliasMatcher;
}
