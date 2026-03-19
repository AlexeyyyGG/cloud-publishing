package com.cloud.publishing.dto.response;

import com.cloud.publishing.model.Gender;
import com.cloud.publishing.model.Type;

public record EmployeeResponse(
        Integer id,
        String firstName,
        String lastName,
        String middleName,
        String email,
        Gender gender,
        int birthYear,
        String address,
        String education,
        Type type,
        boolean chiefEditor
) {
}