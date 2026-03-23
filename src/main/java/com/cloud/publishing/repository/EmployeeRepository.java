package com.cloud.publishing.repository;

import java.util.List;
import com.cloud.publishing.model.Employee;
import java.util.Optional;

public interface EmployeeRepository extends IRepository<Employee, Integer> {
    List<Employee> getAll();

    Optional<Employee> findByEmail(String email);

    boolean existsChiefEditor();
}