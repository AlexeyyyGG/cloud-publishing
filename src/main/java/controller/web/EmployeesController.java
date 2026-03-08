package controller.web;

import static constants.Urls.NEW;
import static constants.Urls.EDIT;
import static constants.Urls.ID;
import static constants.Urls.ID_PATH;
import static constants.Urls.WEB_EMPLOYEES;

import dto.request.EmployeeRequest;
import dto.response.EmployeeResponse;
import dto.request.EmployeeUpdateRequest;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
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
        List<EmployeeResponse> employees = service.getAll();
        model.addAttribute("employees", employees);
        return "employees";
    }

    @GetMapping(NEW)
    public String showAddForm(Model model) {
        model.addAttribute("employeeRequest", EmployeeRequest.empty());
        return "new";
    }

    @PostMapping
    public String add(
            @Valid @ModelAttribute("employeeRequest") EmployeeRequest request,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            return "new";
        }
        service.add(request);
        return "redirect:" + WEB_EMPLOYEES;
    }

    @GetMapping(EDIT)
    public String showUpdateForm(Model model, @PathVariable(ID) int id) {
        if (!model.containsAttribute("employeeUpdateRequest")) {
            model.addAttribute("employeeUpdateRequest", service.getForUpdate(id));
        }
        model.addAttribute("employeeId", id);
        return "edit";
    }

    @PutMapping(ID_PATH)
    public String update(
            @PathVariable(ID) int id,
            @Valid @ModelAttribute("employeeUpdateRequest") EmployeeUpdateRequest employeeUpdateRequest,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes
    ) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute(
                    "org.springframework.validation.BindingResult.employeeUpdateRequest",
                    bindingResult
            );
            redirectAttributes.addFlashAttribute("employeeUpdateRequest", employeeUpdateRequest);
            return "redirect:" + WEB_EMPLOYEES + "/" + id + "/edit";
        }
        service.update(id, employeeUpdateRequest);
        return "redirect:" + WEB_EMPLOYEES;
    }

    @DeleteMapping(ID_PATH)
    public String delete(@PathVariable(ID) int id) {
        service.delete(id);
        return "redirect:" + WEB_EMPLOYEES;
    }
}