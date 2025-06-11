package com.ahmedabad.csr.services;

import java.util.List;

import com.ahmedabad.csr.entities.Category;

public interface Categoryservices {
 Category saveCategory(Category category);
 List<Category> getAllCategory();
}
