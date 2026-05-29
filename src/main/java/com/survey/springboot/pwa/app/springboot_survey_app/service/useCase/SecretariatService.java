package com.survey.springboot.pwa.app.springboot_survey_app.service.useCase;

import java.util.List;

import com.survey.springboot.pwa.app.springboot_survey_app.model.dto.SecretariatDTO;

public interface SecretariatService {
    SecretariatDTO create(SecretariatDTO dto);
    SecretariatDTO getById(Long id);
    List<SecretariatDTO> getAll();
    SecretariatDTO update(Long id, SecretariatDTO dto);
    void delete(Long id);
}
