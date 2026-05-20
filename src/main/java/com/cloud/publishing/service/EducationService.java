package com.cloud.publishing.service;

import com.cloud.publishing.model.Education;
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