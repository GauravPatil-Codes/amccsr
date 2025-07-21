package com.ahmedabad.csr.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.ahmedabad.csr.entities.Project;

public interface ProjectRepository extends JpaRepository<Project, Integer> {
    Page<Project> findByCategoryId(int categoryId, Pageable pageable);

    Page<Project> findByNgoId(int ngoId, Pageable pageable);

    Page<Project> findByProjectBudget(String projectBudget, Pageable pageable);

    Page<Project> getProjectByProjectBudget(String projectBudget, Pageable pageable);

    Page<Project> findByProjectStatus(String projectStatus, Pageable pageable);
    Page<Project> findByprojectDEpartmentName(String projectDEpartmentName, Pageable pageable);

    @Query("SELECT p FROM Project p " +
            "WHERE (:ngoId IS NULL OR p.ngoId = :ngoId) " +
            "AND (:categoryId IS NULL OR p.categoryId = :categoryId) " +
            "AND (:projectBudget IS NULL OR p.projectBudget = :projectBudget) " +
            "AND (:status IS NULL OR p.projectStatus = :status)")
    Page<Project> filterProjects(@Param("ngoId") Integer ngoId,
            @Param("categoryId") Integer categoryId,
            @Param("projectBudget") String projectBudget,
            @Param("status") String status,
            Pageable pageable);

    Page<Project> findBycompanieId(int companieId, Pageable pageable);

}
