package com.cloud.publishing.repository;

import com.cloud.publishing.model.Category;
import java.util.List;

public interface CategoryRepository {
    List<Category> getAll();
}