package dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import model.Gender;
import model.Type;

public record EmployeeRequest(
        @JsonProperty("first_name")
        @NotEmpty(message = "First name is required")
        @Size(min = 2, max = 20, message = "First name should be between 2 and 20 characters")
        String firstName,
        @JsonProperty("last_name")
        @NotEmpty(message = "Last name is required")
        @Size(min = 2, max = 20, message = "Last name should be between 2 and 20 characters")
        String lastName,
        @JsonProperty("middle_name")
        @Size(min = 2, max = 20, message = "Middle name should be between 2 and 20 characters")
        String middleName,
        @NotEmpty(message = "Email is required")
        @Email(message = "Email should be valid")
        String email,
        @NotEmpty(message = "Password is required")
        String password,
        @NotNull(message = "Gender must be specified")
        Gender gender,
        @JsonProperty("birth_year")
        @NotNull(message = "Birth year must be specified")
        @Min(value = 1960, message = "Birth year cannot be earlier than 1950")
        @Max(value = 2008, message = "Birth year cannot be later than 2008")
        Integer birthYear,
        @NotEmpty(message = "Address is required")
        String address,
        @NotEmpty(message = "Education is required")
        String education,
        @NotNull(message = "Employee type must be specified")
        Type type,
        @JsonProperty("is_chief_editor")
        Boolean isChiefEditor
) {
}