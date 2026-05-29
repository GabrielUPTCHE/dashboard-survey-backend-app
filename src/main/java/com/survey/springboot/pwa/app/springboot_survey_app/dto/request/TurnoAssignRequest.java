package com.survey.springboot.pwa.app.springboot_survey_app.dto.request;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class TurnoAssignRequest {
    private String encuestadorId;
    private LocalDate fecha;
    private LocalTime horaInicio;
    private LocalTime horaFin;
}
