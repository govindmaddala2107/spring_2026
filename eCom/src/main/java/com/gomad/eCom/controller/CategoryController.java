package com.gomad.eCom.controller;

import com.gomad.eCom.model.Category;
import com.gomad.eCom.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/all")
    public List<Category> getCategories(){
        return categoryService.getAllCategories();
    }

    @PostMapping("/add")
    public boolean addCategory(@RequestBody Category category){
        return categoryService.createCategory(category);
    }
}
