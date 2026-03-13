package service;

import dto.request.EmployeeRequest;
import dto.response.EmployeeResponse;
import dto.request.EmployeeUpdateRequest;
import exception.InvalidArgumentException;
import exception.ObjectNotFoundException;
import java.util.List;
import mapper.EmployeeMapper;
import model.Employee;
import org.springframework.stereotype.Service;
import repository.EmployeeRepository;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository repository;
    private final EmployeeMapper mapper;
    private static final String EMPLOYEE_NOT_FOUND_MSG = "Employee not found";
    private static final String PASSWORD_REQUIRED_ON_EMPLOYEE_CREATION_MSG = "Password is required when creating an employee";
    private static final String PASSWORD_MISMATCH_OR_CONFIRMATION_MISSING_MSG = "Passwords do not match or confirmation missing";
    private static final String CHIEF_EDITOR_EXISTS_MSG = "Chief editor already exists";

    public EmployeeServiceImpl(EmployeeRepository repository, EmployeeMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public EmployeeResponse add(EmployeeRequest request) {
        if (request.chiefEditor()) {
            if (repository.existsChiefEditor()) {
                throw new InvalidArgumentException(CHIEF_EDITOR_EXISTS_MSG);
            }
        }
        if (request.password() == null || request.password().isEmpty()) {
            throw new InvalidArgumentException(PASSWORD_REQUIRED_ON_EMPLOYEE_CREATION_MSG);
        }
        Employee employee = mapper.toEntity(request);
        Employee saved = repository.add(employee);
        return mapper.toResponse(saved);
    }

    @Override
    public EmployeeResponse update(int id, EmployeeUpdateRequest request) {
        boolean hasPassword = request.password() != null && !request.password().isEmpty();
        if (hasPassword) {
            if (request.passwordConfirm() == null || !request.password()
                    .equals(request.passwordConfirm())) {
                throw new InvalidArgumentException(PASSWORD_MISMATCH_OR_CONFIRMATION_MISSING_MSG);
            }
        }
        Employee employee = mapper.toEntity(id, request);
        if (hasPassword) {
            employee = Employee.withPassword(employee, request.password());
        }
        repository.update(employee);
        return mapper.toResponse(employee);
    }

    @Override
    public EmployeeResponse get(int id) {
        Employee existingEmployee = repository.get(id);
        return mapper.toResponse(existingEmployee);
    }

    @Override
    public List<EmployeeResponse> getAll() {
        return repository.getAll()
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    public void delete(int id) {
        if (repository.exists(id)) {
            repository.delete(id);
        } else {
            throw new ObjectNotFoundException(EMPLOYEE_NOT_FOUND_MSG);
        }
    }

    @Override
    public EmployeeUpdateRequest getForUpdate(int id) {
        Employee employee = repository.get(id);
        return mapper.toUpdateRequest(employee);
    }
}