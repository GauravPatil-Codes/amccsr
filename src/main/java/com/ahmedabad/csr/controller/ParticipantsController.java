package com.ahmedabad.csr.controller;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;

import com.ahmedabad.csr.entities.Category;
import com.ahmedabad.csr.entities.Participants;
import com.ahmedabad.csr.entities.Project;
import com.ahmedabad.csr.repository.ApiResponse;
import com.ahmedabad.csr.repository.CategoryRepository;
import com.ahmedabad.csr.repository.ParticipantsRepository;
import com.ahmedabad.csr.repository.ProjectRepository;
import com.ahmedabad.csr.services.EmailService;
import com.ahmedabad.csr.services.ParticipantsService;
import com.ahmedabad.csr.services.ProjectServices;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@RestController

public class ParticipantsController {

    @Autowired
    private ParticipantsService participantsService;
    @Autowired
    private ParticipantsRepository participantsRepository;
    @Autowired
    private ProjectServices ProjectServices;
        @Autowired
    private EmailService emailService;

    @Autowired
    private CategoryRepository categoryRepository;

    private static final Logger logger = LoggerFactory.getLogger(ParticipantsController.class);

    @PostMapping("/createParticipant")
    public ResponseEntity<Map<String, Object>> createParticipant(@RequestBody Participants participant) {
        Participants saved = participantsService.saveParticipant(participant);

        // Send thank you email for showing interest in the project
        sendProjectInterestEmailAsync(participant);

        Map<String, Object> response = new HashMap<>();
        response.put("status", 201);
        response.put("message", "Participant created successfully");
        response.put("data", saved);
        return ResponseEntity.status(201).body(response);
    }

    /**
     * Send thank you email asynchronously when user shows interest in a project
     */
    @Async
    private void sendProjectInterestEmailAsync(Participants participant) {
        try {
            // Get project details
            Optional<Project> projectOpt = ProjectServices.getProjectById(participant.getProjetcId());
            if (projectOpt.isPresent()) {
                Project project = projectOpt.get();

                // Prepare project details for email
                Map<String, Object> projectDetails = new HashMap<>();
                projectDetails.put("category", getCategoryName(project.getCategoryId()));
                projectDetails.put("location", project.getProjectLocation());
                projectDetails.put("budget", project.getProjectBudget());
                projectDetails.put("impact", project.getImpactpeople());

                // Send project interest email
                emailService.sendProjectInterestEmail(
                        participant.getParticipantEmail(),
                        participant.getParticipantName(),
                        project.getProjectName(),
                        projectDetails
                );

                logger.info("📧 Project interest email queued for: {} for project: {}",
                        participant.getParticipantEmail(), project.getProjectName());
            }
        } catch (Exception e) {
            logger.error("❌ Failed to queue project interest email: {}", e.getMessage());
            // Don't throw exception - email failure should not affect participant creation
        }
    }

    /**
     * Helper method to get category name by ID
     */
    private String getCategoryName(int categoryId) {
        try {
            Optional<Category> categoryOpt = categoryRepository.findById(categoryId);
            return categoryOpt.map(Category::getCategoryName).orElse("N/A");
        } catch (Exception e) {
            return "N/A";
        }
    }

    @GetMapping("/listAllParticipant")
    public ResponseEntity<ApiResponse<Page<Participants>>> getAllParticipants(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("participantID").descending());
        Page<Participants> Participants = participantsRepository.findAll(pageable);

