package com.cloud.publishing.service;

import com.cloud.publishing.model.Category;
import java.util.List;
import java.util.Set;

/**
 * Service for accessing category reference data.
 * <p>
 * Category entities are predefined and stored in the database. They are not editable.
 */
public interface CategoryService {
    /**
     * Returns all available entries.
     *
     * @return list of {@link Category}
     */
    List<Category> getAll();

    List<Category> getByIds(Set<Integer> ids);
}