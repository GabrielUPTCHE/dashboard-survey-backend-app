package com.survey.springboot.pwa.app.springboot_survey_app.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.survey.springboot.pwa.app.springboot_survey_app.model.dto.ApiResponse;
import com.survey.springboot.pwa.app.springboot_survey_app.model.dto.LegalDocumentDTO;
import com.survey.springboot.pwa.app.springboot_survey_app.service.useCase.LegalDocumentService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/legal-documents")
@RequiredArgsConstructor
@Tag(name = "Documentos Legales", description = "API para la gestión de Documentos Legales")
public class LegalDocumentController {

    private final LegalDocumentService service;

    @PostMapping
    @Operation(summary = "Crear un nuevo documento legal")
    public ResponseEntity<ApiResponse<LegalDocumentDTO>> create(@RequestBody LegalDocumentDTO dto) {
        LegalDocumentDTO created = service.create(dto);
        return new ResponseEntity<>(ApiResponse.<LegalDocumentDTO>builder()
                .success(true).message("Documento legal creado con éxito").data(created).build(), HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(summary = "Obtener todos los documentos legales")
    public ResponseEntity<ApiResponse<List<LegalDocumentDTO>>> getAll() {
        List<LegalDocumentDTO> list = service.getAll();
        return new ResponseEntity<>(ApiResponse.<List<LegalDocumentDTO>>builder()
                .success(true).message("Lista de documentos legales obtenida").data(list).build(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener un documento legal por ID")
    public ResponseEntity<ApiResponse<LegalDocumentDTO>> getById(@PathVariable String id) {
        LegalDocumentDTO dto = service.getById(id);
        return new ResponseEntity<>(ApiResponse.<LegalDocumentDTO>builder()
                .success(true).message("Documento legal encontrado").data(dto).build(), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar un documento legal existente")
    public ResponseEntity<ApiResponse<LegalDocumentDTO>> update(@PathVariable String id, @RequestBody LegalDocumentDTO dto) {
        LegalDocumentDTO updated = service.update(id, dto);
        return new ResponseEntity<>(ApiResponse.<LegalDocumentDTO>builder()
                .success(true).message("Documento legal actualizado con éxito").data(updated).build(), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un documento legal")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable String id) {
        service.delete(id);
        return new ResponseEntity<>(ApiResponse.<Void>builder()
                .success(true).message("Documento legal eliminado con éxito").build(), HttpStatus.OK);
    }
}