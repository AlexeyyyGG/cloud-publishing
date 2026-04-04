package com.cloud.publishing.model;

public enum Type {
    JOURNALIST("Журналист"),
    EDITOR("Редактор");
    private final String label;

    Type(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}