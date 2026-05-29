package com.survey.springboot.pwa.app.springboot_survey_app.controllers;

import com.survey.springboot.pwa.app.springboot_survey_app.dto.request.CreateUserRequest;
import com.survey.springboot.pwa.app.springboot_survey_app.dto.request.UpdateUserRequest;
import com.survey.springboot.pwa.app.springboot_survey_app.model.dto.ApiResponse;
import com.survey.springboot.pwa.app.springboot_survey_app.model.dto.UserResponse;
import com.survey.springboot.pwa.app.springboot_survey_app.persistence.models.user.UserEntity;
import com.survey.springboot.pwa.app.springboot_survey_app.services.userServices.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
@Tag(name = "Usuarios", description = "Gestión de usuarios del sistema")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Listar usuarios (filtro opcional ?role=ADMIN|ASSISTANT|SURVEYOR)")
    ResponseEntity<ApiResponse<List<UserResponse>>> getUsers(
            @RequestParam Optional<String> role) {
        List<UserResponse> users = userService.getUsers(role);
        return ResponseEntity.ok(ApiResponse.<List<UserResponse>>builder()
                .success(true)
                .message("Lista de usuarios obtenida")
                .data(users)
                .build());
    }

    @PostMapping("/create")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Crear un nuevo usuario")
    ResponseEntity<ApiResponse<UserResponse>> createUser(@RequestBody CreateUserRequest requestData) {
        try {
            UserEntity user = userService.createUser(requestData);
            UserResponse response = userService.toResponse(user);
            return new ResponseEntity<>(ApiResponse.<UserResponse>builder()
                    .success(true)
                    .message("Usuario creado con éxito")
                    .data(response)
                    .build(), HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(ApiResponse.<UserResponse>builder()
                    .success(false)
                    .message(e.getMessage())
                    .build());
        }
    }

    @PutMapping("/{numberIdentification}")
    @PreAuthorize("hasRole('ADMIN')")
    @Operation(summary = "Actualizar nombre, apellido, rol o estado de un usuario")
    ResponseEntity<ApiResponse<UserResponse>> updateUser(
            @PathVariable String numberIdentification,
            @RequestBody UpdateUserRequest request) {
        try {
            UserResponse updated = userService.updateUser(numberIdentification, request);
            return ResponseEntity.ok(ApiResponse.<UserResponse>builder()
                    .success(true)
                    .message("Usuario actualizado con éxito")
                    .data(updated)
                    .build());
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(ApiResponse.<UserResponse>builder()
                    .success(false)
                    .message(e.getMessage())
                    .build());
        }
    }
}
