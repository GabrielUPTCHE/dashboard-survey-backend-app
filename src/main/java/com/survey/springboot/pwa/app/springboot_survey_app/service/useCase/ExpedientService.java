package com.survey.springboot.pwa.app.springboot_survey_app.service.useCase;

import java.util.List;

import com.survey.springboot.pwa.app.springboot_survey_app.model.dto.ExpedientDTO;

public interface ExpedientService {
    ExpedientDTO create(ExpedientDTO dto);
    ExpedientDTO getById(Long id);
    List<ExpedientDTO> getAll();
    ExpedientDTO update(Long id, ExpedientDTO dto);
    void delete(Long id);
}