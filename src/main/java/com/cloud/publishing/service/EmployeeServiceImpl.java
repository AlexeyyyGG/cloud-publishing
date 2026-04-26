package com.cloud.publishing.service;

import static com.cloud.publishing.constants.employee.EmployeeMessage.*;

import com.cloud.publishing.dto.request.EmployeeRequest;
import com.cloud.publishing.dto.response.EmployeeResponse;
import com.cloud.publishing.dto.request.EmployeeUpdateRequest;
import com.cloud.publishing.exception.InvalidArgumentException;
import com.cloud.publishing.exception.ObjectNotFoundException;
import com.cloud.publishing.model.Education;
import com.cloud.publishing.repository.EducationRepository;
import java.util.List;
import com.cloud.publishing.mapper.EmployeeMapper;
import com.cloud.publishing.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.cloud.publishing.repository.EmployeeRepository;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final EducationRepository educationRepository;
    private final EmployeeMapper mapper;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public EmployeeServiceImpl(
            EmployeeRepository employeeRepository,
            EducationRepository educationRepository,
            EmployeeMapper mapper,
            PasswordEncoder passwordEncoder
    ) {
        this.employeeRepository = employeeRepository;
        this.educationRepository = educationRepository;
        this.mapper = mapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public EmployeeResponse add(EmployeeRequest request) {
        if (request.chiefEditor()) {
            employeeRepository.resetChiefEditor();
        }
        Education education = educationRepository.get(request.educationId());
        Employee employee = mapper.toEntity(request, education);
        String password = passwordEncoder.encode(request.password());
        employee = Employee.withPassword(employee, password);
        Employee saved = employeeRepository.add(employee);
        return mapper.toResponse(saved);
    }

    @Override
    @Transactional
    public EmployeeResponse update(int id, EmployeeUpdateRequest request) {
        Employee current = employeeRepository.get(id);
        if (request.chiefEditor() && !current.chiefEditor()) {
            employeeRepository.resetChiefEditor();
        }
        boolean hasPassword = request.password() != null && !request.password().isEmpty();
        if (hasPassword) {
            if (request.passwordConfirm() == null || !request.password()
                    .equals(request.passwordConfirm())) {
                throw new InvalidArgumentException(PASSWORD_MISMATCH_OR_CONFIRMATION_MISSING_MSG);
            }
        }
        Education education = educationRepository.get(request.educationId());
        Employee employee = mapper.toEntity(id, request, education);
        if (hasPassword) {
            String password = passwordEncoder.encode(request.password());
            employee = Employee.withPassword(employee, password);
        }
        employeeRepository.update(employee);
        return mapper.toResponse(employee);
    }

    @Override
    public EmployeeResponse get(int id) {
        Employee existingEmployee = employeeRepository.get(id);
        return mapper.toResponse(existingEmployee);
    }

    @Override
    public List<EmployeeResponse> getAll() {
        return employeeRepository.getAll()
                .stream()
                .map(mapper::toResponse)
                .toList();
    }

    @Override
    public void delete(int id) {
        if (employeeRepository.exists(id)) {
            employeeRepository.delete(id);
        } else {
            throw new ObjectNotFoundException(EMPLOYEE_NOT_FOUND_MSG);
        }
    }

    @Override
    public EmployeeUpdateRequest getForUpdate(int id) {
        Employee employee = employeeRepository.get(id);
        return mapper.toUpdateRequest(employee);
    }
}