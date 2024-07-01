package com.csthesis.sarinventory_be_final.services;

import com.csthesis.sarinventory_be_final.entities.Category;
import com.csthesis.sarinventory_be_final.repositories.CategoryRepository;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {

    @Autowired
    private final CategoryRepository categoryRepo;

    public CategoryService(CategoryRepository categoryRepo) {
        this.categoryRepo = categoryRepo;
    }

    public Category saveCategory(Category category) {
        return categoryRepo.save(category);
    }

    public Category editCategory(Long categoryId, Category category) {
        Category categoryToEdit = categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category not found"));
        categoryToEdit.setName(category.getName());

        categoryRepo.save(categoryToEdit);
        return category;
    }

    public Category getCategoryById(Long id){
        return categoryRepo.findById(id).orElse(null);
    }

    public HttpStatus deleteCategory(Long categoryId) {
        categoryRepo.deleteById(categoryId);
        return HttpStatus.OK;
    }
}
