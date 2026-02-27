package service;

import dto.request.EmployeeRequest;
import dto.response.EmployeeResponse;
import dto.request.EmployeeUpdateRequest;
import exception.InvalidArgumentException;
import exception.ObjectNotFoundException;
import java.util.List;
import model.Employee;
import org.springframework.stereotype.Service;
import repository.EmployeeRepository;

@Service
public class EmployeeService {
    private final EmployeeRepository repository;
    private static final String EMPLOYEE_NOT_FOUND_MSG = "Employee not found";
    private static final String PASSWORD_REQUIRED_ON_EMPLOYEE_CREATION_MSG = "Password is required when creating an employee";
    private static final String PASSWORD_MISMATCH_OR_CONFIRMATION_MISSING_MSG = "Passwords do not match or confirmation missing";
    private static final String CHIEF_EDITOR_EXISTS_MSG = "Chief editor already exists";

    public EmployeeService(EmployeeRepository repository) {
        this.repository = repository;
    }

    public EmployeeResponse add(EmployeeRequest request) {
        if (request.isChiefEditor()) {
            if (repository.existsChiefEditor()) {
                throw new InvalidArgumentException(CHIEF_EDITOR_EXISTS_MSG);
            }
        }
        if (request.password() == null || request.password().isEmpty()) {
            throw new InvalidArgumentException(PASSWORD_REQUIRED_ON_EMPLOYEE_CREATION_MSG);
        }
        Employee employee = new Employee(
                null,
                request.firstName(),
                request.lastName(),
                request.middleName(),
                request.email(),
                request.password(),
                request.gender(),
                request.birthYear(),
                request.address(),
                request.education(),
                request.type(),
                request.isChiefEditor()
                );
        Employee saved = repository.add(employee);
        return toResponse(saved);
    }

    public EmployeeResponse update(int id, EmployeeUpdateRequest request) {
        Employee existingEmployee = repository.get(id);
        String newPassword = existingEmployee.password();
        if (request.password() != null && !request.password().isEmpty()) {
            if (request.passwordConfirm() == null || !request.password()
                    .equals(request.passwordConfirm())) {
                throw new InvalidArgumentException(PASSWORD_MISMATCH_OR_CONFIRMATION_MISSING_MSG);
            }
            newPassword = request.password();
        }
        Employee updatedEmployee = new Employee(
                existingEmployee.id(),
                request.firstName(),
                request.lastName(),
                request.middleName(),
                request.email(),
                newPassword,
                request.gender(),
                request.birthYear(),
                request.address(),
                request.education(),
                request.type(),
                request.isChiefEditor()
        );
        repository.update(updatedEmployee);
        return toResponse(updatedEmployee);
    }

    public EmployeeResponse get(int id) {
        Employee existingEmployee = repository.get(id);
        return toResponse(existingEmployee);
    }

    public List<EmployeeResponse> getAll() {
        return repository.getAll()
                .stream()
                .map(this::toResponse)
                .toList();

    }

    public void delete(int id) {
        if (repository.exists(id)) {
            repository.delete(id);
        } else {
            throw new ObjectNotFoundException(EMPLOYEE_NOT_FOUND_MSG);
        }
    }

    private EmployeeResponse toResponse(Employee employee) {
        return new EmployeeResponse(
                employee.id(),
                employee.firstName(),
                employee.lastName(),
                employee.middleName(),
                employee.email(),
                employee.gender(),
                employee.birthYear(),
                employee.address(),
                employee.education(),
                employee.type(),
                employee.isChiefEditor()
        );
    }
}