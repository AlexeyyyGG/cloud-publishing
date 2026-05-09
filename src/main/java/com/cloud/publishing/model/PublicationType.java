package com.cloud.publishing.model;

public enum PublicationType {
    NEWSPAPER("Газета"),
    MAGAZINE("Журнал");
    private final String label;

    PublicationType(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}