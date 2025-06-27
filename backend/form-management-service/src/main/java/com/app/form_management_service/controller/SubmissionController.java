package com.app.form_management_service.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.form_management_service.dto.SubmissionDTO;
import com.app.form_management_service.model.Submission;
import com.app.form_management_service.repository.SubmissionRepository;
import com.app.form_management_service.service.EmailExistsException;
import com.app.form_management_service.service.SubmissionService;

import jakarta.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;



@RestController
@RequestMapping("/api/submissions")
public class SubmissionController {

    @Autowired
    private SubmissionService submissionService;

    @GetMapping
    public List<SubmissionDTO> getAllSubmissions() {
        return submissionService.getAllSubmissions();
        
    }

    @GetMapping("/{id}")
    public SubmissionDTO getSubmissionById(@PathVariable Long id) {
        return submissionService.getSubmissionById(id);
    }
    
    @PostMapping
    public ResponseEntity<?> createSubmission(@RequestBody Submission submission) {
        try{
                
                submissionService.createSubmission(submission);
                return ResponseEntity.ok(submission);
        }
        catch(EmailExistsException e){
            return ResponseEntity.status(409).body(e.getMessage());
        }
        
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> updateSubmission(@PathVariable Long id, @RequestBody SubmissionDTO submissionDTO) {
        
            SubmissionDTO updatedSubmission = submissionService.updateSubmission(id,submissionDTO);

            if(updatedSubmission != null){
                return ResponseEntity.ok(updatedSubmission);
            }
            return ResponseEntity.status(404).body(null);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteSubission(@PathVariable Long id){
        boolean isDeleted = submissionService.deleteSubission(id);
        if(isDeleted){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
    

}