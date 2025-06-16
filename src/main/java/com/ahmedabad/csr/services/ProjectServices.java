package com.ahmedabad.csr.services;

import java.util.List;
import java.util.Optional;

import com.ahmedabad.csr.entities.Project;

public interface ProjectServices {
    Project addProject(Project project);

    Project updateProject(int id, Project project);

    void deleteProject(int projetcId);

    Optional<Project> getProjectById(int projetcId);

   List<Project> ListAllProject();
}
