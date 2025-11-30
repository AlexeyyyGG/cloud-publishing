package controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import service.EmployeeService;

@Controller
@RequestMapping("/employeesPage")
public class EmployeesPageController {
    private final EmployeeService service;

    @Autowired
    public EmployeesPageController(EmployeeService service) {
        this.service = service;
    }

    @GetMapping("list")
    public String getAll(Model model) {
        model.addAttribute("employees", service.getAll());
        return "employees";
    }
}
