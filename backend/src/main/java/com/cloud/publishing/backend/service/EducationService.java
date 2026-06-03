package com.cloud.publishing.backend.service;

import com.cloud.publishing.model.employee.Education;
import java.util.List;

/**
 * Service for accessing education reference data.
 * <p>
 * Education entities are predefined and stored in the database. They are not editable.
 */
public interface EducationService {
    /**
     * Returns all available entries.
     *
     * @return list of {@link Education}
     */
    List<Education> getAll();
}