package com.cloud.publishing.repository;

import com.cloud.publishing.model.Type;
import java.util.List;
import com.cloud.publishing.model.Employee;
import java.util.Optional;
import java.util.Set;

public interface EmployeeRepository extends IRepository<Employee, Integer> {
    List<Employee> getAll();

    List<Employee> findByIdsAndType(Set<Integer> ids, Type type);

    List<Employee> findByType(Type type);

    Optional<Employee> findByEmail(String email);

    void resetChiefEditor();
}