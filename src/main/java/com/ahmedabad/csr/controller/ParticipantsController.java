package com.ahmedabad.csr.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.ahmedabad.csr.entities.Participants;
import com.ahmedabad.csr.entities.Project;
import com.ahmedabad.csr.repository.ApiResponse;
import com.ahmedabad.csr.repository.ParticipantsRepository;
import com.ahmedabad.csr.services.ParticipantsService;

@RestController

public class ParticipantsController {

    @Autowired
    private ParticipantsService participantsService;
    @Autowired
    private ParticipantsRepository participantsRepository;

    @PostMapping("/createParticipant")
    public ResponseEntity<Map<String, Object>> createParticipant(@RequestBody Participants participant) {
        Participants saved = participantsService.saveParticipant(participant);
        Map<String, Object> response = new HashMap<>();
        response.put("status", 201);
        response.put("message", "Participant created successfully");
        response.put("data", saved);
        return ResponseEntity.status(201).body(response);
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
    // public ResponseEntity<Map<String, Object>> getParticipantById(@PathVariable int id) {
    //     Optional<Participants> participant = participantsService.getParticipantById(id);
    //     Map<String, Object> response = new HashMap<>();
    //     if (participant.isPresent()) {
    //         response.put("status", 200);
    //         response.put("data", participant.get());
    //         return ResponseEntity.ok(response);
    //     } else {
    //         response.put("status", 404);
    //         response.put("message", "Participant not found");
    //         return ResponseEntity.status(404).body(response);
    //     }
    // }

    @GetMapping("/showbyParticipantId/{id}")
public ResponseEntity<Map<String, Object>> getParticipantById(@PathVariable int id) {
    Optional<Participants> participant = participantsService.getParticipantById(id);
    Map<String, Object> response = new HashMap<>();
    
    
    if (participant.isPresent()) {
        Participants p = participant.get();
        Optional<Participants> project = participantsService.getParticipantById(p.getProjetcId());
        
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
}
