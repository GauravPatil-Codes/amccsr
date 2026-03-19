package com.ahmedabad.csr.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.ahmedabad.csr.entities.Companies;
import com.ahmedabad.csr.repository.ApiResponse;
import com.ahmedabad.csr.services.CompaninesServices;

@RestController
public class CompaniesController {

    @Autowired
    private CompaninesServices companiesService;

    @PostMapping("/addCompany")
    public ResponseEntity<ApiResponse<Companies>> createCompany(@RequestBody Companies company) {
        try {
            Companies createdCompany = companiesService.addCompany(company);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ApiResponse<>(200, "Company created successfully", createdCompany));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(400, "Error creating company: " + e.getMessage(), null));
        }
    }

    @GetMapping("/companyShowById/{id}")
    public ResponseEntity<ApiResponse<Companies>> getCompanyById(@PathVariable int id) {
        Optional<Companies> company = companiesService.getCompanyById(id);
        return company.map(value -> ResponseEntity.ok(new ApiResponse<>(200, "Company retrieved successfully", value)))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ApiResponse<>(404, "Company not found with id: " + id, null)));
    }

    @GetMapping("/listallcompanies")
    public ResponseEntity<ApiResponse<Page<Companies>>> getAllCompanies(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        try {
            Page<Companies> companiesPage = companiesService.getAllCompanies(PageRequest.of(page, size));
            return ResponseEntity.ok(new ApiResponse<>(200, "Companies retrieved successfully", companiesPage));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(500, "Error retrieving companies: " + e.getMessage(), null));
        }
    }

    @PutMapping("/companyUpdate/{id}")
    public ResponseEntity<ApiResponse<Companies>> updateCompany(
            @PathVariable int id, @RequestBody Companies companyDetails) {
        try {
            Companies updatedCompany = companiesService.updateCompany(id, companyDetails);
            return ResponseEntity.ok(new ApiResponse<>(200, "Company updated successfully", updatedCompany));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(404, e.getMessage(), null));
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteCompany(@PathVariable int id) {
        try {
            companiesService.deleteCompany(id);
            return ResponseEntity.ok(new ApiResponse<>(200, "Company deleted successfully", null));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(404, e.getMessage(), null));
        }
    }

    @GetMapping("/company/{categoryId}")
    public ResponseEntity<ApiResponse<Page<Companies>>> getCompaniesByCategory(
            @PathVariable int categoryId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        try {
            Page<Companies> companiesPage = companiesService.getCompaniesByCategory(
                    categoryId, PageRequest.of(page, size));
            
            if (companiesPage.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ApiResponse<>(404, "No companies found for category ID: " + categoryId, null));
            }
            
            return ResponseEntity.ok(new ApiResponse<>(200, 
                    "Companies retrieved by category successfully", companiesPage));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(500, "Error retrieving companies by category: " + e.getMessage(), null));
        }
    }
}