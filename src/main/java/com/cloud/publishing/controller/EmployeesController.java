package com.cloud.publishing.controller;

import com.cloud.publishing.constants.Parameters;
import com.cloud.publishing.constants.Urls;
import com.cloud.publishing.dto.request.EmployeeRequest;
import com.cloud.publishing.dto.response.EmployeeResponse;
import com.cloud.publishing.dto.request.EmployeeUpdateRequest;
import com.cloud.publishing.mapper.EmployeeMapper;
import com.cloud.publishing.model.Employee;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.cloud.publishing.service.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping(Urls.EMPLOYEES)
public class EmployeesController {
    private final EmployeeService service;
    private final EmployeeMapper mapper;
    private static final Logger logger = LoggerFactory.getLogger(EmployeesController.class);

    @Autowired
    public EmployeesController(EmployeeService service, EmployeeMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<EmployeeResponse>> getAll() {
        logger.info("getAll called");
        List<Employee> employees = service.getAll();
        logger.debug("Found {} employees", employees.size());
        return ResponseEntity.status(HttpStatus.OK).body(mapper.toResponse(employees));
    }

    @GetMapping(value = Urls.ID, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EmployeeResponse> get(@PathVariable(Parameters.ID) int id) {
        logger.info("get called with id={}", id);
        Employee employee = service.get(id);
        return ResponseEntity.status(HttpStatus.OK).body(mapper.toResponse(employee));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('CHIEF_EDITOR')")
    public ResponseEntity<EmployeeResponse> add(@Valid @RequestBody EmployeeRequest request) {
        logger.info("add called with: {}", request);
        Employee employee = service.add(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toResponse(employee));
    }

    @PutMapping(value = Urls.ID, consumes = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasRole('CHIEF_EDITOR')")
    public ResponseEntity<EmployeeResponse> update(
            @PathVariable(Parameters.ID) int id,
            @Valid @RequestBody EmployeeUpdateRequest employeeUpdateRequest
    ) {
        logger.info("update called for id {}", id);
        Employee updated = service.update(id, employeeUpdateRequest);
        return ResponseEntity.status(HttpStatus.OK).body(mapper.toResponse(updated));
    }

    @DeleteMapping(value = {Urls.ID})
    @PreAuthorize("hasRole('CHIEF_EDITOR')")
    public ResponseEntity<Void> delete(@PathVariable(Parameters.ID) int id) {
        logger.info("delete called with id {}", id);
        service.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}