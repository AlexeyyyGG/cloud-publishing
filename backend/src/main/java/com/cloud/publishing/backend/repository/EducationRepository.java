package com.cloud.publishing.backend.repository;

import com.cloud.publishing.model.employee.Education;
import java.util.List;

public interface EducationRepository {
    Education get(int id);
    List<Education> getAll();
}