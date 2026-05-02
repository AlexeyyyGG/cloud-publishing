package com.cloud.publishing.service;

import com.cloud.publishing.dto.request.EmployeeRequest;
import com.cloud.publishing.dto.request.EmployeeUpdateRequest;
import com.cloud.publishing.dto.response.EmployeeResponse;
import com.cloud.publishing.dto.response.EmployeeShort;
import java.util.List;

/**
 * Service interface for managing employees. Provides operations for creating, updating, retrieving
 * and deleting employees.
 */
public interface EmployeeService {
    /**
     * Creates a new employee.
     *
     * @param request {@link EmployeeRequest} containing employee data
     * @return created {@link EmployeeResponse}
     */
    EmployeeResponse add(EmployeeRequest request);

    /**
     * Updates an existing employee.
     *
     * @param id      identifier of the employee to update
     * @param request {@link EmployeeUpdateRequest} containing updated employee data
     * @return updated {@link EmployeeResponse}
     */
    EmployeeResponse update(int id, EmployeeUpdateRequest request);

    /**
     * Returns employee by id.
     *
     * @param id identifier of the employee
     * @return {@link EmployeeResponse}
     */
    EmployeeResponse get(int id);

    /**
     * Returns employee data prepared for update form.
     *
     * @param id identifier of the employee
     * @return {@link EmployeeUpdateRequest}
     */
    EmployeeUpdateRequest getForUpdate(int id);

    /**
     * Returns list of all employees.
     *
     * @return list of {@link EmployeeResponse}
     */
    List<EmployeeResponse> getAll();

    /**
     * Returns a list of employees with type JOURNALIST.
     *
     * @return list of {@link EmployeeShort}
     */
    List<EmployeeShort> getJournalists();

    /**
     * Returns a list of employees with type EDITOR.
     *
     * @return list of {@link EmployeeShort}
     */
    List<EmployeeShort> getEditors();

    /**
     * Deletes employee by id.
     *
     * @param id identifier of the employee to delete
     */
    void delete(int id);
}