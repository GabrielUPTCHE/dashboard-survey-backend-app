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
public class VisitRouteDetailDTO {

    private Long id;
    private String status;
    private LocalDate scheduledDate;

    private SubjectInfo subject;
    private SurveyorInfo surveyor;
    private ShiftInfo shift;
    private ProgramInfo program;

    @Data @Builder @NoArgsConstructor @AllArgsConstructor
    public static class SubjectInfo {
        private Long id;
        private String businessName;
        private String physicalAddress;
        private String neighborhood;
        private String zone;
    }

    @Data @Builder @NoArgsConstructor @AllArgsConstructor
    public static class SurveyorInfo {
        private String numberIdentification;
        private String name;
        private String lastName;
    }

    @Data @Builder @NoArgsConstructor @AllArgsConstructor
    public static class ShiftInfo {
        private Long id;
        private LocalDate date;
        private LocalTime startTime;
        private LocalTime endTime;
    }

    @Data @Builder @NoArgsConstructor @AllArgsConstructor
    public static class ProgramInfo {
        private Long id;
        private String name;
    }
}
