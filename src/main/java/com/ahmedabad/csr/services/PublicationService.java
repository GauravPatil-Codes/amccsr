package com.ahmedabad.csr.services;

import com.ahmedabad.csr.entities.Publication;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface PublicationService {
    Publication save(Publication publication);
    Optional<Publication> findById(int id);
    Page<Publication> findAll(Pageable pageable);
    void deleteById(int id);
    Publication update(int id, Publication updatedPublication);
}