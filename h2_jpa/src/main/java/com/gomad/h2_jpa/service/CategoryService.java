package com.gomad.h2_jpa.service;

import com.gomad.h2_jpa.model.Category;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

public interface CategoryService {
    List<Category> getAllCategories();
    boolean createCategory(Category category);
    boolean updateCategory(Long id, Category category);
    boolean deleteCategory(Long id);
    Category getCategoryById(Long id);
}
