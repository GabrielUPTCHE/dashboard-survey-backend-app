package com.survey.springboot.pwa.app.springboot_survey_app.service.useCase;

import java.util.List;

import com.survey.springboot.pwa.app.springboot_survey_app.model.dto.DirectorateDTO;

public interface DirectorateService {
    DirectorateDTO create(DirectorateDTO dto);
    DirectorateDTO getById(Long id);
    List<DirectorateDTO> getAll();
    DirectorateDTO update(Long id, DirectorateDTO dto);
    void delete(Long id);
}