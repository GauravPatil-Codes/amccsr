package com.ahmedabad.csr.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ahmedabad.csr.entities.Companies;

@Repository
public interface CompaniesRepository extends JpaRepository<Companies, Integer> {

    Page<Companies> findByCategoryId(int categoryId, Pageable pageable);
    
    Page<Companies> findByCompanynameContaining(String companyName, Pageable pageable);
    
    Page<Companies> findByStatus(String status, Pageable pageable);
    
    Page<Companies> findByCompanynameContainingAndCategoryId(String companyName, int categoryId, Pageable pageable);
    
    Page<Companies> findByCompanynameContainingAndStatus(String companyName, String status, Pageable pageable);
    
    Page<Companies> findByCategoryIdAndStatus(int categoryId, String status, Pageable pageable);
    
    Page<Companies> findByCompanynameContainingAndCategoryIdAndStatus(
            String companyName, int categoryId, String status, Pageable pageable);
}