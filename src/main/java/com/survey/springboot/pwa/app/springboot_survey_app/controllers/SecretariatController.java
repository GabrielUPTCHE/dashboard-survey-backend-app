package com.survey.springboot.pwa.app.springboot_survey_app.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.survey.springboot.pwa.app.springboot_survey_app.model.dto.ApiResponse;
import com.survey.springboot.pwa.app.springboot_survey_app.model.dto.SecretariatDTO;
import com.survey.springboot.pwa.app.springboot_survey_app.service.useCase.SecretariatService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/secretariats")
@RequiredArgsConstructor
@Tag(name = "Secretarías", description = "API para la gestión de las Secretarías")
public class SecretariatController {

    private final SecretariatService service;

    @PostMapping
    @Operation(summary = "Crear una nueva secretaría")
    public ResponseEntity<ApiResponse<SecretariatDTO>> create(@RequestBody SecretariatDTO dto) {
        SecretariatDTO created = service.create(dto);
        ApiResponse<SecretariatDTO> response = ApiResponse.<SecretariatDTO>builder()
                .success(true)
                .message("Secretaría creada con éxito")
                .data(created)
                .build();
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(summary = "Obtener todas las secretarías")
    public ResponseEntity<ApiResponse<List<SecretariatDTO>>> getAll() {
        List<SecretariatDTO> list = service.getAll();
        ApiResponse<List<SecretariatDTO>> response = ApiResponse.<List<SecretariatDTO>>builder()
                .success(true)
                .message("Lista de secretarías obtenida")
                .data(list)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK); 
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener una secretaría por ID")
    public ResponseEntity<ApiResponse<SecretariatDTO>> getById(@PathVariable Long id) {
        SecretariatDTO dto = service.getById(id);
        ApiResponse<SecretariatDTO> response = ApiResponse.<SecretariatDTO>builder()
                .success(true)
                .message("Secretaría encontrada")
                .data(dto)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar una secretaría existente")
    public ResponseEntity<ApiResponse<SecretariatDTO>> update(@PathVariable Long id, @RequestBody SecretariatDTO dto) {
        SecretariatDTO updated = service.update(id, dto);
        ApiResponse<SecretariatDTO> response = ApiResponse.<SecretariatDTO>builder()
                .success(true)
                .message("Secretaría actualizada con éxito")
                .data(updated)
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar una secretaría")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        service.delete(id);
        ApiResponse<Void> response = ApiResponse.<Void>builder()
                .success(true)
                .message("Secretaría eliminada con éxito")
                .build();
        return new ResponseEntity<>(response, HttpStatus.OK); 
    }
}