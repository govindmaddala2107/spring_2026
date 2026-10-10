package com.gomad.postgresql_tut.repository;

import com.gomad.postgresql_tut.model.Category;
import com.gomad.postgresql_tut.payload.CategoryDTO;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    CategoryDTO findByCategoryName(String categoryName);
}
