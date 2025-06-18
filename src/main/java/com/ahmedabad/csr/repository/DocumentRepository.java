package com.ahmedabad.csr.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.ahmedabad.csr.entities.Documents;

public interface DocumentRepository extends JpaRepository<Documents, Integer>{

    Page<Documents> findBydocumentType(String documentType, Pageable pageable);

}
