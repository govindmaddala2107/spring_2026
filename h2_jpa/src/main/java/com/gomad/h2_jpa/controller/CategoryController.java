package com.gomad.h2_jpa.controller;

import com.gomad.h2_jpa.model.Category;
import com.gomad.h2_jpa.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

//    @GetMapping("/all")
    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public ResponseEntity<List<Category>> getCategories(){
//        return categoryService.getAllCategories();
//        return new ResponseEntity<>(categoryService.getAllCategories(), HttpStatus.OK);
        return ResponseEntity.ok().body(categoryService.getAllCategories());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Category> getCategoryById(@PathVariable Long id){
        Category category = categoryService.getCategoryById(id);

        if(category == null){
            return ResponseEntity.notFound().build();
        }else{
            return new ResponseEntity<>(category, HttpStatus.OK);
        }
    }

    @PostMapping("/add")
    public ResponseEntity<String> addCategory(@Valid @RequestBody Category category) {
        boolean isSaved = categoryService.createCategory(category);

        if (isSaved) {
            // Returns a crisp 201 Created status along with a clean string message
            return new ResponseEntity<>("Category added successfully!", HttpStatus.CREATED);
        } else {
            // Returns a real 400 Bad Request status if execution fails internally
            return new ResponseEntity<>("Failed to create category", HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateCategory(@PathVariable Long id, @RequestBody Category category) {
        boolean isUpdated = categoryService.updateCategory(id, category);

        if (isUpdated) {
            // Returns a crisp 201 Created status along with a clean string message
            return new ResponseEntity<>("Category updated successfully!", HttpStatus.OK);
        } else {
            // Returns a real 400 Bad Request status if execution fails internally
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Category with ID " + id + " not found.");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable Long id) {
        boolean isDeleted = categoryService.deleteCategory(id);

        if (isDeleted) {
            // Returns a crisp 201 Created status along with a clean string message
            return new ResponseEntity<>("Category deleted successfully!", HttpStatus.OK);
        } else {
            // Returns a real 400 Bad Request status if execution fails internally
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Category with ID " + id + " not found.");
        }
    }
}
