package com.tkbbank.address_service.dto.utils;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum ManageCommand {
    LOAD("load"),
    INSERT("insert"),
    UPDATE("update"),
    TRUNCATE("truncate"),
    CREATE_INDEX("create_index"),
    DROP_INDEX("drop_index"),
    INDEX_ENTITIES("index_entities");

    private final String text;

    ManageCommand(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return this.text;
    }

    @JsonCreator
    public static ManageCommand fromText(String text) {
        for (ManageCommand manageCommand : ManageCommand.values()) {
            if (manageCommand.toString().equalsIgnoreCase(text)) {
                return manageCommand;
            }
        }
        throw new IllegalArgumentException();
    }
}