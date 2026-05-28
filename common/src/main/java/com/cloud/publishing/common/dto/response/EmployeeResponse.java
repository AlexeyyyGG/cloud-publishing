package com.cloud.publishing.common.dto.response;

import com.cloud.publishing.model.employee.Education;
import com.cloud.publishing.model.employee.Gender;
import com.cloud.publishing.model.employee.Type;

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