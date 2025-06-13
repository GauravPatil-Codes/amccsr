package com.ahmedabad.csr.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.ahmedabad.csr.entities.LetestUpdate;
import com.ahmedabad.csr.entities.Project;
import com.ahmedabad.csr.repository.ProjectRepository;

@Service
public class ProjectServicesImpl implements ProjectServices {
    @Autowired
    private ProjectRepository projectRepository;

    @Override
    public Project addProject(Project project) {
        return projectRepository.save(project);
    }

    @Override
    public Project updateProject(int id, Project project) {
        Project existing = projectRepository.findById(id).orElseThrow(() -> new RuntimeException("Project not found"));
        // Update fields
        existing.setProjetcName(project.getProjetcName());
        existing.setProjetcDescription(project.getProjetcDescription());
        existing.setProjectStatus(project.getProjectStatus());
        existing.setNgoId(project.getNgoId());
        existing.setCategoryId(project.getCategoryId());
     existing.setProjectImages(project.getProjectImages());
     existing.setProjectMainImage(project.getProjectMainImage());
        existing.setProjectBudget(project.getProjectBudget());
        existing.setProjectLocation(project.getProjectLocation());
        existing.setImpactpeople(project.getImpactpeople());
        existing.setProjectShortDescription(project.getProjectShortDescription());
        existing.setProjectDEpartmentName(project.getProjectDEpartmentName());
        // existing.setProjectDocuments(project.getProjectDocuments());

        return projectRepository.save(existing);
    }

    // @Override
    // public void deleteProject(int projetcId) {
    //     projectRepository.deleteById(projetcId);
    // }

 @Override
    public void deleteProject(int projetcId) {
        Project projetc = projectRepository.findById(projetcId)
                .orElseThrow(() -> new RuntimeException("Latest Update not found"));
        projectRepository.delete(projetc);
    }

 @Override
 public Optional<Project> getProjectById(int projetcId) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'getProjectById'");
 }


    // @Override
    // public Page<Project> listAllProjects(Pageable pageable) {
    // return projectRepository.findAll(pageable);
    // }

}
  