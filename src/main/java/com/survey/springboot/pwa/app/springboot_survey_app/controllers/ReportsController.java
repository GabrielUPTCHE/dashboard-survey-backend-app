package com.survey.springboot.pwa.app.springboot_survey_app.controllers;

import com.survey.springboot.pwa.app.springboot_survey_app.model.dto.ApiResponse;
import com.survey.springboot.pwa.app.springboot_survey_app.model.dto.VisitRouteDetailDTO;
import com.survey.springboot.pwa.app.springboot_survey_app.service.useCase.ReportsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/reports")
@RequiredArgsConstructor
@Tag(name = "Reportes", description = "KPIs y agregaciones para el dashboard")
public class ReportsController {

    private final ReportsService reportsService;

    @GetMapping("/summary")
    @Operation(summary = "Resumen del mes: total encuestas, tasa de completadas, encuestadores activos hoy")
    ResponseEntity<ApiResponse<Map<String, Object>>> getSummary() {
        return ResponseEntity.ok(ApiResponse.<Map<String, Object>>builder()
                .success(true).message("Resumen obtenido").data(reportsService.getSummary()).build());
    }

    @GetMapping("/by-day")
    @Operation(summary = "Encuestas por día de la semana actual")
    ResponseEntity<ApiResponse<List<Map<String, Object>>>> getByDay() {
        return ResponseEntity.ok(ApiResponse.<List<Map<String, Object>>>builder()
                .success(true).message("Datos por día obtenidos").data(reportsService.getByDay()).build());
    }

    @GetMapping("/by-status")
    @Operation(summary = "Totales por estado: completadas, pendientes, sinAsignar, enProgreso")
    ResponseEntity<ApiResponse<Map<String, Object>>> getByStatus() {
        return ResponseEntity.ok(ApiResponse.<Map<String, Object>>builder()
                .success(true).message("Datos por estado obtenidos").data(reportsService.getByStatus()).build());
    }

    @GetMapping("/recent-visits")
    @Operation(summary = "Últimas visitas (máx 10 por defecto)")
    ResponseEntity<ApiResponse<List<VisitRouteDetailDTO>>> getRecentVisits(
            @RequestParam(defaultValue = "10") int limit) {
        return ResponseEntity.ok(ApiResponse.<List<VisitRouteDetailDTO>>builder()
                .success(true).message("Visitas recientes obtenidas").data(reportsService.getRecentVisits(limit)).build());
    }
}
