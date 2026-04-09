package com.cloud.publishing.mapper;

import com.cloud.publishing.dto.request.EmployeeRequest;
import com.cloud.publishing.dto.request.EmployeeUpdateRequest;
import com.cloud.publishing.dto.response.EmployeeResponse;
import com.cloud.publishing.model.Education;
import com.cloud.publishing.model.Employee;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "education", source = "education")
    Employee toEntity(EmployeeRequest employeeRequest, Education education);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "education", source = "education")
    @Mapping(target = "chiefEditor", source = "request.chiefEditor")
    Employee toEntity(int id, EmployeeUpdateRequest request, Education education);

    EmployeeResponse toResponse(Employee employee);

    @Mapping(target = "educationId", source = "education.id")
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "passwordConfirm", ignore = true)
    EmployeeUpdateRequest toUpdateRequest(Employee employee);
}