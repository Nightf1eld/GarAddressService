package com.tkbbank.address_service.enums;

import lombok.RequiredArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
@Getter
public enum TableIndexesMatcher {
    S_ADDR(Arrays.asList("OBJECT_ID")),
    S_ADDR_ADM_IDX(Arrays.asList("OBJECT_ID")),
    S_ADDR_ADM_IDX_HIST(Arrays.asList("OBJECT_ID")),
    S_ADDR_ADM_REL(Arrays.asList("OBJECT_ID")),
    S_ADDR_ADM_REL_HIST(Arrays.asList("OBJECT_ID")),
    S_ADDR_HIST(Arrays.asList("OBJECT_ID")),
    S_ADDR_MUN_IDX(Arrays.asList("OBJECT_ID")),
    S_ADDR_MUN_IDX_HIST(Arrays.asList("OBJECT_ID")),
    S_ADDR_MUN_REL(Arrays.asList("OBJECT_ID")),
    S_ADDR_MUN_REL_HIST(Arrays.asList("OBJECT_ID")),
    S_HOUSE(Arrays.asList("OBJECT_ID")),
    S_HOUSE_HIST(Arrays.asList("OBJECT_ID")),
    S_HOUSE_PARAM(Arrays.asList("OBJECT_ID"));

    private final List<String> indexMatcher;
}
