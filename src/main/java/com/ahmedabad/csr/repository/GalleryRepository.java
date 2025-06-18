package com.ahmedabad.csr.repository;

import com.ahmedabad.csr.entities.Gallery;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GalleryRepository extends JpaRepository<Gallery, Integer> {
    Page<Gallery> findByFileType(String fileType, Pageable pageable);
}