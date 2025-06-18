package com.ahmedabad.csr.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ahmedabad.csr.entities.Documents;
import com.ahmedabad.csr.repository.ApiResponse;
import com.ahmedabad.csr.services.DocumentServiceImpl;

@RestController
public class DocumentController {

    @Autowired
    private DocumentServiceImpl documentService;

    @PostMapping("/addDocuments")
    public ResponseEntity<ApiResponse<Documents>> createDocuments(@RequestBody Documents documents) {
        try {
            Documents createdDocument = documentService.addDocuments(documents);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ApiResponse<>(200, "Document created successfully", createdDocument));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ApiResponse<>(400, "Error creating Document: " + e.getMessage(), null));
        }
    }

    @GetMapping("/documentShowById/{id}")
    public ResponseEntity<ApiResponse<Documents>> getDocumentById(@PathVariable int id) {
        Optional<Documents> document = documentService.getDocumentById(id);
        return document.map(value -> ResponseEntity.ok(new ApiResponse<>(200, "Document retrieved successfully", value)))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ApiResponse<>(404, "Document not found with id: " + id, null)));
    }

    @GetMapping("/listalldocuments")
    public ResponseEntity<ApiResponse<Page<Documents>>> getAllDocuments(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        try {
            Page<Documents> documentsPage = documentService.getAlldocument(PageRequest.of(page, size));
            return ResponseEntity.ok(new ApiResponse<>(200, "Documents retrieved successfully", documentsPage));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(500, "Error retrieving documents: " + e.getMessage(), null));
        }
    }

    @PutMapping("/documentUpdate/{id}")
    public ResponseEntity<ApiResponse<Documents>> updateDocument(
            @PathVariable int id, @RequestBody Documents documentDetails) {
        try {
            Documents updatedDocument = documentService.updateDocument(id, documentDetails);
            return ResponseEntity.ok(new ApiResponse<>(200, "Document updated successfully", updatedDocument));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(404, e.getMessage(), null));
        }
    }

    @DeleteMapping("/deleteDocument/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteDocument(@PathVariable int id) {
        try {
            documentService.deleteDocument(id);
            return ResponseEntity.ok(new ApiResponse<>(200, "Document deleted successfully", null));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(404, e.getMessage(), null));
        }
    }

    @GetMapping("/documents/{documentType}")
    public ResponseEntity<ApiResponse<Page<Documents>>> getDocumentsByType(
            @PathVariable String documentType,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        try {
            Page<Documents> documentsPage = documentService.getDocumentsBytype(
                    documentType, PageRequest.of(page, size));
            
            if (documentsPage.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new ApiResponse<>(404, "No documents found for type: " + documentType, null));
            }
            
            return ResponseEntity.ok(new ApiResponse<>(200, 
                    "Documents retrieved by type successfully", documentsPage));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse<>(500, "Error retrieving documents by type: " + e.getMessage(), null));
        }
    }
}