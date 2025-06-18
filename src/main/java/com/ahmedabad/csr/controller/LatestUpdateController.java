package com.ahmedabad.csr.controller;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;


import com.ahmedabad.csr.entities.LatestUpdate;

import com.ahmedabad.csr.repository.ApiResponse;
import com.ahmedabad.csr.repository.LatestUpdateRepository;
import com.ahmedabad.csr.services.LatestUpdateServices;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
public class LatestUpdateController {
    @Autowired
    private LatestUpdateRepository letestupdateRepository;

    @Autowired
    private LatestUpdateServices letestUpdateServices;

    @PostMapping("/AddLatestupdates")
    public ResponseEntity<ApiResponse<LatestUpdate>> addLatestUpdates(@RequestBody LatestUpdate letestUpdate) {
        LatestUpdate savedLatestUpdates = letestupdateRepository.save(letestUpdate);
        ApiResponse<LatestUpdate> response = new ApiResponse<>(200, "Letets Update added successfully",
                savedLatestUpdates);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/listallLatestUpdates")
    public ResponseEntity<ApiResponse<Page<LatestUpdate>>> listAllLatestUpdates(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("letestupdateid").descending());
        Page<LatestUpdate> letestUpdates = letestupdateRepository.findAll(pageable);

        ApiResponse<Page<LatestUpdate>> response = new ApiResponse<>(200, "Latest updates fetched successfully",
                letestUpdates);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/letestupdateshowbyid/{letestupdateid}")
    public ResponseEntity<ApiResponse<?>> getCategoryById(@PathVariable int letestupdateid) {
        Optional<LatestUpdate> letestUpdate = letestupdateRepository.findById(letestupdateid);

        if (letestUpdate.isPresent()) {
            ApiResponse<LatestUpdate> response = new ApiResponse<>(200, "letest Update fetched successfully",
                    letestUpdate.get());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            ApiResponse<String> response = new ApiResponse<>(404, "letest Update not found", null);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

      @GetMapping("/letestupdateshowbystatus/{status}")
    public ResponseEntity<ApiResponse<?>> getLatestupdateBystatus(@PathVariable String status) {
        Optional<LatestUpdate> letestUpdate = letestupdateRepository.findBystatus(status);

        if (letestUpdate.isPresent()) {
            ApiResponse<LatestUpdate> response = new ApiResponse<>(200, "letest Update fetched successfully",
                    letestUpdate.get());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            ApiResponse<String> response = new ApiResponse<>(404, "letest Update not found", null);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/updateletestupdates/{letestupdateid}")
    public ResponseEntity<ApiResponse<LatestUpdate>> updateLatestUpdate(
            @PathVariable int letestupdateid,
            @RequestBody LatestUpdate updatedLatestUpdate) { // Fixed parameter name
        try {
            LatestUpdate updateLatestUpdate = letestUpdateServices.updateletestUpdate(letestupdateid,
                    updatedLatestUpdate);
            ApiResponse<LatestUpdate> response = new ApiResponse<>(200, "Latest Updates updated successfully",
                    updateLatestUpdate);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (RuntimeException e) {
            ApiResponse<LatestUpdate> response = new ApiResponse<>(404, e.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/deleteletestupdate/{letestupdateid}")
    public ResponseEntity<ApiResponse<String>> deleteLatestUpdate(@PathVariable int letestupdateid) {
        try {
            letestUpdateServices.deleteLatestUpdateById(letestupdateid);
            ApiResponse<String> response = new ApiResponse<>(200, "Latest updates deleted successfully", null);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (RuntimeException e) {
            ApiResponse<String> response = new ApiResponse<>(404, e.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }
}
