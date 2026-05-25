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
public class ProgramByUserDTO {
    private String id;
    private String programId; // Referencia al Programa
    private String userId;    // Referencia al Usuario
     private LocalDate startDate;
    private LocalDate endDate;
}