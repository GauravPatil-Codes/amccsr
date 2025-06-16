package com.ahmedabad.csr.services;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.ahmedabad.csr.entities.Project;

public interface ProjectServices {
    Project addProject(Project project);

    Project updateProject(int id, Project project);

    void deleteProject(int projetcId);

    Optional<Project> getProjectById(int projetcId);

    List<Project> ListAllProject();

    Page<Project> getprojetcByCategoryId(int categoryId, Pageable pageable);

    Page<Project> getprojetcByNgoId(int ngoId, Pageable pageable);

    Page<Project> getProjectByProjectBudget(String projectBudget, Pageable pageable);

    Page<Project> getProjectByProjectStatus(String projectStatus, Pageable pageable);
}
