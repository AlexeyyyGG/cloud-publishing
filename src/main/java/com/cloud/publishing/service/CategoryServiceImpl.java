package com.cloud.publishing.service;

import com.cloud.publishing.model.Category;
import com.cloud.publishing.repository.CategoryRepositoryImpl;
import java.util.List;
import java.util.Set;
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

    @Override
    public List<Category> getByIds(Set<Integer> ids) {
        if (ids.isEmpty()) return List.of();
        return categoryRepository.getAll().stream()
                .filter(c -> ids.contains(c.id()))
                .toList();
    }
}