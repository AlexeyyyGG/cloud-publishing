package controller;

import static constants.Urls.ID;
import static constants.Urls.ID_PATH;
import static constants.Urls.EMPLOYEES;

import dto.EmployeeUpdateRequest;
import java.util.List;
import model.Employee;
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
    public ResponseEntity<List<Employee>> getAll() {
        logger.info("getAll called");
        List<Employee> employees = service.getAll();
        logger.debug("Found {} employees", employees.size());
        return new ResponseEntity<>(employees, HttpStatus.OK);
    }

    @GetMapping(value = ID_PATH, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Employee> get(@PathVariable(ID) int id) {
        logger.info("get called with id={}", id);
        Employee employee = service.get(id);
        return new ResponseEntity<>(employee, HttpStatus.OK);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> add(@RequestBody Employee employee) {
        logger.info("add called with: {}", employee);
        service.add(employee);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping(value = ID_PATH, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> update(
            @PathVariable(ID) int id,
            @RequestBody EmployeeUpdateRequest employeeUpdateRequest
    ) {
        logger.info("update called for id {}", id);
        service.update(id, employeeUpdateRequest);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping(value = {ID_PATH})
    public ResponseEntity<Void> delete(@PathVariable(ID) int id) {
        logger.info("delete called with id {}", id);
        service.delete(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}