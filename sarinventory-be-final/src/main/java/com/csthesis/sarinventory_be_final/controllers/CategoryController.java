package com.csthesis.sarinventory_be_final.controllers;

import com.csthesis.sarinventory_be_final.entities.Category;
import com.csthesis.sarinventory_be_final.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {
    @Autowired
    public CategoryService categoryService;

    @PostMapping
    public Category createCategory (@RequestBody Category category) {
        return categoryService.saveCategory(category);
    }

    @GetMapping
    @RequestMapping("/{id}")
    public Category getCategoryById(@PathVariable Long id) {
        return categoryService.getCategoryById(id);
    }
}
