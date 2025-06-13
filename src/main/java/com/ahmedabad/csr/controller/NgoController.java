package com.ahmedabad.csr.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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


  // update
  @PutMapping("/updateNgo/{id}")
public ResponseEntity<NGO> updateNgo(@PathVariable int id, @RequestBody NGO updatedNgo) {
    Optional<NGO> optionalNgo = ngoRepository.findById(id);

    if (optionalNgo.isPresent()) {
        NGO existingNgo = optionalNgo.get();

        existingNgo.setOrganizationName(updatedNgo.getOrganizationName());
        existingNgo.setEmailId(updatedNgo.getEmailId());
        existingNgo.setUserName(updatedNgo.getUserName());
        existingNgo.setPassword(updatedNgo.getPassword());
        existingNgo.setAgeOfOrganization(updatedNgo.getAgeOfOrganization());
        existingNgo.setAnnualTurnover(updatedNgo.getAnnualTurnover());
        existingNgo.setNameOfContactPerson(updatedNgo.getNameOfContactPerson());
        existingNgo.setContactNumber(updatedNgo.getContactNumber());
        existingNgo.setNgo80GregistrationNumber(updatedNgo.getNgo80GregistrationNumber());
        existingNgo.setNgo80Gdocument(updatedNgo.getNgo80Gdocument());
        existingNgo.setNgo12AregistrationNumber(updatedNgo.getNgo12AregistrationNumber());
        existingNgo.setNgo12Adocument(updatedNgo.getNgo12Adocument());
        existingNgo.setCaCertifiedStatementUpload(updatedNgo.getCaCertifiedStatementUpload());
        existingNgo.setOrganizationRegistrationCertificate(updatedNgo.getOrganizationRegistrationCertificate());
        existingNgo.setCsr1RegistartionNumber(updatedNgo.getCsr1RegistartionNumber());
        existingNgo.setCsr1Document(updatedNgo.getCsr1Document());
        existingNgo.setBylaws(updatedNgo.getBylaws());
        existingNgo.setMoa(updatedNgo.getMoa());
        existingNgo.setCaptchaCode(updatedNgo.getCaptchaCode());
        existingNgo.setStatus(updatedNgo.getStatus());
        existingNgo.setCategory(updatedNgo.getCategory()); // if needed

        NGO saved = ngoRepository.save(existingNgo);
        return ResponseEntity.ok(saved);
    } else {
        return ResponseEntity.notFound().build();
    }
}

// delete
@DeleteMapping("/deleteNgo/{id}")
public ResponseEntity<String> deleteNgo(@PathVariable int id) {
    if (ngoRepository.existsById(id)) {
        ngoRepository.deleteById(id);
        return ResponseEntity.ok("NGO deleted successfully.");
    } else {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("NGO not found.");
    }
}

// show
@GetMapping("/getNgoById/{id}")
public ResponseEntity<NGO> getNgoById(@PathVariable int id) {
    Optional<NGO> ngo = ngoRepository.findById(id);
    return ngo.map(ResponseEntity::ok)
              .orElseGet(() -> ResponseEntity.notFound().build());
}


}
