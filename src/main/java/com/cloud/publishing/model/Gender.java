package com.cloud.publishing.model;

public enum Gender {
    MALE("Мужской", "М"),
    FEMALE("Женский", "Ж");
    private final String label;
    private final String shortLabel;

    Gender(String label, String shortLabel) {
        this.label = label;
        this.shortLabel = shortLabel;
    }

    public String getLabel() {
        return label;
    }

    public String getShortLabel() {
        return shortLabel;
    }
}