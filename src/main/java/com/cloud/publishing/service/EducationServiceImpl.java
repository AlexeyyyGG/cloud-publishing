package com.cloud.publishing.service;

import com.cloud.publishing.model.Education;
import com.cloud.publishing.repository.EducationRepository;
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