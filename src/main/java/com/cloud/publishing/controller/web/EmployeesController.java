package com.cloud.publishing.controller.web;

import com.cloud.publishing.constants.Parameters;
import com.cloud.publishing.constants.Urls;
import com.cloud.publishing.dto.request.EmployeeRequest;
import com.cloud.publishing.dto.response.EmployeeResponse;
import com.cloud.publishing.dto.request.EmployeeUpdateRequest;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.cloud.publishing.service.EmployeeService;

@Controller("webEmployeesController")
@RequestMapping(Urls.WEB_EMPLOYEES)
public class EmployeesController {
    private final EmployeeService service;
    private static final String EMPLOYEES = "employees";
    private static final String LIST_PAGE = "employees/employees";
    private static final String NEW_PAGE = "employees/new";
    private static final String EDIT_PAGE = "employees/edit";
    private static final String EMPLOYEE_REQUEST = "employeeRequest";
    private static final String EMPLOYEE_UPDATE_REQUEST = "employeeUpdateRequest";
    private static final String EMPLOYEE_ID = "employeeId";
    private static final String REDIRECT_EMPLOYEES = "redirect:" + Urls.WEB_EMPLOYEES;

    @Autowired
    public EmployeesController(EmployeeService service) {
        this.service = service;
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }

    @GetMapping
    public String getAll(Model model) {
        List<EmployeeResponse> employees = service.getAll();
        model.addAttribute(EMPLOYEES, employees);
        return LIST_PAGE;
    }

    @GetMapping(Urls.NEW)
    @PreAuthorize("hasRole('CHIEF_EDITOR')")
    public String showAddForm(Model model) {
        model.addAttribute(EMPLOYEE_REQUEST, EmployeeRequest.empty());
        return NEW_PAGE;
    }

    @PostMapping
    @PreAuthorize("hasRole('CHIEF_EDITOR')")
    public String add(
            @Valid @ModelAttribute(EMPLOYEE_REQUEST) EmployeeRequest request,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            return NEW_PAGE;
        }
        service.add(request);
        return REDIRECT_EMPLOYEES;
    }

    @GetMapping(Urls.ID + Urls.EDIT)
    @PreAuthorize("hasRole('CHIEF_EDITOR')")
    public String showUpdateForm(Model model, @PathVariable(Parameters.ID) int id) {
        model.addAttribute(EMPLOYEE_UPDATE_REQUEST, service.getForUpdate(id));
        model.addAttribute(EMPLOYEE_ID, id);
        return EDIT_PAGE;
    }

    @PostMapping(Urls.ID)
    @PreAuthorize("hasRole('CHIEF_EDITOR')")
    public String update(
            @Valid @ModelAttribute(EMPLOYEE_UPDATE_REQUEST) EmployeeUpdateRequest request,
            BindingResult bindingResult,
            @PathVariable(Parameters.ID) int id,
            Model model
    ) {
        if (bindingResult.hasErrors()) {
            model.addAttribute(EMPLOYEE_ID, id);
            return EDIT_PAGE;
        }
        service.update(id, request);
        return REDIRECT_EMPLOYEES;
    }

    @DeleteMapping(Urls.ID)
    @PreAuthorize("hasRole('CHIEF_EDITOR')")
    public String delete(@PathVariable(Parameters.ID) int id) {
        service.delete(id);
        return REDIRECT_EMPLOYEES;
    }
}