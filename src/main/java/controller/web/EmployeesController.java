package controller.web;

import static constants.Urls.ADD;
import static constants.Urls.ADD_FORM;
import static constants.Urls.EDIT;
import static constants.Urls.ID;
import static constants.Urls.ID_PATH;
import static constants.Urls.WEB_EMPLOYEES;

import dto.EmployeeUpdateRequest;
import java.util.List;
import model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import service.EmployeeService;

@Controller("webEmployeesController")
@RequestMapping(WEB_EMPLOYEES)
public class EmployeesController {
    private final EmployeeService service;

    @Autowired
    public EmployeesController(EmployeeService service) {
        this.service = service;
    }

    @GetMapping
    public String getAll(Model model) {
        List<Employee> employees = service.getAll();
        model.addAttribute("employees", employees);
        return "employees";
    }

    @GetMapping(ADD_FORM)
    public String showAddForm() {
        return "addForm";
    }

    @PostMapping(ADD)
    public String add(Employee employee) {
        service.add(employee);
        return "redirect:/web/employees";
    }

    @GetMapping(EDIT)
    public String showUpdateForm(Model model, @PathVariable(ID) int id) {
        model.addAttribute("employee", service.get(id));
        return  "updateForm";
    }

    @PutMapping(ID_PATH)
    public String update(@PathVariable(ID) int id,
            @ModelAttribute EmployeeUpdateRequest employeeUpdateRequest) {
        service.update(id, employeeUpdateRequest);
        return "redirect:/web/employees";
    }

    @DeleteMapping(ID_PATH)
    public String delete(@PathVariable(ID) int id) {
        service.delete(id);
        return "redirect:/web/employees";
    }
}