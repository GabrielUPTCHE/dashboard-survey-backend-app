package com.survey.springboot.pwa.app.springboot_survey_app.controllers;

import com.survey.springboot.pwa.app.springboot_survey_app.model.dto.ApiResponse;
import com.survey.springboot.pwa.app.springboot_survey_app.model.dto.VisitRouteDTO;
import com.survey.springboot.pwa.app.springboot_survey_app.model.dto.VisitRouteDetailDTO;
import com.survey.springboot.pwa.app.springboot_survey_app.service.useCase.VisitRouteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/visit-routes")
@RequiredArgsConstructor
@Tag(name = "Rutas de Visitas", description = "API para la gestión de rutas y visitas programadas")
public class VisitRouteController {

    private final VisitRouteService service;

    @GetMapping
    @Operation(summary = "Listar rutas de visita (con detalle de sujeto/encuestador/turno). Filtrar por ?date=YYYY-MM-DD")
    ResponseEntity<ApiResponse<List<VisitRouteDetailDTO>>> getAll(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Optional<LocalDate> date) {
        List<VisitRouteDetailDTO> list = service.getAllDetailed(date);
        return ResponseEntity.ok(ApiResponse.<List<VisitRouteDetailDTO>>builder()
                .success(true).message("Lista de rutas obtenida").data(list).build());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener una ruta de visita por ID")
    ResponseEntity<ApiResponse<VisitRouteDTO>> getById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.<VisitRouteDTO>builder()
                .success(true).message("Ruta de visita encontrada").data(service.getById(id)).build());
    }

    @PostMapping
    @Operation(summary = "Crear una nueva ruta de visita")
    ResponseEntity<ApiResponse<VisitRouteDTO>> create(@RequestBody VisitRouteDTO dto) {
        return new ResponseEntity<>(ApiResponse.<VisitRouteDTO>builder()
                .success(true).message("Ruta de visita creada con éxito").data(service.create(dto)).build(),
                HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar una ruta de visita")
    ResponseEntity<ApiResponse<VisitRouteDTO>> update(@PathVariable Long id, @RequestBody VisitRouteDTO dto) {
        return ResponseEntity.ok(ApiResponse.<VisitRouteDTO>builder()
                .success(true).message("Ruta de visita actualizada con éxito").data(service.update(id, dto)).build());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar una ruta de visita")
    ResponseEntity<ApiResponse<Void>> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.ok(ApiResponse.<Void>builder()
                .success(true).message("Ruta de visita eliminada con éxito").build());
    }
}
