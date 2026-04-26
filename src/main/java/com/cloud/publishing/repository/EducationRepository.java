package com.cloud.publishing.repository;

import com.cloud.publishing.model.Education;
import java.util.List;

public interface EducationRepository {
    Education get(int id);
    List<Education> getAll();
}