package com.cloud.publishing.frontend.mapper;

import com.cloud.publishing.common.dto.request.EmployeeUpdateRequest;
import com.cloud.publishing.common.dto.response.EmployeeResponse;
import com.cloud.publishing.common.dto.response.EmployeeShort;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface EmployeeWebMapper {
    @Mapping(target = "educationId", source = "education.id")
    @Mapping(target = "password", ignore = true)
    @Mapping(target = "passwordConfirm", ignore = true)
    EmployeeUpdateRequest toUpdateRequest(EmployeeResponse response);

    EmployeeShort toShort(EmployeeResponse employee);
}