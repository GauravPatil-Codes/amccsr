package com.ahmedabad.csr.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.ahmedabad.csr.entities.Project;
import com.ahmedabad.csr.repository.ProjectRepository;
import org.springframework.data.domain.Pageable;

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
        existing.setProjectName(project.getProjectName());
        existing.setProjectDescription(project.getProjectDescription());
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

    @Override
    public void deleteProject(int projetcId) {
        Project projetc = projectRepository.findById(projetcId)
                .orElseThrow(() -> new RuntimeException("Latest Update not found"));
        projectRepository.delete(projetc);
    }

    // showbyid
    @Override
    public Optional<Project> getProjectById(int projetcId) {
        return Optional.of(projectRepository.findById(projetcId).orElse(null));
    }

    // listALL
    @Override
    public List<Project> ListAllProject() {
        return projectRepository.findAll();
    }

    @Override
    public Page<Project> getprojetcByCategoryId(int categoryId, Pageable pageable) {
        return projectRepository.findByCategoryId(categoryId, pageable);
    }

      @Override
    public Page<Project> getprojetcByNgoId(int ngoId, Pageable pageable) {
        return projectRepository.findByNgoId(ngoId, pageable);
    }
 @Override
   public Page<Project> getProjectByProjectBudget(String projectBudget, Pageable pageable) {
    return projectRepository.findByProjectBudget(projectBudget, pageable);
}


    @Override
    public Page<Project> getprojetcByCompanyId(int companieId, Pageable pageable) {
        return projectRepository.findBycompanieId(companieId, pageable);
    }
@Override
   public Page<Project> getProjectByProjectStatus(String projectStatus, Pageable pageable) {
    return projectRepository.findByProjectStatus(projectStatus, pageable);
}

@Override
public Page<Project> findByprojectDEpartmentName(String projectDEpartmentName, Pageable pageable){
    return projectRepository.findByprojectDEpartmentName(projectDEpartmentName, pageable);
};
}
