package com.gomad.h2_jpa.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity(name = "categories")
public class Category {
    // 1. Change primitive long to wrapper Long object
    @Id
    private Long id;
    private String categoryName;

    // 2. REQUIRED: Default no-argument constructor for Jackson deserialisation
    public Category() {
    }

    public Category(String categoryName, Long id) {
        this.categoryName = categoryName;
        this.id = id;
    }

    // Update getter and setter to use Long wrapper
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", categoryName='" + categoryName + '\'' +
                '}';
    }
}
