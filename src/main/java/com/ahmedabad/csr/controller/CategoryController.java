package com.ahmedabad.csr.controller;

import com.ahmedabad.csr.entities.Category;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.ahmedabad.csr.repository.ApiResponse;
import com.ahmedabad.csr.repository.CategoryRepository;


@RestController
public class CategoryController {

    @Autowired
    private CategoryRepository categoryRepository;

   @PostMapping("/addcategory")
public ResponseEntity<ApiResponse<Category>> addCategory(@RequestBody Category category) {
    Category savedCategory = categoryRepository.save(category);
    ApiResponse<Category> response = new ApiResponse<>(200, "Category added successfully", savedCategory);
    return new ResponseEntity<>(response, HttpStatus.CREATED);
}


    @GetMapping("/listAllCategory")
public ResponseEntity<ApiResponse<List<Category>>> listAllCategory() {
    List<Category> categories = categoryRepository.findAll();
    ApiResponse<List<Category>> response = new ApiResponse<>(200, "Category list fetched successfully", categories);
    return new ResponseEntity<>(response, HttpStatus.OK);
}

    
 @GetMapping("/categoryshowbyid/{id}")
public ResponseEntity<ApiResponse<?>> getCategoryById(@PathVariable int id) {
    Optional<Category> category = categoryRepository.findById(id);

    if (category.isPresent()) {
        ApiResponse<Category> response = new ApiResponse<>(200, "Category fetched successfully", category.get());
        return new ResponseEntity<>(response, HttpStatus.OK);
    } else {
        ApiResponse<String> response = new ApiResponse<>(404, "Category not found", null);
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
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