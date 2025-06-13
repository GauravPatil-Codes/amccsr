package com.ahmedabad.csr.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.ahmedabad.csr.entities.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {
    
}