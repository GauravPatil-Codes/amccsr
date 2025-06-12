package com.ahmedabad.csr.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ahmedabad.csr.entities.Category;
import com.ahmedabad.csr.entities.NGO;
import com.ahmedabad.csr.repository.ApiResponse;
import com.ahmedabad.csr.repository.NgoRepository;
import com.ahmedabad.csr.services.NgoServices;

@RestController
public class NgoController {
    @Autowired
    private NgoServices ngoService;

    @Autowired
    private NgoRepository ngoRepository;

      @PostMapping("/addngo")
    public ResponseEntity<ApiResponse<NGO>> addCategory(@RequestBody NGO ngo) {
        NGO savedNgo = ngoRepository.save(ngo);
        ApiResponse<NGO> response = new ApiResponse<>(200, "NGO added successfully", savedNgo);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }


}
