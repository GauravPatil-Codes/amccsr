package com.ahmedabad.csr.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ahmedabad.csr.entities.NGO;
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

  @PutMapping("/updateProject/{projetcId}")
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

  @GetMapping("/projectshowbyid/{projetcId}")
  public ResponseEntity<Map<String, Object>> getProjectById(@PathVariable int projetcId) {
    Optional<Project> project = projectRepository.findById(projetcId);

    if (project.isPresent()) {
      Map<String, Object> response = new HashMap<>();
      response.put("status", 200);
      response.put("message", "Project found successfully");
      response.put("data", project.get());
      return ResponseEntity.ok(response);
    } else {
      Map<String, Object> response = new HashMap<>();
      response.put("status", 404);
      response.put("message", "Project not found");
      return ResponseEntity.status(404).body(response);
    }
  }


  @GetMapping("/listallProjects")
  public ResponseEntity<ApiResponse<Page<Project>>> listNGOs(
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "5") int size) {
    Pageable pageable = PageRequest.of(page, size, Sort.by("projetcId").descending());
    Page<Project> project = projectRepository.findAll(pageable);

    return ResponseEntity.ok(new ApiResponse<>(200, "projects fetched successfully", project));
  }
}
