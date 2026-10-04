package com.gomad.postgresql_tut.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "category")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Category {
    // Update getter and setter to use Long wrapper
    // 1. Change primitive long to wrapper Long object
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "category_seq")
    private Long id;

    @NotBlank(message = "Category name shouldn't be blank.")
    @Size(min = 5, message = "Category name should be at least of size of 5 characters.")
    private String categoryName;
}
