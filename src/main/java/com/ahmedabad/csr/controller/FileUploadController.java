package com.ahmedabad.csr.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.ahmedabad.csr.entities.Companies;
import com.ahmedabad.csr.entities.Gallery;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.file.*;
import java.util.*;

@RestController
@RequestMapping("/api/files")
public class FileUploadController {

   
    private static final String SERVER_STORAGE_PATH = "/files/public_html/SRS-documents";
    private static final String PUBLIC_BASE_URL = "https://lakhpatididi.in/SRS-documents";

  
    private static final Set<String> IMAGE_EXTENSIONS = Set.of("jpg", "jpeg", "png", "gif", "webp");
    private static final Set<String> VIDEO_EXTENSIONS = Set.of("mp4", "mov", "avi", "mkv", "webm");
    private static final Set<String> DOCUMENT_EXTENSIONS = Set.of("pdf", "doc", "docx", "xls", "xlsx", "ppt", "pptx", "txt");

    @PostMapping("/upload")
    public ResponseEntity<Map<String, Object>> uploadFiles(@RequestParam("files") MultipartFile[] files) {
        Map<String, Object> response = new HashMap<>();
        List<String> fileUrls = new ArrayList<>();

        try {
            for (MultipartFile file : files) {
                if (file.isEmpty()) continue;

                String originalName = file.getOriginalFilename();
                String fileExtension = getFileExtension(originalName).toLowerCase();
                String subfolder = determineSubfolder(fileExtension);
                
                if (subfolder == null) {
                    continue;
                }

               
                Path targetDir = Paths.get(SERVER_STORAGE_PATH, subfolder);
                Files.createDirectories(targetDir);

                String safeName = originalName.replaceAll("[^a-zA-Z0-9.-]", "_");
                Path targetPath = targetDir.resolve(safeName);
                
                
                file.transferTo(targetPath);
                
              
                String encodedName = URLEncoder.encode(originalName, "UTF-8")
                                             .replace("+", "%20");
                fileUrls.add(PUBLIC_BASE_URL + "/" + subfolder + "/" + encodedName);
            }

            if (fileUrls.isEmpty()) {
                response.put("status", 400);
                response.put("message", "No valid files were uploaded");
            } else {
                response.put("status", 200);
                response.put("message", "Files uploaded successfully");
                response.put("fileurl", fileUrls);
            }

        } catch (Exception e) {
            response.put("status", 500);
            response.put("message", "Upload failed: " + e.getMessage());
            e.printStackTrace();
        }

        return ResponseEntity.ok(response);
    }

    private String getFileExtension(String filename) {
        int dotIndex = filename.lastIndexOf('.');
        return (dotIndex == -1) ? "" : filename.substring(dotIndex + 1);
    }

    private String determineSubfolder(String fileExtension) {
        if (IMAGE_EXTENSIONS.contains(fileExtension)) {
            return "images";
        } else if (VIDEO_EXTENSIONS.contains(fileExtension)) {
            return "videos";
        } else if (DOCUMENT_EXTENSIONS.contains(fileExtension)) {
            return "documents";
        }
        return null;
    }



   
}