package com.cloud.publishing.backend.controller;

import com.cloud.publishing.common.constants.Urls;
import com.cloud.publishing.backend.service.EducationService;
import com.cloud.publishing.model.employee.Education;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(Urls.EDUCATIONS)
public class EducationsController {
    private final EducationService service;

    @Autowired
    public EducationsController(EducationService service) {
        this.service = service;
    }

    @GetMapping
    public List<Education> getAll() {
        return service.getAll();
    }
}
