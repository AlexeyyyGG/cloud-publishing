package com.cloud.publishing.controller.web;

import com.cloud.publishing.constants.Parameters;
import com.cloud.publishing.constants.Urls;
import com.cloud.publishing.constants.employee.EmployeeModelAttrs;
import com.cloud.publishing.constants.employee.EmployeePages;
import com.cloud.publishing.dto.request.EmployeeRequest;
import com.cloud.publishing.dto.response.EmployeeResponse;
import com.cloud.publishing.dto.request.EmployeeUpdateRequest;
import com.cloud.publishing.mapper.EmployeeMapper;
import com.cloud.publishing.model.Employee;
import com.cloud.publishing.model.Gender;
import com.cloud.publishing.model.Type;
import com.cloud.publishing.service.EducationService;
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
    private final EmployeeService employeeService;
    private final EducationService educationService;
    private final EmployeeMapper mapper;
    private static final int MIN_BIRTH_YEAR = 1960;
    private static final int MAX_BIRTH_YEAR = 2008;

    @Autowired
    public EmployeesController(
            EmployeeService employeeService,
            EducationService educationService,
            EmployeeMapper mapper
    ) {
        this.employeeService = employeeService;
        this.educationService = educationService;
        this.mapper = mapper;
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }

    @ModelAttribute
    public void prepareFormData(Model model) {
        model.addAttribute(EmployeeModelAttrs.GENDERS, Gender.values());
        model.addAttribute(EmployeeModelAttrs.TYPES, Type.values());
        model.addAttribute(EmployeeModelAttrs.EDUCATIONS, educationService.getAll());
        model.addAttribute(EmployeeModelAttrs.MIN_BIRTH_YEAR, MIN_BIRTH_YEAR);
        model.addAttribute(EmployeeModelAttrs.MAX_BIRTH_YEAR, MAX_BIRTH_YEAR);
    }

    @GetMapping
    public String getAll(Model model) {
        List<EmployeeResponse> employees = employeeService.getAll().stream()
                .map(mapper::toResponse)
                .toList();
        model.addAttribute(EmployeeModelAttrs.EMPLOYEES, employees);
        return EmployeePages.LIST;
    }

    @GetMapping(Urls.NEW)
    @PreAuthorize("hasRole('CHIEF_EDITOR')")
    public String showAddForm(Model model) {
        model.addAttribute(EmployeeModelAttrs.EMPLOYEE_REQUEST, EmployeeRequest.empty());
        return EmployeePages.NEW;
    }

    @PostMapping
    @PreAuthorize("hasRole('CHIEF_EDITOR')")
    public String add(
            @Valid @ModelAttribute(EmployeeModelAttrs.EMPLOYEE_REQUEST) EmployeeRequest request,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            return EmployeePages.NEW;
        }
        employeeService.add(request);
        return EmployeePages.REDIRECT_EMPLOYEES;
    }

    @GetMapping(Urls.ID + Urls.EDIT)
    @PreAuthorize("hasRole('CHIEF_EDITOR')")
    public String showUpdateForm(Model model, @PathVariable(Parameters.ID) int id) {
        Employee employee = employeeService.getForUpdate(id);
        model.addAttribute(
                EmployeeModelAttrs.EMPLOYEE_UPDATE_REQUEST,
                mapper.toUpdateRequest(employee)
        );
        model.addAttribute(EmployeeModelAttrs.EMPLOYEE_ID, id);
        return EmployeePages.EDIT;
    }

    @PostMapping(Urls.ID)
    @PreAuthorize("hasRole('CHIEF_EDITOR')")
    public String update(
            @Valid @ModelAttribute(EmployeeModelAttrs.EMPLOYEE_UPDATE_REQUEST)
            EmployeeUpdateRequest request,
            BindingResult bindingResult,
            @PathVariable(Parameters.ID) int id,
            Model model
    ) {
        if (bindingResult.hasErrors()) {
            model.addAttribute(EmployeeModelAttrs.EMPLOYEE_ID, id);
            return EmployeePages.EDIT;
        }
        employeeService.update(id, request);
        return EmployeePages.REDIRECT_EMPLOYEES;
    }

    @DeleteMapping(Urls.ID)
    @PreAuthorize("hasRole('CHIEF_EDITOR')")
    public String delete(@PathVariable(Parameters.ID) int id) {
        employeeService.delete(id);
        return EmployeePages.REDIRECT_EMPLOYEES;
    }
}