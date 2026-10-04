package com.gomad.postgresql_tut.service;

import com.gomad.postgresql_tut.payload.CategoryDTO;
import com.gomad.postgresql_tut.payload.CategoryResponse;

public interface CategoryService {
    CategoryResponse getAllCategories();

    CategoryResponse getAllCategoriesPagination(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);

    CategoryDTO createCategory(CategoryDTO categoryDTO);

    CategoryDTO updateCategory(Long id, CategoryDTO categoryDTO);

    CategoryDTO deleteCategory(Long id);

    CategoryDTO getCategoryById(Long id);
}
