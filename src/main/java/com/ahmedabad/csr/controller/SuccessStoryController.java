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

    @GetMapping("/listallSuccessStory")
    public ResponseEntity<ApiResponse<Page<SuccessStory>>> listAllSuccessStory(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("successstoryId").descending());
        Page<SuccessStory> successStory = successStoryRepository.findAll(pageable);

        ApiResponse<Page<SuccessStory>> response = new ApiResponse<>(200, "Success stories fetched successfully",
                successStory);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/successStoryshowbyid/{successstoryId}")
    public ResponseEntity<ApiResponse<?>> getCategoryById(@PathVariable int successstoryId) {
        Optional<SuccessStory> successStory = successStoryRepository.findById(successstoryId);

        if (successStory.isPresent()) {
            ApiResponse<SuccessStory> response = new ApiResponse<>(200, "Success Story fetched successfully",
                    successStory.get());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            ApiResponse<String> response = new ApiResponse<>(404, "Success Story not found", null);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/updateSuccessStory/{successstoryId}")
    public ResponseEntity<ApiResponse<SuccessStory>> updateSuccessStory(
            @PathVariable int successstoryId,
            @RequestBody SuccessStory updatedSuccessStory) {
        try {
            SuccessStory updateLatestUpdate = successStoryService.updateSuccessStory(successstoryId,
                    updatedSuccessStory);
            ApiResponse<SuccessStory> response = new ApiResponse<>(200, "Success Story updated successfully",
                    updateLatestUpdate);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (RuntimeException e) {
            ApiResponse<SuccessStory> response = new ApiResponse<>(404, e.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/deleteSuccessStory/{successstoryId}")
    public ResponseEntity<Map<String, Object>> deleteSuccessStory(@PathVariable int successstoryId) {
        successStoryService.deleteSuccessStoryById(successstoryId);
        Map<String, Object> response = new HashMap<>();
        response.put("status", 200);
        response.put("message", "Success Story deleted successfully");
        return ResponseEntity.ok(response);
    }


    @GetMapping("/SuccessStoryshowbycategoryid/{categoryId}")
public ResponseEntity<Map<String, Object>> getSuccessStoryByCategoryId(
    @PathVariable int categoryId,
    @RequestParam(defaultValue = "0") int page,
    @RequestParam(defaultValue = "10") int size) {
  
  Pageable pageable = PageRequest.of(page, size);
  Page<SuccessStory> successStoryPage = successStoryService.getSuccessStoryByCategoryId(categoryId, pageable);
  Map<String, Object> response = new HashMap<>();
  
  if (successStoryPage.hasContent()) {
    response.put("status", 200);
    response.put("message", "Success stories found by category ID successfully");
    response.put("data", successStoryPage.getContent());
    response.put("currentPage", successStoryPage.getNumber());
    response.put("totalItems", successStoryPage.getTotalElements());
    response.put("totalPages", successStoryPage.getTotalPages());
    return ResponseEntity.ok(response);
  } else {
    response.put("status", 404);
    response.put("message", "No success stories found for this category ID");
    return ResponseEntity.status(404).body(response);
  }
}
}
