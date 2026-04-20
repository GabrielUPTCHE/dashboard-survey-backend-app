package com.survey.springboot.pwa.app.springboot_survey_app.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.survey.springboot.pwa.app.springboot_survey_app.model.dto.ApiResponse;
import com.survey.springboot.pwa.app.springboot_survey_app.model.dto.ProgramByUserDTO;
import com.survey.springboot.pwa.app.springboot_survey_app.service.useCase.ProgramByUserService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/program-by-users")
@RequiredArgsConstructor
@Tag(name = "Programas por Usuario", description = "API para la asignación de Programas a Usuarios")
public class ProgramByUserController {

    private final ProgramByUserService service;

    @PostMapping
    @Operation(summary = "Asignar un programa a un usuario")
    public ResponseEntity<ApiResponse<ProgramByUserDTO>> create(@RequestBody ProgramByUserDTO dto) {
        ProgramByUserDTO created = service.create(dto);
        return new ResponseEntity<>(ApiResponse.<ProgramByUserDTO>builder()
                .success(true).message("Asignación creada con éxito").data(created).build(), HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(summary = "Obtener todas las asignaciones")
    public ResponseEntity<ApiResponse<List<ProgramByUserDTO>>> getAll() {
        List<ProgramByUserDTO> list = service.getAll();
        return new ResponseEntity<>(ApiResponse.<List<ProgramByUserDTO>>builder()
                .success(true).message("Lista de asignaciones obtenida").data(list).build(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener una asignación por ID")
    public ResponseEntity<ApiResponse<ProgramByUserDTO>> getById(@PathVariable Long id) {
        ProgramByUserDTO dto = service.getById(id);
        return new ResponseEntity<>(ApiResponse.<ProgramByUserDTO>builder()
                .success(true).message("Asignación encontrada").data(dto).build(), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar una asignación existente")
    public ResponseEntity<ApiResponse<ProgramByUserDTO>> update(@PathVariable Long id, @RequestBody ProgramByUserDTO dto) {
        ProgramByUserDTO updated = service.update(id, dto);
        return new ResponseEntity<>(ApiResponse.<ProgramByUserDTO>builder()
                .success(true).message("Asignación actualizada con éxito").data(updated).build(), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar una asignación")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        service.delete(id);
        return new ResponseEntity<>(ApiResponse.<Void>builder()
                .success(true).message("Asignación eliminada con éxito").build(), HttpStatus.OK);
    }
}