package com.cloud.publishing.backend.repository;

import com.cloud.publishing.common.reference.Category;
import java.util.List;

public interface CategoryRepository {
    List<Category> getAll();
}