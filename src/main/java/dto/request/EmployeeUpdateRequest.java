package dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import model.Gender;
import model.Type;

public record EmployeeUpdateRequest(
        @JsonProperty("first_name")
        String firstName,
        @JsonProperty("last_name")
        String lastName,
        @JsonProperty("middle_name")
        String middleName,
        String email,
        String password,
        String passwordConfirm,
        Gender gender,
        @JsonProperty("birth_year")
        int birthYear,
        String address,
        String education,
        Type type,
        boolean isChiefEditor
) {
}