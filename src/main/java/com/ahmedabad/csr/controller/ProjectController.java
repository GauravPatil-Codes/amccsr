package com.ahmedabad.csr.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ahmedabad.csr.entities.Project;
import com.ahmedabad.csr.repository.ApiResponse;
import com.ahmedabad.csr.repository.ProjectRepository;
import com.ahmedabad.csr.services.ProjectServices;

@RestController
public class ProjectController {
  @Autowired
  private ProjectServices projectService;

  @Autowired
  private ProjectRepository projectRepository;

  @PostMapping("/addProject")
  public ResponseEntity<Project> addProject(@RequestBody Project project) {
    return ResponseEntity.ok(projectService.addProject(project));
  }

  @PutMapping("/updateProjetc/{projetcId}")
  public ResponseEntity<Project> updateProject(@PathVariable int projetcId, @RequestBody Project project) {
    return ResponseEntity.ok(projectService.updateProject(projetcId, project));
  }

  @DeleteMapping("/deleteProject/{projetcId}")
  public ResponseEntity<ApiResponse<String>> deleteProject(@PathVariable int projetcId) {
    try {
      projectService.deleteProject(projetcId);
      ApiResponse<String> response = new ApiResponse<>(200, "project deleted successfully", null);
      return new ResponseEntity<>(response, HttpStatus.OK);
    } catch (RuntimeException e) {
      ApiResponse<String> response = new ApiResponse<>(404, e.getMessage(), null);
      return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
  }

   @GetMapping("/projectshowbyid/{letestupdateid}")
    public ResponseEntity<ApiResponse<?>> getProjectById(@PathVariable int projetcId) {
        Optional<Project> project = projectRepository.findById(projetcId);

        if (project.isPresent()) {
            ApiResponse<Project> response = new ApiResponse<>(200, "project fetched successfully",
                    project.get());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            ApiResponse<String> response = new ApiResponse<>(404, "letest Update not found", null);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

}
