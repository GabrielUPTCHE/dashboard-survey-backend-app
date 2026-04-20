package com.survey.springboot.pwa.app.springboot_survey_app.service.useCase;

import java.util.List;

import com.survey.springboot.pwa.app.springboot_survey_app.model.dto.LegalDocumentDTO;

public interface LegalDocumentService {
    LegalDocumentDTO create(LegalDocumentDTO dto);
    LegalDocumentDTO getById(String id);
    List<LegalDocumentDTO> getAll();
    LegalDocumentDTO update(String id, LegalDocumentDTO dto);
    void delete(String id);
}