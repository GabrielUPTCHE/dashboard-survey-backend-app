package com.survey.springboot.pwa.app.springboot_survey_app.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.survey.springboot.pwa.app.springboot_survey_app.model.dto.ApiResponse;
import com.survey.springboot.pwa.app.springboot_survey_app.model.dto.DirectorateDTO;
import com.survey.springboot.pwa.app.springboot_survey_app.service.useCase.DirectorateService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/directorates")
@RequiredArgsConstructor
@Tag(name = "Direcciones", description = "API para la gestión de las Direcciones")
public class DirectorateController {

    private final DirectorateService service;

    @PostMapping
    @Operation(summary = "Crear una nueva dirección")
    public ResponseEntity<ApiResponse<DirectorateDTO>> create(@RequestBody DirectorateDTO dto) {
        DirectorateDTO created = service.create(dto);
        return new ResponseEntity<>(ApiResponse.<DirectorateDTO>builder()
                .success(true).message("Dirección creada con éxito").data(created).build(), HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(summary = "Obtener todas las direcciones")
    public ResponseEntity<ApiResponse<List<DirectorateDTO>>> getAll() {
        List<DirectorateDTO> list = service.getAll();
        return new ResponseEntity<>(ApiResponse.<List<DirectorateDTO>>builder()
                .success(true).message("Lista de direcciones obtenida").data(list).build(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener una dirección por ID")
    public ResponseEntity<ApiResponse<DirectorateDTO>> getById(@PathVariable Long id) {
        DirectorateDTO dto = service.getById(id);
        return new ResponseEntity<>(ApiResponse.<DirectorateDTO>builder()
                .success(true).message("Dirección encontrada").data(dto).build(), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar una dirección existente")
    public ResponseEntity<ApiResponse<DirectorateDTO>> update(@PathVariable Long id, @RequestBody DirectorateDTO dto) {
        DirectorateDTO updated = service.update(id, dto);
        return new ResponseEntity<>(ApiResponse.<DirectorateDTO>builder()
                .success(true).message("Dirección actualizada con éxito").data(updated).build(), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar una dirección")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        service.delete(id);
        return new ResponseEntity<>(ApiResponse.<Void>builder()
                .success(true).message("Dirección eliminada con éxito").build(), HttpStatus.OK);
    }
}