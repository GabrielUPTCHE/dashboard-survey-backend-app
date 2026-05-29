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
public class LegalDocumentDTO {
    private String id;
    private String documentTypeId; 
    private Long subjectId;        
    private String status;
    private LocalDate expirationDate;
    private String evidenceUrl;
}