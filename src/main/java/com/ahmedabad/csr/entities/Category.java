package com.ahmedabad.csr.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "category")
public class Category {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    
    private String categoryName;
    
    // Default constructor (required by JPA)
    public Category() {}
    
    // Constructor with parameters
    public Category(int id, String categoryName) {
        this.id = id;
        this.categoryName = categoryName;
    }
    
    // Constructor without id (for new entities)
    public Category(String categoryName) {
        this.categoryName = categoryName;
    }
    
    // Getters and Setters
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
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
        return "Category [id=" + id + ", categoryName=" + categoryName + "]";
    }
}