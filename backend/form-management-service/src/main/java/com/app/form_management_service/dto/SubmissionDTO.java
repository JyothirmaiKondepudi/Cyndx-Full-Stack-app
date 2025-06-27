package com.app.form_management_service.dto;


import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class SubmissionDTO {

    private Long id;
    private String fullName;
    private String email;
    private String phoneNumber;
    private int age;
    private String address;
    private String preferredContact;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
}
