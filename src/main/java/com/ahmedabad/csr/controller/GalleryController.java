package com.ahmedabad.csr.controller;

import com.ahmedabad.csr.entities.Gallery;
import com.ahmedabad.csr.services.GalleryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
// @RequestMapping("gallery")
public class GalleryController {

    @Autowired
    private GalleryService galleryService;

    // Create
    @PostMapping("/addgallery")
    public ResponseEntity<Map<String, Object>> addGallery(@RequestBody Gallery gallery) {
        Map<String, Object> response = new HashMap<>();

        if (!gallery.getFileType().equalsIgnoreCase("image") && !gallery.getFileType().equalsIgnoreCase("video")) {
            response.put("status", 400);
            response.put("message", "Invalid fileType. Allowed values: image or video");
            return ResponseEntity.badRequest().body(response);
        }

        Gallery saved = galleryService.saveGallery(gallery);

        response.put("status", 200);
        response.put("message", "Gallery entry saved successfully");
        response.put("data", saved);
        return ResponseEntity.ok(response);
    }

    // Read All
    @GetMapping("/listallgallery")
    public ResponseEntity<List<Gallery>> getAll() {
        return ResponseEntity.ok(galleryService.getAllGallery());
    }

    // Read One
    @GetMapping("/gallerybyid/{id}")
    public ResponseEntity<Gallery> getById(@PathVariable int id) {
        return ResponseEntity.ok(galleryService.getGalleryById(id));
    }

    // Update
    @PutMapping("/updategallery/{id}")
    public ResponseEntity<Gallery> update(@PathVariable int id, @RequestBody Gallery gallery) {
        return ResponseEntity.ok(galleryService.updateGallery(id, gallery));
    }

    // Delete
    @DeleteMapping("/deletegallery/{id}")
    public ResponseEntity<Map<String, Object>> delete(@PathVariable int id) {
       
        galleryService.deleteGallery(id);
        Map<String, Object> response = new HashMap<>();
        response.put("status", 200);
        response.put("message", "gallery deleted successfully");
        return ResponseEntity.ok(response);
    }


    @GetMapping("/galleryfileType/{fileType}")
public ResponseEntity<?> getByFileType(
        @PathVariable String fileType,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size) {
    
    // Validate file type
    if (!List.of("image", "video").contains(fileType.toLowerCase())) {
        return ResponseEntity.badRequest()
                .body(Map.of(
                    "status", 400,
                    "message", "Invalid type. Use 'image' or 'video'"
                ));
    }

    Page<Gallery> result = galleryService.getgallerybyfiletype(
        fileType.toLowerCase(), 
        PageRequest.of(page, size, Sort.by("createdAt").descending())
    );

    return result.isEmpty() 
        ? ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(Map.of(
                "status", 404,
                "message", "No " + fileType + "s found"
            ))
        : ResponseEntity.ok(Map.of(
            "status", 200,
            "data", result,
            "message", result.getContent().size() + " " + fileType + "s found"
        ));
}

}
