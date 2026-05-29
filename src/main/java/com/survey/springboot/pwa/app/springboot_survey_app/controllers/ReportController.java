package com.survey.springboot.pwa.app.springboot_survey_app.controllers;

import com.survey.springboot.pwa.app.springboot_survey_app.model.dto.ApiResponse;
import com.survey.springboot.pwa.app.springboot_survey_app.services.reportServices.ReportService;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/reportes")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;

    @GetMapping("/resumen")
    public ResponseEntity<ApiResponse<Map<String, Object>>> resumen() {
        return ResponseEntity.ok(ApiResponse.<Map<String, Object>>builder()
                .success(true).message("Resumen de reportes").data(reportService.resumen()).build());
    }

    @GetMapping("/por-dia")
    public ResponseEntity<ApiResponse<List<Map<String, Object>>>> porDia() {
        return ResponseEntity.ok(ApiResponse.<List<Map<String, Object>>>builder()
                .success(true).message("Encuestas por día").data(reportService.porDia()).build());
    }

    @GetMapping("/por-estado")
    public ResponseEntity<ApiResponse<Map<String, Object>>> porEstado() {
        return ResponseEntity.ok(ApiResponse.<Map<String, Object>>builder()
                .success(true).message("Encuestas por estado").data(reportService.porEstado()).build());
    }

    @GetMapping("/recientes")
    public ResponseEntity<ApiResponse<List<Map<String, Object>>>> recientes(
            @RequestParam(value = "limit", defaultValue = "10") int limit) {
        return ResponseEntity.ok(ApiResponse.<List<Map<String, Object>>>builder()
                .success(true).message("Encuestas recientes").data(reportService.recientes(limit)).build());
    }
}
