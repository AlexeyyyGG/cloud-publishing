package com.cloud.publishing.common.dto.response;

import com.cloud.publishing.common.reference.Education;
import com.cloud.publishing.common.reference.Gender;
import com.cloud.publishing.common.reference.Type;

public record EmployeeResponse(
        Integer id,
        String firstName,
        String lastName,
        String middleName,
        String email,
        Gender gender,
        int birthYear,
        String address,
        Education education,
        Type type,
        boolean chiefEditor
) {
}