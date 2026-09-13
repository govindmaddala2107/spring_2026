package com.gomad.h2_jpa.implementation;

import com.gomad.h2_jpa.model.Category;
import com.gomad.h2_jpa.repository.CategoryRepository;
import com.gomad.h2_jpa.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImplementation implements CategoryService {
//    private final List<Category> categories = new ArrayList<>();
//    private long nextId = 1L;

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public boolean createCategory(Category category) {
        categoryRepository.save(category);
        return true;
    }

    @Override
    public boolean updateCategory(Long id, Category category) {
        if (!categoryRepository.existsById(id)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Category not found");
        }

        category.setId(id);
        categoryRepository.save(category);
        return true;
    }

    @Override
    public boolean deleteCategory(Long id) {
        Category categoryToDelete = categoryRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Category not found"));

        categoryRepository.delete(categoryToDelete);
        return true;
    }

    @Override
    public Category getCategoryById(Long id) {
        return Optional.of(categoryRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found"))).get();
    }
}
