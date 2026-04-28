package com.ahmedabad.csr.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ahmedabad.csr.entities.Companies;
import com.ahmedabad.csr.helper.FtpHelper;
import com.ahmedabad.csr.repository.ApiResponse;
import com.ahmedabad.csr.services.CompaninesServices;

@RestController
public class CompaniesController {

    @Autowired
    private CompaninesServices companiesService;
    @Autowired
    private FtpHelper ftpHelper;

    @PostMapping("/addCompany")
    public ResponseEntity<ApiResponse<Companies>> createCompany(
        @RequestParam("logo") MultipartFile logoFile,
        @RequestParam("panCard") MultipartFile panFile,
        @ModelAttribute Companies company) {

       
         try {
        // 🔹 Upload Logo
        String logoName = System.currentTimeMillis() + "_" + logoFile.getOriginalFilename();
        String logoUrl = ftpHelper.uploadFile(logoFile.getInputStream(), logoName);
        company.setCompanyLogo(logoUrl);

        // 🔹 Upload PAN Card (NEW)
        String panName = System.currentTimeMillis() + "_" + panFile.getOriginalFilename();
        String panUrl = ftpHelper.uploadFile(panFile.getInputStream(), panName);
        company.setPanCardFile(panUrl);

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
            @PathVariable int id,
            @ModelAttribute Companies companyDetails,
            @RequestParam(value = "logo", required = false) MultipartFile logoFile,
            @RequestParam(value = "panCard", required = false) MultipartFile panCardFile) {

        try {

           if (logoFile != null && !logoFile.isEmpty()) {
               String fileName = System.currentTimeMillis() + "_" + logoFile.getOriginalFilename();
               String fileUrl = ftpHelper.uploadFile(logoFile.getInputStream(), fileName);
                companyDetails.setCompanyLogo(fileUrl);
           }

           if (panCardFile != null && !panCardFile.isEmpty()) {
               String fileName = System.currentTimeMillis() + "_" + panCardFile.getOriginalFilename();
               String fileUrl = ftpHelper.uploadFile(panCardFile.getInputStream(), fileName);
               companyDetails.setPanCardFile(fileUrl);
            }

        
         Companies updatedCompany = companiesService.updateCompany(id, companyDetails);

         return ResponseEntity.ok(
                new ApiResponse<>(200, "Company updated successfully", updatedCompany));

       } catch (RuntimeException e) {
             return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ApiResponse<>(404, e.getMessage(), null));

       } catch (Exception e) {
           return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ApiResponse<>(400, e.getMessage(), null));
    }
}
    @PutMapping("/verifyCompany/{id}")
    public ResponseEntity<ApiResponse<Companies>> verifyCompany(@PathVariable int id) 
    {
    try {
        Companies verifiedCompany = companiesService.verifyCompany(id);

        return ResponseEntity.ok(
                new ApiResponse<>(200, "Company verified successfully", verifiedCompany));

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