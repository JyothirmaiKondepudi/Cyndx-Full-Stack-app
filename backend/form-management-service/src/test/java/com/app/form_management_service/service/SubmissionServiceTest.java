package com.app.form_management_service.service;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.aspectj.lang.annotation.Before;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import com.app.form_management_service.dto.SubmissionDTO;
import com.app.form_management_service.model.Submission;
import com.app.form_management_service.repository.SubmissionRepository;

@ExtendWith(MockitoExtension.class)
public class SubmissionServiceTest {

    private Submission submission1;
    private Submission submission2;
    @Mock
    private SubmissionRepository repo;
    @InjectMocks
    private SubmissionService submissionService;

    @BeforeEach
    void init(){
        this.submission1 = Submission.builder()
                                      .fullName("Test Name1")
                                      .age(20)
                                      .address("Test Lane1, Test city1, test state")
                                      .email("test1@email.com")
                                      .phoneNumber("1234567890")
                                      .preferredContact("email")
                                      .createdAt(LocalDateTime.now())
                                      .updatedAt(LocalDateTime.now())
                                      .build();
        this.submission2 = Submission.builder()
                                      .fullName("Test Name2")
                                      .age(20)
                                      .address("Test Lane2, Test city2, test state")
                                      .email("test2@email.com")
                                      .phoneNumber("1234567899")
                                      .preferredContact("both")
                                      .createdAt(LocalDateTime.now())
                                      .updatedAt(LocalDateTime.now())
                                      .build();

    }
    @Test
    void testCreateSubmission() throws EmailExistsException{
        when(repo.save(Mockito.any(Submission.class))).thenReturn(submission1);
        Submission newSubmission = submissionService.createSubmission(submission1);

        Assertions.assertThat(newSubmission).isNotNull();
    }
    @Test
    void testGetAllSubmissions(){
        when(repo.findAll()).thenReturn(List.of(submission1,submission2));
        List<SubmissionDTO> submissions = submissionService.getAllSubmissions();

        Assertions.assertThat(submissions).isNotNull();

    }

    @Test
    void testGetSubmissionById(){
        when(repo.findById(1L)).thenReturn(Optional.of(submission1));
        SubmissionDTO sub = submissionService.getSubmissionById(1L);
        Assertions.assertThat(sub).isNotNull();

    }
    @Test
    void testUpdateSubmission(){
        when(repo.findById(1L)).thenReturn(Optional.of(submission1));
        when(repo.save(Mockito.any(Submission.class))).thenReturn(submission1);

        SubmissionDTO updateDto = new SubmissionDTO();
        updateDto.setAge(25);
        updateDto.setFullName(submission1.getFullName());
        updateDto.setEmail(submission1.getEmail());
        updateDto.setPhoneNumber(submission1.getPhoneNumber());
        updateDto.setAddress(submission1.getAddress());
        updateDto.setPreferredContact(submission1.getPreferredContact());

       
        SubmissionDTO updatedSubmission = submissionService.updateSubmission(new Long(1L),updateDto);
        Assertions.assertThat(updatedSubmission).isNotNull();
        Assertions.assertThat(updatedSubmission.getAge()).isEqualTo(25);
    }

    @Test
    void testDeleteSubmission(){
        when(repo.findById(2L)).thenReturn(Optional.of(submission2));
        boolean isDeleted = submissionService.deleteSubission(2L);

        Assertions.assertThat(isDeleted).isTrue();
        // verify(repo).deleteById(2L);

    }
    
    
}
