package com.app.form_management_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.form_management_service.model.Submission;
import java.util.List;


public interface SubmissionRepository extends JpaRepository<Submission,Long> {

    boolean existsByEmail(String email);
    
}
