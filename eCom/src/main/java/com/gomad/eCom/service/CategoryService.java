package com.gomad.eCom.service;

import com.gomad.eCom.model.Category;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;

public interface CategoryService {
    List<Category> getAllCategories();
    boolean createCategory(Category category);
}
