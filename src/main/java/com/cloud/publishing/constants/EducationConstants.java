package com.cloud.publishing.constants;

public class EducationConstants {
    private EducationConstants() {
    }

    public static final String SQL_GET_EDUCATIONS = "SELECT * FROM education WHERE id = ?";
    public static final String SQL_GET_ALL = "SELECT * FROM education";
    public static final String EDUCATION_NOT_FOUND = "Образование не найдено";
    public static final String EDUCATION_ID = "id";
    public static final String EDUCATION_NAME = "name";
    public static final String EDUCATION_LABEL = "label";
    public static final String ED_ID = "ed_id";
    public static final String ED_NAME = "ed_name";
    public static final String ED_LABEL = "ed_label";
}
