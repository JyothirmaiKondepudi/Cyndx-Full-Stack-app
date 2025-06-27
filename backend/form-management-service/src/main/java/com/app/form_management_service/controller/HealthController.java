package com.app.form_management_service.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class HealthController {

    @GetMapping("api/health")
    public ResponseEntity<String> getHealth() {
        return ResponseEntity.ok("ok");
    }
    
    
}
