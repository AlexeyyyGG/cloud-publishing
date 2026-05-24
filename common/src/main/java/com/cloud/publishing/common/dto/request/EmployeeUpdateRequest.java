package com.cloud.publishing.common.dto.request;

import com.cloud.publishing.common.reference.Type;
import com.cloud.publishing.common.reference.Gender;
import com.cloud.publishing.common.validation.PasswordMatchable;
import com.cloud.publishing.common.validation.PasswordMatches;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@PasswordMatches(required = false)
public record EmployeeUpdateRequest(
        @JsonProperty("first_name")
        @NotEmpty(message = "Имя обязательно для заполнения")
        @Size(min = 2, max = 20, message = "Имя должно содержать от 2 до 20 символов")
        String firstName,
        @JsonProperty("last_name")
        @NotEmpty(message = "Фамилия обязательна для заполнения")
        @Size(min = 2, max = 20, message = "Фамилия должна содержать от 2 до 20 символов")
        String lastName,
        @JsonProperty("middle_name")
        @Size(min = 2, max = 20, message = "Отчество должно содержать от 2 до 20 символов")
        String middleName,
        @NotEmpty(message = "Email обязателен для заполнения")
        @Email(message = "Введите корректный email")
        String email,
        String password,
        @JsonProperty("password_confirm")
        String passwordConfirm,
        @NotNull(message = "Необходимо указать пол")
        Gender gender,
        @JsonProperty("birth_year")
        @NotNull(message = "Необходимо указать год рождения")
        @Min(value = 1960, message = "Год рождения не может быть раньше 1960")
        @Max(value = 2008, message = "Год рождения не может быть позже 2008")
        Integer birthYear,
        @NotEmpty(message = "Адрес обязателен для заполнения")
        String address,
        @NotNull(message = "Необходимо указать образование")
        Integer educationId,
        @NotNull(message = "Необходимо указать тип сотрудника")
        Type type,
        @JsonProperty("is_chief_editor")
        boolean chiefEditor
) implements PasswordMatchable {
}