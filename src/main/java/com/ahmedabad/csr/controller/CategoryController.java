package com.ahmedabad.csr.controller;

import com.ahmedabad.csr.entities.Category;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import com.ahmedabad.csr.repository.CategoryRepository;

@RestController
public class CategoryController {

    @Autowired
    private CategoryRepository categoryRepository;

    @PostMapping("/addcategory")
    public Category addCategory(@RequestBody Category category) {
        return categoryRepository.save(category);
    }

    @GetMapping("/listAllCategory")
    public List<Category> listAllCategory() {
        return categoryRepository.findAll();
    }
}







// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RequestBody;
// import org.springframework.web.bind.annotation.RestController;
// import com.ahmedabad.csr.entities.Category;
// import com.ahmedabad.csr.services.CatagoryServicesImpl;

// @RestController
// public class CategoryController {
    
//     private final CatagoryServicesImpl categoryServicesImpl;
    
//     @Autowired
//     public CategoryController(CatagoryServicesImpl categoryServicesImpl) {
//         this.categoryServicesImpl = categoryServicesImpl;
//     }
    
//     // POST API to add category
//     @PostMapping("/addcategory")
//     public ResponseEntity<Category> saveCategory(@RequestBody Category category) {
//         Category savedCategory = categoryServicesImpl.saveCategory(category);
//         return ResponseEntity.ok(savedCategory);
//     }
// }