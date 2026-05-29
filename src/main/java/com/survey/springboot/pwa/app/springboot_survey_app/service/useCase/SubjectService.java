package com.survey.springboot.pwa.app.springboot_survey_app.service.useCase;

import java.util.List;

import com.survey.springboot.pwa.app.springboot_survey_app.model.dto.SubjectDTO;

public interface SubjectService {
    SubjectDTO create(SubjectDTO subjectDTO);
    SubjectDTO getById(Long id);
    List<SubjectDTO> getAll();
    SubjectDTO update(Long id, SubjectDTO subjectDTO);
    void delete(Long id);
}
