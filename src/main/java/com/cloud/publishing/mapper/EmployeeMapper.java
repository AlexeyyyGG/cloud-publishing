package com.cloud.publishing.mapper;

import com.cloud.publishing.dto.request.EmployeeRequest;
import com.cloud.publishing.dto.request.EmployeeUpdateRequest;
import com.cloud.publishing.dto.response.EmployeeResponse;
import com.cloud.publishing.model.Employee;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {
    @Mapping(target = "id", ignore = true)
    Employee toEntity(EmployeeRequest employeeRequest);

    @Mapping(target = "id", source = "id")
    @Mapping(target = "password", ignore = true)
    Employee toEntity(int id, EmployeeUpdateRequest request);

    EmployeeResponse toResponse(Employee employee);

    @Mapping(target = "password", ignore = true)
    @Mapping(target = "passwordConfirm", ignore = true)
    EmployeeUpdateRequest toUpdateRequest(Employee employee);
}