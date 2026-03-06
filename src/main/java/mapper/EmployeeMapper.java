package mapper;

import dto.request.EmployeeRequest;
import dto.request.EmployeeUpdateRequest;
import dto.response.EmployeeResponse;
import model.Employee;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {
    @Mapping(target = "id", ignore = true)
    Employee toEntity(EmployeeRequest employeeRequest);

    EmployeeResponse toResponse(Employee employee);

    @Mapping(target = "password", ignore = true)
    @Mapping(target = "passwordConfirm", ignore = true)
    EmployeeUpdateRequest toUpdateRequest(Employee employee);

    @Mapping(target = "id", ignore = true)
    void update(EmployeeUpdateRequest request, @MappingTarget Employee employee);
}