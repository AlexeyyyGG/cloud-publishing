package com.cloud.publishing.dto.response;


public record EmployeeShort(
        Integer id,
        String firstName,
        String lastName,
        String middleName
) {
    public String getShortName() {
        return lastName + " " +
                firstName.charAt(0) + ". " +
                middleName.charAt(0) + ".";
    }
}