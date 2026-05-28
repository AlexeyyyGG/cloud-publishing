package com.cloud.publishing.backend.service;

import com.cloud.publishing.backend.repository.EducationRepository;
import com.cloud.publishing.model.employee.Education;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EducationServiceImpl implements EducationService {
    private final EducationRepository repository;

    @Autowired
    public EducationServiceImpl(EducationRepository repository) {
        this.repository = repository;
    }

    public List<Education> getAll() {
        return repository.getAll();
    }
}