package com.gomad.h2_jpa.controller;

import com.gomad.h2_jpa.payload.CategoryDTO;
import com.gomad.h2_jpa.payload.CategoryResponse;
import com.gomad.h2_jpa.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public ResponseEntity<CategoryResponse> getCategories(){
        return ResponseEntity.ok().body(categoryService.getAllCategories());
    }

    @GetMapping
    public ResponseEntity<CategoryResponse> getAllCategoriesPagination(@RequestParam Integer pageNumber, Integer pageSize){
        return ResponseEntity.ok().body(categoryService.getAllCategoriesPagination(pageNumber, pageSize));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoryDTO> getCategoryById(@PathVariable Long id){
        CategoryDTO category = categoryService.getCategoryById(id);
        return new ResponseEntity<>(category, HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<CategoryDTO> addCategory(@Valid @RequestBody CategoryDTO categoryDTO) {
        CategoryDTO savedCategoryDTO = categoryService.createCategory(categoryDTO);
        return new ResponseEntity<>(savedCategoryDTO, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoryDTO> updateCategory(@PathVariable Long id, @RequestBody CategoryDTO categoryDTO) {
        CategoryDTO updatedCategoryDTO = categoryService.updateCategory(id, categoryDTO);
        return new ResponseEntity<>(updatedCategoryDTO, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CategoryDTO> deleteCategory(@PathVariable Long id) {
        CategoryDTO deletedCategoryDTO = categoryService.deleteCategory(id);
        return new ResponseEntity<>(deletedCategoryDTO, HttpStatus.OK);
    }

    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public ResponseEntity<String> hello(@RequestParam(value = "message", defaultValue = "World", required = true) String message){
        return new ResponseEntity<>(String.format("Hello %s", message), HttpStatus.OK);
    }
}
