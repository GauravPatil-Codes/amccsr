package com.ahmedabad.csr.repository;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.ahmedabad.csr.entities.Project;

public interface ProjectRepository extends JpaRepository<Project , Integer>{       
    Page<Project> findByCategoryId(int categoryId, Pageable pageable);
    Page<Project> findByNgoId(int ngoId, Pageable pageable);

}
