package com.survey.springboot.pwa.app.springboot_survey_app.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.survey.springboot.pwa.app.springboot_survey_app.model.dto.ApiResponse;
import com.survey.springboot.pwa.app.springboot_survey_app.model.dto.ExpedientDTO;
import com.survey.springboot.pwa.app.springboot_survey_app.service.useCase.ExpedientService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/expedients")
@RequiredArgsConstructor
@Tag(name = "Expedientes", description = "API para la gestión de Expedientes")
public class ExpedientController {

    private final ExpedientService service;

    @PostMapping
    @Operation(summary = "Crear un nuevo expediente")
    public ResponseEntity<ApiResponse<ExpedientDTO>> create(@RequestBody ExpedientDTO dto) {
        ExpedientDTO created = service.create(dto);
        return new ResponseEntity<>(ApiResponse.<ExpedientDTO>builder()
                .success(true).message("Expediente creado con éxito").data(created).build(), HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(summary = "Obtener todos los expedientes")
    public ResponseEntity<ApiResponse<List<ExpedientDTO>>> getAll() {
        List<ExpedientDTO> list = service.getAll();
        return new ResponseEntity<>(ApiResponse.<List<ExpedientDTO>>builder()
                .success(true).message("Lista de expedientes obtenida").data(list).build(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener un expediente por ID")
    public ResponseEntity<ApiResponse<ExpedientDTO>> getById(@PathVariable Long id) {
        ExpedientDTO dto = service.getById(id);
        return new ResponseEntity<>(ApiResponse.<ExpedientDTO>builder()
                .success(true).message("Expediente encontrado").data(dto).build(), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar un expediente existente")
    public ResponseEntity<ApiResponse<ExpedientDTO>> update(@PathVariable Long id, @RequestBody ExpedientDTO dto) {
        ExpedientDTO updated = service.update(id, dto);
        return new ResponseEntity<>(ApiResponse.<ExpedientDTO>builder()
                .success(true).message("Expediente actualizado con éxito").data(updated).build(), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un expediente")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        service.delete(id);
        return new ResponseEntity<>(ApiResponse.<Void>builder()
                .success(true).message("Expediente eliminado con éxito").build(), HttpStatus.OK);
    }
}