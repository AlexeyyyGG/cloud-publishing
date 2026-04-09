package com.cloud.publishing.service;

import static com.cloud.publishing.constants.employee.EmployeeMessage.EMPLOYEE_NOT_FOUND_MSG;

import com.cloud.publishing.model.Employee;
import com.cloud.publishing.repository.EmployeeRepository;
import com.cloud.publishing.security.EmployeeDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class EmployeeDetailService implements UserDetailsService {
    private final EmployeeRepository repository;

    @Autowired
    public EmployeeDetailService(EmployeeRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Employee employee = repository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(EMPLOYEE_NOT_FOUND_MSG));
        return new EmployeeDetails(employee.email(), employee.password(), employee.chiefEditor());
    }
}