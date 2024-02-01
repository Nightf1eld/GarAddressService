package com.tkbbank.address_service.dto.utils;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum ManageCommand {
    LOAD("load"), TRUNCATE("truncate");

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