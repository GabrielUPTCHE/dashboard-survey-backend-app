package com.survey.springboot.pwa.app.springboot_survey_app.service.useCase;

import java.util.List;

import com.survey.springboot.pwa.app.springboot_survey_app.model.dto.ProgramDTO;

public interface ProgramService {
    ProgramDTO create(ProgramDTO dto);
    ProgramDTO getById(Long id);
    List<ProgramDTO> getAll();
    ProgramDTO update(Long id, ProgramDTO dto);
    void delete(Long id);
}
