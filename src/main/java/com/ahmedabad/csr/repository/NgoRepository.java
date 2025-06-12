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
    
    // Search by organization name (minimum 3 characters) with pagination
    @Query("SELECT n FROM NGO n WHERE LENGTH(:name) >= 3 AND LOWER(n.organizationName) LIKE LOWER(CONCAT('%', :name, '%'))")
    Page<NGO> findByOrganizationNameContainingIgnoreCase(@Param("name") String name, Pageable pageable);
    
    // Search by organization name and status with pagination
    @Query("SELECT n FROM NGO n WHERE LENGTH(:name) >= 3 AND LOWER(n.organizationName) LIKE LOWER(CONCAT('%', :name, '%')) AND LOWER(n.status) = LOWER(:status)")
    Page<NGO> findByOrganizationNameContainingIgnoreCaseAndStatus(@Param("name") String name, @Param("status") String status, Pageable pageable);
    
    // Find by email - CORRECT field name: emailId
    Optional<NGO> findByEmailIdIgnoreCase(String emailId);
    
    // Find by username - CORRECT field name: userName (NOT ngousername)
    Optional<NGO> findByUserNameIgnoreCase(String userName);
    
    // Check if email exists - CORRECT field name: emailId
    boolean existsByEmailIdIgnoreCase(String emailId);
    
    // Check if username exists - CORRECT field name: userName (NOT ngousername)
    boolean existsByUserNameIgnoreCase(String userName);
    
    // Get all distinct statuses
    @Query("SELECT DISTINCT n.status FROM NGO n WHERE n.status IS NOT NULL ORDER BY n.status")
    List<String> findAllDistinctStatuses();
    
    // Count by status
    long countByStatusIgnoreCase(String status);

//    boolean existsByEmailIdIgnoreCase(String emailId);
}