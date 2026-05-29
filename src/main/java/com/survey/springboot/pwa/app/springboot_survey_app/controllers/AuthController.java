package com.survey.springboot.pwa.app.springboot_survey_app.controllers;

import com.survey.springboot.pwa.app.springboot_survey_app.config.AppProperties;
import com.survey.springboot.pwa.app.springboot_survey_app.model.dto.ApiResponse;
import com.survey.springboot.pwa.app.springboot_survey_app.persistence.models.user.UserEntity;
import com.survey.springboot.pwa.app.springboot_survey_app.persistence.repository.UserRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "Autenticación", description = "Verificación de sesión y logout")
public class AuthController {

    private final UserRepository userRepository;
    private final AppProperties appProperties;

    @GetMapping("/verify")
    @Operation(summary = "Verifica la sesión actual mediante la cookie JWT")
    ResponseEntity<ApiResponse<Map<String, Object>>> verify(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(401).build();
        }

        String email = authentication.getName();
        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Map<String, Object> data = new HashMap<>();
        data.put("email", user.getEmail());
        data.put("name", user.getName());
        data.put("lastName", user.getLastName());
        data.put("numberIdentification", user.getNumberIdentification());
        data.put("role", user.getRole().getName().name());
        data.put("state", user.isState());

        return ResponseEntity.ok(ApiResponse.<Map<String, Object>>builder()
                .success(true)
                .message("Sesión válida")
                .data(data)
                .build());
    }

    @PostMapping("/logout")
    @Operation(summary = "Cierra la sesión limpiando la cookie JWT")
    ResponseEntity<ApiResponse<Void>> logout(HttpServletResponse response) {
        boolean secure = appProperties.getCookie().isSecure();
        Cookie cookie = new Cookie("token", "");
        cookie.setHttpOnly(true);
        cookie.setSecure(secure);
        cookie.setPath("/");
        cookie.setMaxAge(0);
        response.addCookie(cookie);

        return ResponseEntity.ok(ApiResponse.<Void>builder()
                .success(true)
                .message("Sesión cerrada")
                .build());
    }
}
