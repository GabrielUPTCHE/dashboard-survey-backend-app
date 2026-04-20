package com.survey.springboot.pwa.app.springboot_survey_app.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.survey.springboot.pwa.app.springboot_survey_app.model.dto.ApiResponse;
import com.survey.springboot.pwa.app.springboot_survey_app.model.dto.DocumentTypeDTO;
import com.survey.springboot.pwa.app.springboot_survey_app.service.useCase.DocumentTypeService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/document-types")
@RequiredArgsConstructor
@Tag(name = "Tipos de Documento", description = "API para la gestión de Tipos de Documento")
public class DocumentTypeController {

    private final DocumentTypeService service;

    @PostMapping
    @Operation(summary = "Crear un nuevo tipo de documento")
    public ResponseEntity<ApiResponse<DocumentTypeDTO>> create(@RequestBody DocumentTypeDTO dto) {
        DocumentTypeDTO created = service.create(dto);
        return new ResponseEntity<>(ApiResponse.<DocumentTypeDTO>builder()
                .success(true).message("Tipo de documento creado con éxito").data(created).build(), HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(summary = "Obtener todos los tipos de documento")
    public ResponseEntity<ApiResponse<List<DocumentTypeDTO>>> getAll() {
        List<DocumentTypeDTO> list = service.getAll();
        return new ResponseEntity<>(ApiResponse.<List<DocumentTypeDTO>>builder()
                .success(true).message("Lista de tipos de documento obtenida").data(list).build(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener un tipo de documento por ID")
    public ResponseEntity<ApiResponse<DocumentTypeDTO>> getById(@PathVariable String id) {
        DocumentTypeDTO dto = service.getById(id);
        return new ResponseEntity<>(ApiResponse.<DocumentTypeDTO>builder()
                .success(true).message("Tipo de documento encontrado").data(dto).build(), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar un tipo de documento existente")
    public ResponseEntity<ApiResponse<DocumentTypeDTO>> update(@PathVariable String id, @RequestBody DocumentTypeDTO dto) {
        DocumentTypeDTO updated = service.update(id, dto);
        return new ResponseEntity<>(ApiResponse.<DocumentTypeDTO>builder()
                .success(true).message("Tipo de documento actualizado con éxito").data(updated).build(), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un tipo de documento")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable String id) {
        service.delete(id);
        return new ResponseEntity<>(ApiResponse.<Void>builder()
                .success(true).message("Tipo de documento eliminado con éxito").build(), HttpStatus.OK);
    }
}