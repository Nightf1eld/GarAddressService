package com.tkbbank.address_service.enums;

import lombok.RequiredArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
@Getter
public enum TableMatcher {
    S_ADDR(Arrays.asList("OBJECT_ID"), "1", null),
    S_ADDR_ADM_REL(Arrays.asList("OBJECT_ID"), "1", null),
    S_ADDR_ADM_REL_HIST(Arrays.asList("OBJECT_ID"), "1", null),
    S_ADDR_MUN_REL(Arrays.asList("OBJECT_ID"), "1", null),
    S_ADDR_MUN_REL_HIST(Arrays.asList("OBJECT_ID"), "1", null),
    S_HOUSE(Arrays.asList("OBJECT_ID"), "1", null),
    S_HOUSE_HIST(Arrays.asList("OBJECT_ID"), "1", null),
    S_HOUSE_PARAM(Arrays.asList("OBJECT_ID"), "1", null),
    S_ADDR_ADM_IDX(Arrays.asList("OBJECT_ID", "PAR_OBJECT_ID"), "2", Arrays.asList("1; 1; S_ADDR_ADM_REL")),
    S_ADDR_ADM_IDX_HIST(Arrays.asList("OBJECT_ID", "PAR_OBJECT_ID"), "2", Arrays.asList("NULL; NULL; (SELECT OBJECT_ID, PAR_OBJECT_ID, PATH, ACTIVE_FLG FROM S_ADDR_ADM_REL UNION ALL SELECT DISTINCT OBJECT_ID, PAR_OBJECT_ID, PATH, ACTIVE_FLG FROM S_ADDR_ADM_REL_HIST)")),
    S_ADDR_MUN_IDX(Arrays.asList("OBJECT_ID", "PAR_OBJECT_ID"), "2", Arrays.asList("1; 1; S_ADDR_MUN_REL")),
    S_ADDR_MUN_IDX_HIST(Arrays.asList("OBJECT_ID", "PAR_OBJECT_ID"), "2", Arrays.asList("NULL; NULL; (SELECT OBJECT_ID, PAR_OBJECT_ID, PATH, ACTIVE_FLG FROM S_ADDR_MUN_REL UNION ALL SELECT DISTINCT OBJECT_ID, PAR_OBJECT_ID, PATH, ACTIVE_FLG FROM S_ADDR_MUN_REL_HIST)"));

    private final List<String> indexMatcher;
    private final String tableQueue;
    private final List<String> insertParams;
}
