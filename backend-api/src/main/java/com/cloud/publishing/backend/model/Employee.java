package com.cloud.publishing.backend.model;

import com.cloud.publishing.common.reference.Education;
import com.cloud.publishing.common.reference.Gender;
import com.cloud.publishing.common.reference.Type;

public record Employee(
        Integer id,
        String firstName,
        String lastName,
        String middleName,
        String email,
        String password,
        Gender gender,
        int birthYear,
        String address,
        Education education,
        Type type,
        boolean chiefEditor
) {
    public static Employee withPassword(Employee employee, String password) {
        return new Employee(
                employee.id(),
                employee.firstName(),
                employee.lastName(),
                employee.middleName(),
                employee.email(),
                password,
                employee.gender(),
                employee.birthYear(),
                employee.address(),
                employee.education(),
                employee.type(),
                employee.chiefEditor()
        );
    }
}