package com.ahmedabad.csr.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ahmedabad.csr.entities.Publication;

public interface PublicationRepository extends JpaRepository<Publication ,Integer> {

}
