package com.ahmedabad.csr.controller;

import java.util.HashMap;
import java.util.List;
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

import com.ahmedabad.csr.entities.Category;
import com.ahmedabad.csr.entities.NGO;
import com.ahmedabad.csr.entities.Project;
import com.ahmedabad.csr.repository.ApiResponse;
import com.ahmedabad.csr.repository.CategoryRepository;
import com.ahmedabad.csr.repository.NgoRepository;
import com.ahmedabad.csr.repository.ProjectRepository;
import com.ahmedabad.csr.services.ProjectServices;

@RestController
public class ProjectController {
  @Autowired
  private ProjectServices projectService;

  @Autowired
  private ProjectRepository projectRepository;

  @Autowired
  private CategoryRepository categoryRepository;

  @Autowired
  private NgoRepository ngoRepository;

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
  public ResponseEntity<ApiResponse<Page<Map<String, Object>>>> listNGOs(
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "5") int size) {

    Pageable pageable = PageRequest.of(page, size, Sort.by("projetcId").descending());
    Page<Project> projectPage = projectRepository.findAll(pageable);
    Page<Map<String, Object>> mapPage = projectPage.map(project -> {
      Map<String, Object> map = new HashMap<>();

      // Add project fields
      map.put("projectId", project.getProjectId());
      map.put("projectName", project.getProjectName());
      map.put("projectStatus", project.getProjectStatus());
      map.put("ngoId", project.getNgoId());
      map.put("categoryId", project.getCategoryId());
      map.put("projectMainImage", project.getProjectMainImage());
      map.put("projectBudget", project.getProjectBudget());
      map.put("projectLocation", project.getProjectLocation());
      map.put("impactpeople", project.getImpactpeople());
      map.put("projectShortDescription", project.getProjectShortDescription());
      map.put("projectDEpartmentName", project.getProjectDEpartmentName());
      map.put("projectImages", project.getProjectImages());
      map.put("projectDescription", project.getProjectDescription());

      // Fetch category name
      Optional<Category> categoryOpt = categoryRepository.findById(project.getCategoryId());
      map.put("categoryName", categoryOpt.map(Category::getCategoryName).orElse("Category not found"));

      // Fetch NGO info
      Optional<NGO> ngoOpt = ngoRepository.findById(project.getNgoId());
      if (ngoOpt.isPresent()) {
        NGO ngo = ngoOpt.get();
        Map<String, Object> ngoMap = new HashMap<>();
        ngoMap.put("id", ngo.getId());
        ngoMap.put("ngoname", ngo.getOrganizationName());
        ngoMap.put("ngoemailid", ngo.getEmailId());
        ngoMap.put("ngousername", ngo.getUserName());
        ngoMap.put("ngoPassword", ngo.getPassword());
        ngoMap.put("ageoforganization", ngo.getAgeOfOrganization());
        ngoMap.put("annualturnover", ngo.getAnnualTurnover());
        ngoMap.put("nameofcontactprson", ngo.getNameOfContactPerson());
        ngoMap.put("numberofcontactprson", ngo.getContactNumber());
        map.put("ngoInfo", ngoMap);
      } else {
        map.put("ngoInfo", "NGO not found");
      }

      return map;
    });

    return ResponseEntity.ok(new ApiResponse<>(200, "projects fetched successfully", mapPage));
  }

  // projectby category id
  @GetMapping("/projectshowbycategoryid/{categoryId}")
  public ResponseEntity<Map<String, Object>> getProjectByCategoryId(
      @PathVariable int categoryId,
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "10") int size) {

    Pageable pageable = PageRequest.of(page, size);
    Page<Project> projectPage = projectRepository.findByCategoryId(categoryId, pageable);

    Map<String, Object> response = new HashMap<>();
    if (projectPage.hasContent()) {
      response.put("status", 200);
      response.put("message", "Projects found by category ID successfully");
      response.put("data", projectPage.getContent());
      response.put("currentPage", projectPage.getNumber());
      response.put("totalItems", projectPage.getTotalElements());
      response.put("totalPages", projectPage.getTotalPages());
      return ResponseEntity.ok(response);
    } else {
      response.put("status", 404);
      response.put("message", "No projects found for this category ID");
      return ResponseEntity.status(404).body(response);
    }
  }

  // projectby category id
  @GetMapping("/projectshowbyngoid/{ngoId}")
  public ResponseEntity<Map<String, Object>> getProjectByNgoId(
      @PathVariable int ngoId,
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "10") int size) {

    Pageable pageable = PageRequest.of(page, size);
    Page<Project> projectPage = projectRepository.findByNgoId(ngoId, pageable);

    Map<String, Object> response = new HashMap<>();
    if (projectPage.hasContent()) {
      response.put("status", 200);
      response.put("message", "Projects found by NGO ID successfully");
      response.put("data", projectPage.getContent());
      response.put("currentPage", projectPage.getNumber());
      response.put("totalItems", projectPage.getTotalElements());
      response.put("totalPages", projectPage.getTotalPages());
      return ResponseEntity.ok(response);
    } else {
      response.put("status", 404);
      response.put("message", "No projects found for this NGO ID");
      return ResponseEntity.status(404).body(response);
    }
  }

  // projectby budget id
  @GetMapping("/projectshowbyprojectBudget/{projectBudget}")
  public ResponseEntity<Map<String, Object>> getProjectByBudget(
      @PathVariable String projectBudget,
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "10") int size) {

    Pageable pageable = PageRequest.of(page, size);
    Page<Project> projectPage = projectRepository.getProjectByProjectBudget(projectBudget, pageable);

    Map<String, Object> response = new HashMap<>();
    if (projectPage.hasContent()) {
      response.put("status", 200);
      response.put("message", "Projects found by Budget amount successfully");
      response.put("data", projectPage.getContent());
      response.put("currentPage", projectPage.getNumber());
      response.put("totalItems", projectPage.getTotalElements());
      response.put("totalPages", projectPage.getTotalPages());
      return ResponseEntity.ok(response);
    } else {
      response.put("status", 404);
      response.put("message", "No projects found for this  Budget amount");
      return ResponseEntity.status(404).body(response);
    }
  }

  // projectby status

  @GetMapping("/projects/by-status")
  public ResponseEntity<Map<String, Object>> getProjectsByStatus(
      @RequestParam String status,
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "10") int size) {

    Pageable pageable = PageRequest.of(page, size);
    Page<Project> projectPage = projectRepository.findByProjectStatus(status, pageable);

    Map<String, Object> response = new HashMap<>();
    if (projectPage.hasContent()) {
      response.put("status", 200);
      response.put("message", "Projects found by status successfully");
      response.put("data", projectPage.getContent());
      response.put("currentPage", projectPage.getNumber());
      response.put("totalItems", projectPage.getTotalElements());
      response.put("totalPages", projectPage.getTotalPages());
      return ResponseEntity.ok(response);
    } else {
      response.put("status", 404);
      response.put("message", "No projects found for this status");
      return ResponseEntity.status(404).body(response);
    }
  }


  @GetMapping("/projects/filter")
