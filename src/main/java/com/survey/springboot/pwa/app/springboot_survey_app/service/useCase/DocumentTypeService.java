package com.survey.springboot.pwa.app.springboot_survey_app.service.useCase;

import java.util.List;

import com.survey.springboot.pwa.app.springboot_survey_app.model.dto.DocumentTypeDTO;

public interface DocumentTypeService {
    DocumentTypeDTO create(DocumentTypeDTO dto);
    DocumentTypeDTO getById(String id);
    List<DocumentTypeDTO> getAll();
    DocumentTypeDTO update(String id, DocumentTypeDTO dto);
    void delete(String id);
}
