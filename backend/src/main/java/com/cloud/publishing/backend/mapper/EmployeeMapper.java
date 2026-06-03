package com.cloud.publishing.backend.mapper;

import com.cloud.publishing.common.dto.request.EmployeeRequest;
import com.cloud.publishing.common.dto.request.EmployeeUpdateRequest;
import com.cloud.publishing.common.dto.response.EmployeeResponse;
import com.cloud.publishing.model.employee.Education;
import com.cloud.publishing.model.employee.Employee;
import java.util.List;
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

    List<EmployeeResponse> toResponse(List<Employee> employees);
}