package com.survey.springboot.pwa.app.springboot_survey_app.service.useCase;
import java.util.List;

import com.survey.springboot.pwa.app.springboot_survey_app.model.dto.ProgramByUserDTO;

public interface ProgramByUserService {
    ProgramByUserDTO create(ProgramByUserDTO dto);
    ProgramByUserDTO getById(Long id);
    List<ProgramByUserDTO> getAll();
    ProgramByUserDTO update(Long id, ProgramByUserDTO dto);
    void delete(Long id);
}