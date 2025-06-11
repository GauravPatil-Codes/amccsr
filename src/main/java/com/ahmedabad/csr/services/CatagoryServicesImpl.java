package com.ahmedabad.csr.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ahmedabad.csr.entities.Category;
import com.ahmedabad.csr.repository.CategoryRepository;

@Service
public class CatagoryServicesImpl implements Categoryservices {
    private CategoryRepository categoryRepository;

    @Autowired
    public void CategoryServicesImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    // create
    @Override
    public Category saveCategory(Category category) {
        return categoryRepository.save(category);
    }

    // read
    @Override
    public List<Category> getAllCategory() {
        return (List<Category>) categoryRepository.findAll();
    }

    // showbyid
    @Override
    public Category getCategoryById(int id) {
        return categoryRepository.findById(id).orElse(null);
    }

    @Override
    public Category updateCategory(int id, Category updatedCategory) {
        Category existingCategory = getCategoryById(id);
        existingCategory.setCategoryName(updatedCategory.getCategoryName());
        return categoryRepository.save(existingCategory);
    }

    @Override
    public void deleteCategoryById(int id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));

        categoryRepository.delete(category);
    }

}
