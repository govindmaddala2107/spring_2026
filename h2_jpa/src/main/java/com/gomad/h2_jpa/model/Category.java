package com.gomad.h2_jpa.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name = "categories")
@Data
public class Category {
    // Update getter and setter to use Long wrapper
    // 1. Change primitive long to wrapper Long object
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "category_seq")
    private Long id;

    @NotBlank
    private String categoryName;
}
