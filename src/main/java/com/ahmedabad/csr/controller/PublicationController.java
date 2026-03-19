package com.ahmedabad.csr.controller;

import com.ahmedabad.csr.entities.Publication;
import com.ahmedabad.csr.repository.ApiResponse;
import com.ahmedabad.csr.services.PublicationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
// @RequestMapping("/api/publications")
public class PublicationController {

    @Autowired
    private PublicationService service;

    @PostMapping("/addpublications")
    public ApiResponse<Publication> create(@RequestBody Publication publication) {
        Publication saved = service.save(publication);
        return new ApiResponse<>(200, "Publication created successfully", saved);
    }

    @GetMapping("/publicationsyid/{id}")
    public ApiResponse<Publication> getById(@PathVariable int id) {
        Optional<Publication> publication = service.findById(id);
        return publication.map(pub -> new ApiResponse<>(200, "Publication found", pub))
                .orElseGet(() -> new ApiResponse<>(404, "Publication not found", null));
    }

    @GetMapping("/listAllpublications")
    public ApiResponse<Page<Publication>> getAll(@RequestParam(defaultValue = "0") int page,
                                                 @RequestParam(defaultValue = "10") int size) {
        Page<Publication> publications = service.findAll(PageRequest.of(page, size));
        return new ApiResponse<>(200, "Publication list fetched", publications);
    }

    @PutMapping("/updatepublications/{id}")
    public ApiResponse<Publication> update(@PathVariable int id, @RequestBody Publication publication) {
        try {
            Publication updated = service.update(id, publication);
            return new ApiResponse<>(200, "Publication updated", updated);
        } catch (RuntimeException e) {
            return new ApiResponse<>(404, e.getMessage(), null);
        }
    }

    @DeleteMapping("/deletepublications/{id}")
    public ApiResponse<String> delete(@PathVariable int id) {
        try {
            service.deleteById(id);
            return new ApiResponse<>(200, "Publication deleted", "Deleted ID: " + id);
        } catch (Exception e) {
            return new ApiResponse<>(500, "Error deleting publication", null);
        }
    }
}
