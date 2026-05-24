package com.cloud.publishing.backend.service;

import com.cloud.publishing.common.reference.Category;
import java.util.List;

/**
 * Service for accessing category reference data.
 * <p>
 * com.cloud.publishing.common.reference.Category entities are predefined and stored in the database. They are not editable.
 */
public interface CategoryService {
    /**
     * Returns all available entries.
     *
     * @return list of {@link Category}
     */
    List<Category> getAll();
}