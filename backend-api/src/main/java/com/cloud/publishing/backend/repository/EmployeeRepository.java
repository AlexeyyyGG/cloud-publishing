package com.cloud.publishing.backend.repository;

import com.cloud.publishing.common.dto.response.EmployeeShort;
import java.util.List;
import com.cloud.publishing.backend.model.Employee;
import java.util.Optional;
import java.util.Set;

public interface EmployeeRepository extends IRepository<Employee, Integer> {
    List<Employee> getAll();

    List<EmployeeShort> findById(Set<Integer> ids);

    Optional<Employee> findByEmail(String email);

    void resetChiefEditor();
}