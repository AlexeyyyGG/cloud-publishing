package controller;

import static constants.Urls.ID;
import static constants.Urls.ID_PATH;
import static constants.Urls.EMPLOYEES;

import dto.request.EmployeeRequest;
import dto.response.EmployeeResponse;
import dto.request.EmployeeUpdateRequest;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import service.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping(EMPLOYEES)
public class EmployeesController {
    private final EmployeeService service;
    private static final Logger logger = LoggerFactory.getLogger(EmployeesController.class);

    @Autowired
    public EmployeesController(EmployeeService service) {
        this.service = service;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<EmployeeResponse>> getAll() {
        logger.info("getAll called");
        List<EmployeeResponse> employees = service.getAll();
        logger.debug("Found {} employees", employees.size());
        return ResponseEntity.status(HttpStatus.OK).body(employees);
    }

    @GetMapping(value = ID_PATH, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EmployeeResponse> get(@PathVariable(ID) int id) {
        logger.info("get called with id={}", id);
        EmployeeResponse employee = service.get(id);
        return ResponseEntity.status(HttpStatus.OK).body(employee);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EmployeeResponse> add(@RequestBody EmployeeRequest request) {
        logger.info("add called with: {}", request);
        return ResponseEntity.status(HttpStatus.CREATED).body(service.add(request));
    }

    @PutMapping(value = ID_PATH, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<EmployeeResponse> update(
            @PathVariable(ID) int id,
            @RequestBody EmployeeUpdateRequest employeeUpdateRequest
    ) {
        logger.info("update called for id {}", id);
        EmployeeResponse updated = service.update(id, employeeUpdateRequest);
        return ResponseEntity.status(HttpStatus.OK).body(updated);
    }

    @DeleteMapping(value = {ID_PATH})
    public ResponseEntity<Void> delete(@PathVariable(ID) int id) {
        logger.info("delete called with id {}", id);
        service.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}