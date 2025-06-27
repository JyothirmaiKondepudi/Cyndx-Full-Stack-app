package com.app.form_management_service.service;

import java.util.*;
import java.util.stream.Collectors;

import com.app.form_management_service.dto.SubmissionDTO;
import com.app.form_management_service.model.Submission;
import com.app.form_management_service.repository.SubmissionRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class SubmissionService {

    @Autowired
    private SubmissionRepository submissionRepository;

    public List<SubmissionDTO> getAllSubmissions(){
        return submissionRepository.findAll().stream()
                                        .map(this::convertToDTO)
                                        .collect(Collectors.toList());
    }

    public SubmissionDTO convertToDTO(Submission submission){
        SubmissionDTO dto = new SubmissionDTO();
        dto.setId(submission.getId());
        dto.setAddress(submission.getAddress());
        dto.setCreatedAt(submission.getCreatedAt());
        dto.setUpdatedAt(submission.getUpdatedAt());
        dto.setAge(submission.getAge());
        dto.setEmail(submission.getEmail());
        dto.setFullName(submission.getFullName());
        dto.setPhoneNumber(submission.getPhoneNumber());
        dto.setPreferredContact(submission.getPreferredContact());

        return dto;
    }

    public SubmissionDTO getSubmissionById(Long id) {
        Submission sb = submissionRepository.findById(id).get();
        return convertToDTO(sb);
        
    }

    public Submission createSubmission(Submission submission) throws EmailExistsException {
        if(submissionRepository.existsByEmail(submission.getEmail())){
            throw new EmailExistsException("This email address already exists");

        }
        Submission newSubmission = new Submission();
        newSubmission.setAddress(submission.getAddress());
        newSubmission.setAge((submission.getAge()));
        newSubmission.setEmail(submission.getEmail());
        newSubmission.setFullName(submission.getFullName());
        newSubmission.setPhoneNumber(submission.getPhoneNumber());
        newSubmission.setPreferredContact(submission.getPreferredContact());
        newSubmission.setUpdatedAt(submission.getUpdatedAt());
        newSubmission.setCreatedAt(submission.getCreatedAt());

        return submissionRepository.save(newSubmission);

    }

    public SubmissionDTO updateSubmission(Long id, SubmissionDTO submissionDTO) {

                Optional<Submission> sb = submissionRepository.findById(id);
                if(sb.isPresent()){
                    Submission updatedSubmission = sb.get();
                    updatedSubmission.setAddress(submissionDTO.getAddress());
                    updatedSubmission.setAge(submissionDTO.getAge());
                    updatedSubmission.setEmail(submissionDTO.getEmail());
                    updatedSubmission.setFullName(submissionDTO.getFullName());
                    updatedSubmission.setPhoneNumber(submissionDTO.getPhoneNumber());
                    updatedSubmission.setPreferredContact(submissionDTO.getPreferredContact());
                    updatedSubmission.setCreatedAt(submissionDTO.getCreatedAt());
                    updatedSubmission.setUpdatedAt(submissionDTO.getUpdatedAt());
                    Submission saved = submissionRepository.save(updatedSubmission);
                    return convertToDTO(saved);
                }
                return null;
            }

    public boolean deleteSubission(Long id) {
        
        Optional<Submission> sb = submissionRepository.findById(id);
        if(sb!=null){
            submissionRepository.deleteById(id);
            return true;
        }
        return false;
    }
    
}
