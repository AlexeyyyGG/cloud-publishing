package com.cloud.publishing.repository;

import java.util.List;
import com.cloud.publishing.model.Employee;

public interface EmployeeRepository extends IRepository<Employee, Integer> {
    List<Employee> getAll();

    boolean existsChiefEditor();
}