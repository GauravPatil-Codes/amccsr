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

import com.ahmedabad.csr.entities.FundAnIdea;

import com.ahmedabad.csr.repository.ApiResponse;
import com.ahmedabad.csr.repository.FundAnIdeaRepository;
import com.ahmedabad.csr.services.FundAnIdeaServices;

@RestController
public class FundAnIdeaController {
    @Autowired
    private FundAnIdeaServices fundAnIdeaService;

    @Autowired
    private FundAnIdeaRepository fundAnIdeaRepository;

    @PostMapping("/addFundanidea")
    // public ResponseEntity<ApiResponse<FundAnIdea>> addFundAnIdea(@RequestBody FundAnIdea fundAnIdea) {
    //     FundAnIdea savedFundAnIdea = fundAnIdeaService.saveFundAnIdea(fundAnIdea);
    //     return ResponseEntity.ok(new ApiResponse<>(200, "Fund An Idea added successfully", savedFundAnIdea));
    // }
// @PostMapping("/addFundanidea")
public ResponseEntity<ApiResponse<FundAnIdea>> addFundAnIdea(@RequestBody FundAnIdea fundAnIdea) {
    FundAnIdea savedFundAnIdea = fundAnIdeaService.saveFundAnIdea(fundAnIdea);
    return ResponseEntity.ok(new ApiResponse<>(200, "Fund An Idea added successfully", savedFundAnIdea));
}

    @GetMapping("/listallFundanidea")
    public ResponseEntity<ApiResponse<Page<FundAnIdea>>> listAllFundAnIdea(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("fundanideaid").descending());
        Page<FundAnIdea> fundAnIdea = fundAnIdeaRepository.findAll(pageable);

        return ResponseEntity.ok(new ApiResponse<>(200, "Fund An Idea fetched successfully", fundAnIdea));
    }

    @GetMapping("/fundanideashowbyid/{fundanideaid}")
    public ResponseEntity<ApiResponse<?>> getFundAnIdeaById(@PathVariable int fundanideaid) {
        Optional<FundAnIdea> fundAnIdea = fundAnIdeaRepository.findById(fundanideaid);

        if (fundAnIdea.isPresent()) {
            ApiResponse<FundAnIdea> response = new ApiResponse<>(200, "Fund An Idea fetched successfully",
                    fundAnIdea.get());
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            ApiResponse<String> response = new ApiResponse<>(404, "Fund An Idea not found", null);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/updateFundanIdea/{fundanideaid}")
    public ResponseEntity<Map<String, Object>> updateFundAnIdea(
            @PathVariable int fundanideaid,
            @RequestBody FundAnIdea fundAnIdea) {
        FundAnIdea updated = fundAnIdeaService.updateFundAnIdea(fundanideaid, fundAnIdea);
        Map<String, Object> response = new HashMap<>();
        if (updated != null) {
            response.put("status", 200);
            response.put("message", "Fund An Idea updated successfully");
            response.put("data", updated);
            return ResponseEntity.ok(response);
        } else {
            response.put("status", 404);
            response.put("message", "Fund An Idea not found");
            return ResponseEntity.status(404).body(response);
        }
    }

    // delete
    @DeleteMapping("/deleteFundAnIdea/{fundanideaid}")
    public ResponseEntity<Map<String, Object>> deleteFundAnIdea(@PathVariable int fundanideaid) {
        fundAnIdeaService.deleteFundAnIdea(fundanideaid);
        Map<String, Object> response = new HashMap<>();
        response.put("status", 200);
        response.put("message", "Fund An Idea deleted successfully");
        return ResponseEntity.ok(response);
    }
}
