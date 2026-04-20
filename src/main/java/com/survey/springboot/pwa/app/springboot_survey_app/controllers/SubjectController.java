package com.survey.springboot.pwa.app.springboot_survey_app.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.survey.springboot.pwa.app.springboot_survey_app.model.dto.ApiResponse;
import com.survey.springboot.pwa.app.springboot_survey_app.model.dto.SubjectDTO;
import com.survey.springboot.pwa.app.springboot_survey_app.service.useCase.SubjectService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/subjects")
@RequiredArgsConstructor
@Tag(name = "Sujetos", description = "API para la gestión de Sujetos")
public class SubjectController {

    private final SubjectService service;

    @PostMapping
    @Operation(summary = "Crear un nuevo sujeto")
    public ResponseEntity<ApiResponse<SubjectDTO>> create(@RequestBody SubjectDTO dto) {
        SubjectDTO created = service.create(dto);
        return new ResponseEntity<>(ApiResponse.<SubjectDTO>builder()
                .success(true).message("Sujeto creado con éxito").data(created).build(), HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(summary = "Obtener todos los sujetos")
    public ResponseEntity<ApiResponse<List<SubjectDTO>>> getAll() {
        List<SubjectDTO> list = service.getAll();
        return new ResponseEntity<>(ApiResponse.<List<SubjectDTO>>builder()
                .success(true).message("Lista de sujetos obtenida").data(list).build(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener un sujeto por ID")
    public ResponseEntity<ApiResponse<SubjectDTO>> getById(@PathVariable Long id) {
        SubjectDTO dto = service.getById(id);
        return new ResponseEntity<>(ApiResponse.<SubjectDTO>builder()
                .success(true).message("Sujeto encontrado").data(dto).build(), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar un sujeto existente")
    public ResponseEntity<ApiResponse<SubjectDTO>> update(@PathVariable Long id, @RequestBody SubjectDTO dto) {
        SubjectDTO updated = service.update(id, dto);
        return new ResponseEntity<>(ApiResponse.<SubjectDTO>builder()
                .success(true).message("Sujeto actualizado con éxito").data(updated).build(), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un sujeto")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        service.delete(id);
        return new ResponseEntity<>(ApiResponse.<Void>builder()
                .success(true).message("Sujeto eliminado con éxito").build(), HttpStatus.OK);
    }
}
