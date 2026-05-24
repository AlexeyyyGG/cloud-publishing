package com.cloud.publishing.backend.repository;

import com.cloud.publishing.common.reference.Education;
import java.util.List;

public interface EducationRepository {
    Education get(int id);
    List<Education> getAll();
}