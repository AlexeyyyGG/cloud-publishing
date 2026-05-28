package com.cloud.publishing.backend.service;

import com.cloud.publishing.backend.repository.CategoryRepositoryImpl;
import com.cloud.publishing.model.publication.Category;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepositoryImpl categoryRepository;

    @Autowired
    public CategoryServiceImpl(CategoryRepositoryImpl categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<Category> getAll(){
        return categoryRepository.getAll();
    }

}