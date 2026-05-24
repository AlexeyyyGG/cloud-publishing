package com.cloud.publishing.backend.controller;

import com.cloud.publishing.backend.service.CategoryService;
import com.cloud.publishing.common.reference.Category;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/categories")
public class CategoriesController {
    private final CategoryService categoryService;

    @Autowired
    public CategoriesController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public List<Category> getAll() {
        return categoryService.getAll();
    }
}
