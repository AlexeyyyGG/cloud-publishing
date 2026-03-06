package repository;

import java.util.List;
import model.Employee;

public interface EmployeeRepository extends IRepository<Employee, Integer> {
    List<Employee> getAll();

    boolean existsChiefEditor();
}