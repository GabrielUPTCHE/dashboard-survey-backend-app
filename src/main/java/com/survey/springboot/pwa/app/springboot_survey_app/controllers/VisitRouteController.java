package com.survey.springboot.pwa.app.springboot_survey_app.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.survey.springboot.pwa.app.springboot_survey_app.model.dto.ApiResponse;
import com.survey.springboot.pwa.app.springboot_survey_app.model.dto.VisitRouteDTO;
import com.survey.springboot.pwa.app.springboot_survey_app.service.useCase.VisitRouteService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/visit-routes")
@RequiredArgsConstructor
@Tag(name = "Rutas de Visitas", description = "API para la gestión de rutas y visitas programadas")
public class VisitRouteController {

    private final VisitRouteService service;

    @PostMapping
    @Operation(summary = "Crear una nueva ruta de visita")
    public ResponseEntity<ApiResponse<VisitRouteDTO>> create(@RequestBody VisitRouteDTO dto) {
        VisitRouteDTO created = service.create(dto);
        return new ResponseEntity<>(ApiResponse.<VisitRouteDTO>builder()
                .success(true).message("Ruta de visita creada con éxito").data(created).build(), HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(summary = "Obtener todas las rutas de visita")
    public ResponseEntity<ApiResponse<List<VisitRouteDTO>>> getAll() {
        List<VisitRouteDTO> list = service.getAll();
        return new ResponseEntity<>(ApiResponse.<List<VisitRouteDTO>>builder()
                .success(true).message("Lista de rutas obtenida").data(list).build(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener una ruta de visita por ID")
    public ResponseEntity<ApiResponse<VisitRouteDTO>> getById(@PathVariable Long id) {
        VisitRouteDTO dto = service.getById(id);
        return new ResponseEntity<>(ApiResponse.<VisitRouteDTO>builder()
                .success(true).message("Ruta de visita encontrada").data(dto).build(), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar una ruta de visita existente")
    public ResponseEntity<ApiResponse<VisitRouteDTO>> update(@PathVariable Long id, @RequestBody VisitRouteDTO dto) {
        VisitRouteDTO updated = service.update(id, dto);
        return new ResponseEntity<>(ApiResponse.<VisitRouteDTO>builder()
                .success(true).message("Ruta de visita actualizada con éxito").data(updated).build(), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar una ruta de visita")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        service.delete(id);
        return new ResponseEntity<>(ApiResponse.<Void>builder()
                .success(true).message("Ruta de visita eliminada con éxito").build(), HttpStatus.OK);
    }
}