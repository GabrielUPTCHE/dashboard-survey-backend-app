package com.survey.springboot.pwa.app.springboot_survey_app.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VisitRouteDTO {
    private Long id;
    private Long shiftScheduleId; // Referencia a Programacion Turnos
    private Long subjectId;       // Referencia a Sujetos
    private Long programId;       // Referencia a Programas
    private LocalDate scheduledDate;
    private String status;
}