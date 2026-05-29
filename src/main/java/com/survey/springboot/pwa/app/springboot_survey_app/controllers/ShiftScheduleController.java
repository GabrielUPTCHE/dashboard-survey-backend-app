package com.survey.springboot.pwa.app.springboot_survey_app.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.survey.springboot.pwa.app.springboot_survey_app.model.dto.ApiResponse;
import com.survey.springboot.pwa.app.springboot_survey_app.model.dto.ShiftScheduleDTO;
import com.survey.springboot.pwa.app.springboot_survey_app.service.useCase.ShiftScheduleService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/shift-schedules")
@RequiredArgsConstructor
@Tag(name = "Programación de Turnos", description = "API para la gestión de turnos de usuarios")
public class ShiftScheduleController {

    private final ShiftScheduleService service;

    @PostMapping
    @Operation(summary = "Crear un nuevo turno")
    public ResponseEntity<ApiResponse<ShiftScheduleDTO>> create(@RequestBody ShiftScheduleDTO dto) {
        ShiftScheduleDTO created = service.create(dto);
        return new ResponseEntity<>(ApiResponse.<ShiftScheduleDTO>builder()
                .success(true).message("Turno creado con éxito").data(created).build(), HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(summary = "Obtener todos los turnos")
    public ResponseEntity<ApiResponse<List<ShiftScheduleDTO>>> getAll() {
        List<ShiftScheduleDTO> list = service.getAll();
        return new ResponseEntity<>(ApiResponse.<List<ShiftScheduleDTO>>builder()
                .success(true).message("Lista de turnos obtenida").data(list).build(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener un turno por ID")
    public ResponseEntity<ApiResponse<ShiftScheduleDTO>> getById(@PathVariable Long id) {
        ShiftScheduleDTO dto = service.getById(id);
        return new ResponseEntity<>(ApiResponse.<ShiftScheduleDTO>builder()
                .success(true).message("Turno encontrado").data(dto).build(), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar un turno existente")
    public ResponseEntity<ApiResponse<ShiftScheduleDTO>> update(@PathVariable Long id, @RequestBody ShiftScheduleDTO dto) {
        ShiftScheduleDTO updated = service.update(id, dto);
        return new ResponseEntity<>(ApiResponse.<ShiftScheduleDTO>builder()
                .success(true).message("Turno actualizado con éxito").data(updated).build(), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un turno")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        service.delete(id);
        return new ResponseEntity<>(ApiResponse.<Void>builder()
                .success(true).message("Turno eliminado con éxito").build(), HttpStatus.OK);
    }
}