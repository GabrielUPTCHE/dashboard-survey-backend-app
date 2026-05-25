package com.survey.springboot.pwa.app.springboot_survey_app.controllers;

import com.survey.springboot.pwa.app.springboot_survey_app.dto.request.LoginRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = {"http://localhost:5174", "http://localhost:5173"})
@RequestMapping
public class LoginController {

    /**
     * This endpoint is intercepted by JwtAuthenticationFilter
     * Credentials should be sent as JSON in the request body
     * On successful authentication, JWT token will be returned in response header
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest) {
        // This will be handled by JwtAuthenticationFilter
        // The method itself won't be called during normal JWT flow
        return ResponseEntity.ok().build();
    }
}
