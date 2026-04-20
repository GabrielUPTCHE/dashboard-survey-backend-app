package com.survey.springboot.pwa.app.springboot_survey_app.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.survey.springboot.pwa.app.springboot_survey_app.model.dto.ApiResponse;
import com.survey.springboot.pwa.app.springboot_survey_app.model.dto.ProgramDTO;
import com.survey.springboot.pwa.app.springboot_survey_app.service.useCase.ProgramService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/programs")
@RequiredArgsConstructor
@Tag(name = "Programas", description = "API para la gestión de los Programas")
public class ProgramController {

    private final ProgramService service;

    @PostMapping
    @Operation(summary = "Crear un nuevo programa")
    public ResponseEntity<ApiResponse<ProgramDTO>> create(@RequestBody ProgramDTO dto) {
        ProgramDTO created = service.create(dto);
        return new ResponseEntity<>(ApiResponse.<ProgramDTO>builder()
                .success(true).message("Programa creado con éxito").data(created).build(), HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(summary = "Obtener todos los programas")
    public ResponseEntity<ApiResponse<List<ProgramDTO>>> getAll() {
        List<ProgramDTO> list = service.getAll();
        return new ResponseEntity<>(ApiResponse.<List<ProgramDTO>>builder()
                .success(true).message("Lista de programas obtenida").data(list).build(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener un programa por ID")
    public ResponseEntity<ApiResponse<ProgramDTO>> getById(@PathVariable Long id) {
        ProgramDTO dto = service.getById(id);
        return new ResponseEntity<>(ApiResponse.<ProgramDTO>builder()
                .success(true).message("Programa encontrado").data(dto).build(), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar un programa existente")
    public ResponseEntity<ApiResponse<ProgramDTO>> update(@PathVariable Long id, @RequestBody ProgramDTO dto) {
        ProgramDTO updated = service.update(id, dto);
        return new ResponseEntity<>(ApiResponse.<ProgramDTO>builder()
                .success(true).message("Programa actualizado con éxito").data(updated).build(), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un programa")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        service.delete(id);
        return new ResponseEntity<>(ApiResponse.<Void>builder()
                .success(true).message("Programa eliminado con éxito").build(), HttpStatus.OK);
    }
}