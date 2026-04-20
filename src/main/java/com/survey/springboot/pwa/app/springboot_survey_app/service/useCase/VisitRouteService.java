package com.survey.springboot.pwa.app.springboot_survey_app.service.useCase;

import java.util.List;

import com.survey.springboot.pwa.app.springboot_survey_app.model.dto.VisitRouteDTO;

public interface VisitRouteService {
    VisitRouteDTO create(VisitRouteDTO dto);
    VisitRouteDTO getById(Long id);
    List<VisitRouteDTO> getAll();
    VisitRouteDTO update(Long id, VisitRouteDTO dto);
    void delete(Long id);
}