package com.ahmedabad.csr.repository;

import com.ahmedabad.csr.entities.NGO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NgoRepository extends JpaRepository<NGO, Integer> {
    
    // Find all NGOs by status with pagination
    Page<NGO> findByStatusIgnoreCase(String status, Pageable pageable);
    
    // Find all NGOs by status without pagination
    List<NGO> findByStatusIgnoreCase(String status);
 
    // Find by email - CORRECT field name: emailId
    Optional<NGO> findByEmailIdIgnoreCase(String emailId);
    
    // Find by username - CORRECT field name: userName (NOT ngousername)
    Optional<NGO> findByUserNameIgnoreCase(String userName);
    
    // Check if email exists - CORRECT field name: emailId
    boolean existsByEmailIdIgnoreCase(String emailId);
    
    // Check if username exists - CORRECT field name: userName (NOT ngousername)
    boolean existsByUserNameIgnoreCase(String userName);
  
}