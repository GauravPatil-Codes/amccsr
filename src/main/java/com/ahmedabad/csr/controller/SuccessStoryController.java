package com.ahmedabad.csr.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ahmedabad.csr.entities.LatestUpdate;
import com.ahmedabad.csr.entities.SuccessStory;
import com.ahmedabad.csr.repository.ApiResponse;
import com.ahmedabad.csr.repository.SuccessStoryRepository;
import com.ahmedabad.csr.services.SuccessStoryServices;

@RestController
public class SuccessStoryController {

    @Autowired
    private SuccessStoryServices successStoryService;
    @Autowired 
    private SuccessStoryRepository successStoryRepository;

    @PostMapping("/createsuccessstory")
    public ResponseEntity<ApiResponse<SuccessStory>> addSuccessStory(@RequestBody SuccessStory successStory) {
        SuccessStory savedSuccessStory = successStoryRepository.save(successStory);
        ApiResponse<SuccessStory> response = new ApiResponse<>(200, "Success Story added successfully",
                savedSuccessStory);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

}
