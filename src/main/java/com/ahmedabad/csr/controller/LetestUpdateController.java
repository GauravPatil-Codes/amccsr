package com.ahmedabad.csr.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.ahmedabad.csr.entities.Category;
import com.ahmedabad.csr.entities.LetestUpdate;
import com.ahmedabad.csr.entities.NGO;
import com.ahmedabad.csr.repository.ApiResponse;
import com.ahmedabad.csr.repository.LetestUpdateRepository;
import com.ahmedabad.csr.services.LetestUpdateServices;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
public class LetestUpdateController {
    @Autowired
    private LetestUpdateRepository letestupdateRepository;

    @Autowired
    private LetestUpdateServices letestUpdateServices;

    @PostMapping("/AddLetestupdates")
    public ResponseEntity<ApiResponse<LetestUpdate>> addLetestUpdates(@RequestBody LetestUpdate letestUpdate) {
        LetestUpdate savedLetestUpdates = letestupdateRepository.save(letestUpdate);
        ApiResponse<LetestUpdate> response = new ApiResponse<>(200, "Letets Update added successfully",
                savedLetestUpdates);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/listallLetestUpdates")
    public ResponseEntity<ApiResponse<Page<LetestUpdate>>> listAllLetestUpdates(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("letestupdateid").descending());
        Page<LetestUpdate> letestUpdates = letestupdateRepository.findAll(pageable);

        ApiResponse<Page<LetestUpdate>> response = new ApiResponse<>(200, "Letest updates fetched successfully",
                letestUpdates);
        return ResponseEntity.ok(response);
    }

}
