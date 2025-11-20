package service;

import dto.EmployeeUpdateRequest;
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

    public void add(Employee employee) {
        if (employee.isChiefEditor()) {
            if (repository.existsChiefEditor()) {
                throw new InvalidArgumentException(CHIEF_EDITOR_EXISTS_MSG);
            }
        }
        if (employee.password() == null || employee.password().isEmpty()) {
            throw new InvalidArgumentException(PASSWORD_REQUIRED_ON_EMPLOYEE_CREATION_MSG);
        }
        repository.add(employee);
    }

    public void update(int id, EmployeeUpdateRequest request) {
        Employee existingEmployee = repository.get(id);
        if (existingEmployee == null) {
            throw new ObjectNotFoundException(EMPLOYEE_NOT_FOUND_MSG);
        }
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
    }

    public Employee get(int id) {
        if (repository.exists(id)) {
            return repository.get(id);
        } else {
            throw new ObjectNotFoundException(EMPLOYEE_NOT_FOUND_MSG);
        }
    }

    public List<Employee> getAll() {
        return repository.getAll();
    }

    public void delete(int id) {
        if (repository.exists(id)) {
            repository.delete(id);
        } else {
            throw new ObjectNotFoundException(EMPLOYEE_NOT_FOUND_MSG);
        }
    }
}