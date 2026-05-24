package com.cloud.publishing.common.reference;

public enum Type {
    JOURNALIST("журналист"),
    EDITOR("редактор");
    private final String label;

    Type(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}