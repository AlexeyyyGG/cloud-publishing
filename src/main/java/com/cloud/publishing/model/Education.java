package com.cloud.publishing.model;

public enum Education {
    SECONDARY("Среднее"),
    VOCATIONAL("Среднее специальное"),
    INCOMPLETE_HIGHER("Неоконченное высшее"),
    HIGHER("Высшее"),
    BACHELOR("Бакалавр"),
    MASTER("Магистр");
    private final String label;

    Education(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}