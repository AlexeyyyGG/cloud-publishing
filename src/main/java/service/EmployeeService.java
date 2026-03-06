package service;

import dto.request.EmployeeRequest;
import dto.request.EmployeeUpdateRequest;
import dto.response.EmployeeResponse;
import java.util.List;

/**
 * Service interface for managing employees. Provides operations for creating, updating, retrieving
 * and deleting employees.
 */
public interface EmployeeService {
    /**
     * Creates a new employee.
     *
     * @param request object containing employee data
     * @return created employee
     */
    EmployeeResponse add(EmployeeRequest request);

    /**
     * Updates an existing employee.
     *
     * @param id      identifier of the employee to update
     * @param request object containing updated employee data
     * @return updated employee
     */
    EmployeeResponse update(int id, EmployeeUpdateRequest request);

    /**
     * Returns employee by id.
     *
     * @param id identifier of the employee
     * @return employee
     */
    EmployeeResponse get(int id);

    /**
     * Returns employee data prepared for update form.
     *
     * @param id identifier of the employee
     * @return employee
     */
    EmployeeUpdateRequest getForUpdate(int id);

    /**
     * Returns list of all employees.
     *
     * @return list of employees
     */
    List<EmployeeResponse> getAll();

    /**
     * Deletes employee by id.
     *
     * @param id identifier of the employee to delete
     */
    void delete(int id);
}