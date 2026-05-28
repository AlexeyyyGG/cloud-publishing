package com.cloud.publishing.backend.repository;


import com.cloud.publishing.model.publication.Category;
import java.util.List;

public interface CategoryRepository {
    List<Category> getAll();
}