public ResponseEntity<Map<String, Object>> filterProjects(
        @RequestParam(required = false) Integer ngoId,
        @RequestParam(required = false) Integer categoryId,
        @RequestParam(required = false) String projectBudget,
        @RequestParam(required = false) String status,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size) {

    Pageable pageable = PageRequest.of(page, size);
    Page<Project> projectPage = projectRepository.filterProjects(ngoId, categoryId, projectBudget, status, pageable);

    List<Map<String, Object>> projectList = projectPage.getContent().stream().map(project -> {
        Map<String, Object> map = new HashMap<>();
        map.put("projectId", project.getProjectId());
        map.put("projectName", project.getProjectName());
        map.put("projectDescription", project.getProjectDescription());
        map.put("projectStatus", project.getProjectStatus());
        map.put("projectBudget", project.getProjectBudget());
        map.put("projectLocation", project.getProjectLocation());
        map.put("projectShortDescription", project.getProjectShortDescription());
        map.put("projectMainImage", project.getProjectMainImage());
        map.put("projectDEpartmentName", project.getProjectDEpartmentName());
        map.put("ngoId", project.getNgoId());
        map.put("categoryId", project.getCategoryId());
        map.put("projectImages", project.getProjectImages());

        // Fetch Category name
        Optional<Category> categoryOpt = categoryRepository.findById(project.getCategoryId());
        map.put("categoryName", categoryOpt.map(Category::getCategoryName).orElse("Category not found"));

        // Fetch NGO info
        Optional<NGO> ngoOpt = ngoRepository.findById(project.getNgoId());
        if (ngoOpt.isPresent()) {
            NGO ngo = ngoOpt.get();
            Map<String, Object> ngoMap = new HashMap<>();
            ngoMap.put("id", ngo.getId());
            ngoMap.put("ngoname", ngo.getOrganizationName());
            ngoMap.put("ngoemailid", ngo.getEmailId());
            ngoMap.put("ngousername", ngo.getUserName());
            ngoMap.put("ngoPassword", ngo.getPassword());
            ngoMap.put("ageoforganization", ngo.getAgeOfOrganization());
            ngoMap.put("annualturnover", ngo.getAnnualTurnover());
            ngoMap.put("nameofcontactprson", ngo.getNameOfContactPerson());
            ngoMap.put("numberofcontactprson", ngo.getContactNumber());
            map.put("ngoInfo", ngoMap);
        } else {
            map.put("ngoInfo", "NGO not found");
        }

        return map;
    }).toList();

    Map<String, Object> response = new HashMap<>();
    if (!projectList.isEmpty()) {
        response.put("status", 200);
        response.put("message", "Projects found successfully");
        response.put("data", projectList);
        response.put("currentPage", projectPage.getNumber());
        response.put("totalItems", projectPage.getTotalElements());
        response.put("totalPages", projectPage.getTotalPages());
        return ResponseEntity.ok(response);
    } else {
        response.put("status", 404);
        response.put("message", "No projects found for the provided filters");
        return ResponseEntity.status(404).body(response);
    }
}




 // projectby category id
  @GetMapping("/projectshowbycompanieId/{companieId}")
  public ResponseEntity<Map<String, Object>> getprojetcByCompanyId(
      @PathVariable int companieId,
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "10") int size) {

    Pageable pageable = PageRequest.of(page, size);
    Page<Project> projectPage = projectRepository.findBycompanieId(companieId, pageable);

    Map<String, Object> response = new HashMap<>();
    if (projectPage.hasContent()) {
      response.put("status", 200);
      response.put("message", "Projects found by companie ID successfully");
      response.put("data", projectPage.getContent());
      response.put("currentPage", projectPage.getNumber());
      response.put("totalItems", projectPage.getTotalElements());
      response.put("totalPages", projectPage.getTotalPages());
      return ResponseEntity.ok(response);
    } else {
      response.put("status", 404);
      response.put("message", "No projects found for this category ID");
      return ResponseEntity.status(404).body(response);
    }
  }

}
