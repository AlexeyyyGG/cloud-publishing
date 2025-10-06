package controller;

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
@RequestMapping(EmployeeController.EMPLOYEES)
public class EmployeeController {
    static final String EMPLOYEES = "/employees";
    private static final String ID_PATH = "/{id}";
    private static final String ID = "id";
    private final EmployeeService service;
    private static final Logger logger = LoggerFactory.getLogger(EmployeeController.class);

    @Autowired
    public EmployeeController(EmployeeService service) {
        this.service = service;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getAllEmployees() {
        logger.info("getAllEmployees called");
        List<Employee> employees = service.getAllEmployees();
        logger.debug("Found {} employees", employees.size());
        return new ResponseEntity<>(employees, HttpStatus.OK);
    }

    @GetMapping(value = ID_PATH, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getEmployee(@PathVariable(ID) int id) {
        logger.info("getEmployee called with id={}", id);
        Employee employee = service.getEmployee(id);
        return new ResponseEntity<>(employee, HttpStatus.OK);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> addEmployee(@RequestBody Employee employee) {
        logger.info("addEmployee called with: {}", employee);
        service.addEmployee(employee);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping(value = ID_PATH, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateEmployee(
            @PathVariable(ID) int id,
            @RequestBody EmployeeUpdateRequest employeeUpdateRequest
    ) {
        logger.info("updateEmployee called for id {}", id);
        service.updateEmployee(id, employeeUpdateRequest);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping(value = {ID_PATH})
    public ResponseEntity<?> deletePrincess(@PathVariable(ID) int id) {
        logger.info("deleteEmployee called with id {}", id);
        service.deleteEmployee(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}