package com.ahmedabad.csr.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ahmedabad.csr.entities.Category;
import com.ahmedabad.csr.entities.NGO;
import com.ahmedabad.csr.repository.ApiResponse;
import com.ahmedabad.csr.repository.NgoRepository;
import com.ahmedabad.csr.services.NgoServices;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

@RestController
public class NgoController {
  @Autowired
  private NgoServices ngoService;

  @Autowired
  private NgoRepository ngoRepository;

  @PostMapping("/addngo")
  public ResponseEntity<ApiResponse<NGO>> addNgo(@RequestBody NGO ngo) {
    try {
      // First check for validation errors
      if (ngoService.isEmailExists(ngo.getEmailId())) {
        ApiResponse<NGO> errorResponse = new ApiResponse<>(400, "Email ID already exists: " + ngo.getEmailId(), null);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
      }

      if (ngoService.isUserNameExists(ngo.getUserName())) {
        ApiResponse<NGO> errorResponse = new ApiResponse<>(400, "Username already exists: " + ngo.getUserName(), null);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
      }

      NGO savedNgo = ngoService.saveNGO(ngo);
      ApiResponse<NGO> response = new ApiResponse<>(201, "NGO added successfully", savedNgo);
      return new ResponseEntity<>(response, HttpStatus.CREATED);

    } catch (RuntimeException e) {

      ApiResponse<NGO> errorResponse = new ApiResponse<>(500, "Error: " + e.getMessage(), null);
      return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    } catch (Exception e) {

      ApiResponse<NGO> errorResponse = new ApiResponse<>(500, "Unexpected error: " + e.getMessage(), null);
      return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @GetMapping("/listallNgo")
  public ResponseEntity<ApiResponse<Page<NGO>>> listNGOs(
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "5") int size) {
    Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
    Page<NGO> ngos = ngoRepository.findAll(pageable);

    return ResponseEntity.ok(new ApiResponse<>(200, "NGOs fetched successfully", ngos));
  }
}
