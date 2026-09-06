package com.gomad.h2_jpa.implementation;

import com.gomad.h2_jpa.model.Category;
import com.gomad.h2_jpa.service.CategoryService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImplementation implements CategoryService {
    private final List<Category> categories = new ArrayList<>();
    private long nextId = 1L;

    @Override
    public List<Category> getAllCategories() {
        return categories;
    }

    @Override
    public boolean createCategory(Category category) {
        category.setId(nextId++);
        return categories.add(category);
    }

    @Override
    public boolean updateCategory(Long id, Category category) {
        Category cat = categories.stream()
                .filter(c -> c.getId().equals(id))
                .findFirst()
                .orElse(null);
        if (cat == null){
              return false;
        }

        cat.setCategoryName(category.getCategoryName());
        return true;
    }

    @Override
    public boolean deleteCategory(Long id) {
        Category category = categories.stream()
                .filter(c -> c.getId().equals(id))
                .findFirst()
                .orElse(null);
        if(category == null){
            return false;
        }
        return categories.remove(category);
    }

    @Override
    public Category getCategoryById(Long id) {
//        return categories.stream()
//                .filter(c -> c.getId().equals(id))
//                .findFirst()
//                .orElse(null);
//                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Record not found"));

        Optional<Category> optionalCategory = categories.stream()
                .filter(c -> c.getId().equals(id))
                .findFirst();
        return optionalCategory.orElse(null);
//        if(optionalCategory.isPresent()){
//            return optionalCategory.get();
//        } else{
//            return null;
//        }
    }
}
