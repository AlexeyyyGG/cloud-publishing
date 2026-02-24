package model;

import com.fasterxml.jackson.annotation.JsonProperty;

public record Employee(
        Integer id,
        @JsonProperty("first_name")
        String firstName,
        @JsonProperty("last_name")
        String lastName,
        @JsonProperty("middle_name")
        String middleName,
        String email,
        String password,
        Gender gender,
        @JsonProperty("birth_year")
        int birthYear,
        String address,
        String education,
        Type type,
        @JsonProperty("is_chief_editor")
        boolean isChiefEditor
) {
}