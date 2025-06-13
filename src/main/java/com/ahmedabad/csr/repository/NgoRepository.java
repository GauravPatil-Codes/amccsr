package com.ahmedabad.csr.repository;

import com.ahmedabad.csr.entities.NGO;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;



@Repository
public interface NgoRepository extends JpaRepository<NGO, Integer> {
    
 
    boolean existsByEmailIdIgnoreCase(String emailId);
   
    boolean existsByUserNameIgnoreCase(String userName);
  
}