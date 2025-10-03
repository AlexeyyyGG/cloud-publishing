package service;

import dto.EmployeeUpdateRequest;
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

    public void addEmployee(Employee employee) {
        if (employee.isChiefEditor()) {
            if (repository.existsChiefEditor()) {
                throw new IllegalStateException(CHIEF_EDITOR_EXISTS_MSG);
            }
        }
        if (employee.getPassword() == null || employee.getPassword().isEmpty()) {
            throw new IllegalArgumentException(PASSWORD_REQUIRED_ON_EMPLOYEE_CREATION_MSG);
        }
        repository.add(employee);
    }

    public void updateEmployee(EmployeeUpdateRequest request) {
        Employee existingEmployee = repository.get(request.getId());
        if (existingEmployee == null) {
            throw new IllegalArgumentException(EMPLOYEE_NOT_FOUND_MSG);
        }
        if (request.getPassword() != null && !request.getPassword().isEmpty()) {
            if (request.getPasswordConfirm() == null || !request.getPassword()
                    .equals(request.getPasswordConfirm())) {
                throw new IllegalArgumentException(PASSWORD_MISMATCH_OR_CONFIRMATION_MISSING_MSG);
            }
            existingEmployee.setPassword(request.getPassword());
        }
        existingEmployee.setFirstName(request.getFirstName());
        existingEmployee.setLastName(request.getLastName());
        existingEmployee.setMiddleName(request.getMiddleName());
        existingEmployee.setEmail(request.getEmail());
        existingEmployee.setGender(request.getGender());
        existingEmployee.setBirthYear(request.getBirthYear());
        existingEmployee.setAddress(request.getAddress());
        existingEmployee.setEducation(request.getEducation());
        existingEmployee.setType(request.getType());
        if (request.isChiefEditor()) {
            repository.resetChiefEditorExcept(existingEmployee.getId());
            existingEmployee.setChiefEditor(true);
        }
        repository.update(existingEmployee);
    }

    public Employee getEmployee(int id) {
        if (repository.exist(id)) {
            return repository.get(id);
        } else {
            throw new IllegalArgumentException(EMPLOYEE_NOT_FOUND_MSG);
        }
    }

    public List<Employee> getAllEmployees() {
        return repository.list();
    }

    public void deleteEmployee(int id) {
        if (repository.exist(id)) {
            repository.delete(id);
        } else {
            throw new IllegalArgumentException(EMPLOYEE_NOT_FOUND_MSG);
        }
    }
}