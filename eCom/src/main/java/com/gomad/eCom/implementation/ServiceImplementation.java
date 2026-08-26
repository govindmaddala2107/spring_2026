package com.gomad.eCom.implementation;

import com.gomad.eCom.model.Category;
import com.gomad.eCom.service.CategoryService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ServiceImplementation implements CategoryService {
    private List<Category> categories = new ArrayList<>();

    @Override
    public List<Category> getAllCategories() {
        return categories;
    }

    @Override
    public boolean createCategory(Category category) {
        return categories.add(category);
    }
}
