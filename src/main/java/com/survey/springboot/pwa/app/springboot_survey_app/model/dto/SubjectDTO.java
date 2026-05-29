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
public class SubjectDTO {
    private Long id;
    private String nit;
    private String businessName;
    private String legalRepresentation;
    private Double latitude;
    private Double longitude;
    private String physicalAddress;
    private String neighborhood;
    private String zone;
    private String censusStatus;
    private LocalDate createdAt;
}