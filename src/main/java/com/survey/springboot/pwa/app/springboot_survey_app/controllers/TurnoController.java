package com.survey.springboot.pwa.app.springboot_survey_app.controllers;

import com.survey.springboot.pwa.app.springboot_survey_app.dto.request.TurnoAssignRequest;
import com.survey.springboot.pwa.app.springboot_survey_app.dto.request.TurnoCreateRequest;
import com.survey.springboot.pwa.app.springboot_survey_app.model.dto.ApiResponse;
import com.survey.springboot.pwa.app.springboot_survey_app.services.turnoServices.TurnoService;

import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/turnos")
@RequiredArgsConstructor
public class TurnoController {

    private final TurnoService turnoService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<Map<String, Object>>>> getTurnos(
            @RequestParam(value = "fecha", required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate fecha) {
        return ResponseEntity.ok(ApiResponse.<List<Map<String, Object>>>builder()
                .success(true).message("Lista de turnos obtenida").data(turnoService.getTurnos(fecha)).build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Map<String, Object>>> assign(
            @PathVariable Long id, @RequestBody TurnoAssignRequest req) {
        return ResponseEntity.ok(ApiResponse.<Map<String, Object>>builder()
                .success(true).message("Turno asignado con éxito").data(turnoService.assign(id, req)).build());
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Map<String, Object>>> create(@RequestBody TurnoCreateRequest req) {
        return new ResponseEntity<>(ApiResponse.<Map<String, Object>>builder()
                .success(true).message("Turno creado con éxito").data(turnoService.create(req)).build(),
                HttpStatus.CREATED);
    }
}