        return ResponseEntity.ok(new ApiResponse<>(200, "Participants fetched successfully", Participants));
    }

    // @GetMapping("/showbyParticipantId/{id}")
    // public ResponseEntity<Map<String, Object>> getParticipantById(@PathVariable
    // int id) {
    // Optional<Participants> participant =
    // participantsService.getParticipantById(id);
    // Map<String, Object> response = new HashMap<>();
    // if (participant.isPresent()) {
    // response.put("status", 200);
    // response.put("data", participant.get());
    // return ResponseEntity.ok(response);
    // } else {
    // response.put("status", 404);
    // response.put("message", "Participant not found");
    // return ResponseEntity.status(404).body(response);
    // }
    // }

    @GetMapping("/showbyParticipantId/{id}")
    public ResponseEntity<Map<String, Object>> getParticipantById(@PathVariable int id) {
        Optional<Participants> participant = participantsService.getParticipantById(id);
        Map<String, Object> response = new HashMap<>();

        if (participant.isPresent()) {
            Participants p = participant.get();

            // Fix: Use project service instead of participant service
            Optional<Project> project = ProjectServices.getProjectById(p.getProjetcId());

            Map<String, Object> data = new HashMap<>();
            data.put("participant", p);
            data.put("project", project.orElse(null));

            response.put("status", 200);
            response.put("data", data);
            return ResponseEntity.ok(response);
        } else {
            response.put("status", 404);
            response.put("message", "Participant not found");
            return ResponseEntity.status(404).body(response);
        }
    }

    @PutMapping("/updateParticipant/{id}")
    public ResponseEntity<Map<String, Object>> updateParticipant(
            @PathVariable int id,
            @RequestBody Participants participant) {
        Participants updated = participantsService.updateParticipant(id, participant);
        Map<String, Object> response = new HashMap<>();
        if (updated != null) {
            response.put("status", 200);
            response.put("message", "Participant updated successfully");
            response.put("data", updated);
            return ResponseEntity.ok(response);
        } else {
            response.put("status", 404);
            response.put("message", "Participant not found");
            return ResponseEntity.status(404).body(response);
        }
    }

    @DeleteMapping("/deleteParticipant/{id}")
    public ResponseEntity<Map<String, Object>> deleteParticipant(@PathVariable int id) {
        participantsService.deleteParticipant(id);
        Map<String, Object> response = new HashMap<>();
        response.put("status", 200);
        response.put("message", "Participant deleted successfully");
        return ResponseEntity.ok(response);
    }



   @PostMapping("/send-email")
    public ResponseEntity<Map<String, Object>> sendEmail(@RequestBody Map<String, String> emailData) {
        Map<String, Object> response = new HashMap<>();
        
        try {
        
            String to = emailData.get("to");           // participant email
            String subject = emailData.get("subject"); // email subject  
            String body = emailData.get("body");       // email body/message
            
            logger.info("📧 Sending email to {} with subject: {}", to, subject);
            
            // Validate required fields
            if (to == null || to.trim().isEmpty()) {
                response.put("success", false);
                response.put("error", "Recipient email (to) is required");
                return ResponseEntity.badRequest().body(response);
            }
            
            if (subject == null || subject.trim().isEmpty()) {
                response.put("success", false);
                response.put("error", "Email subject is required");
                return ResponseEntity.badRequest().body(response);
            }
            
            if (body == null || body.trim().isEmpty()) {
                response.put("success", false);
                response.put("error", "Email body is required");
                return ResponseEntity.badRequest().body(response);
            }
            
            // Send email using your existing service
            emailService.sendEmail(to, subject, body);
            
            // If we reach here, email was sent successfully
            response.put("success", true);
            response.put("message", "Email sent successfully");
            response.put("timestamp", LocalDateTime.now().toString());
            response.put("to", to);
            response.put("subject", subject);
            
            logger.info("✅ Email sent successfully to {}", to);
            return ResponseEntity.ok(response);
            
        } catch (RuntimeException e) {
            logger.error("❌ Runtime error sending email: {}", e.getMessage(), e);
            response.put("success", false);
            response.put("error", "Failed to send email: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
            
        } catch (Exception e) {
            logger.error("❌ Unexpected error sending email: {}", e.getMessage(), e);
            response.put("success", false);
            response.put("error", "Internal server error: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }


   
}
