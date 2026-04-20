package com.survey.springboot.pwa.app.springboot_survey_app.service.useCase;

import java.util.List;

import com.survey.springboot.pwa.app.springboot_survey_app.model.dto.ShiftScheduleDTO;

public interface ShiftScheduleService {
    ShiftScheduleDTO create(ShiftScheduleDTO dto);
    ShiftScheduleDTO getById(Long id);
    List<ShiftScheduleDTO> getAll();
    ShiftScheduleDTO update(Long id, ShiftScheduleDTO dto);
    void delete(Long id);
}
