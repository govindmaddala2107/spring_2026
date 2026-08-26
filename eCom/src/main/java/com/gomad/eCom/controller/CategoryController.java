package com.gomad.eCom.controller;

import com.gomad.eCom.model.Category;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    private List<Category> categories = new ArrayList<>();

    @GetMapping("/all")
    public List<Category> getCategories(){
        return categories;
    }

    @PostMapping("/add")
    public List<Category> addCategory(@RequestBody Category category){
        categories.add(category);
        return categories;
    }
}
