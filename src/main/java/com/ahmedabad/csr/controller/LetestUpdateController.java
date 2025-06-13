package com.ahmedabad.csr.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.ahmedabad.csr.entities.Category;
import com.ahmedabad.csr.entities.LetestUpdate;
import com.ahmedabad.csr.repository.ApiResponse;
import com.ahmedabad.csr.repository.LetestupdateRepository;
import com.ahmedabad.csr.services.LetestUpdateServices;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
public class LetestUpdateController {
    @Autowired
    private LetestupdateRepository letestupdateRepository;

    @Autowired
    private LetestUpdateServices letestUpdateServices;

    @PostMapping("/AddLetestupdates")
    public ResponseEntity<ApiResponse<LetestUpdate>> addLetestUpdates(@RequestBody LetestUpdate letestUpdate) {
        LetestUpdate savedLetestUpdates = letestupdateRepository.save(letestUpdate);
        ApiResponse<LetestUpdate> response = new ApiResponse<>(200, "Letets Update added successfully", savedLetestUpdates);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
    



}
