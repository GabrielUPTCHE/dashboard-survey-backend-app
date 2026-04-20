package com.survey.springboot.pwa.app.springboot_survey_app.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ShiftScheduleDTO {
    private Long id;
    private Long userId; // Referencia al Usuario
    private LocalDate date;
    private LocalTime startTime;
    private LocalTime endTime;
    private String activityType;
}