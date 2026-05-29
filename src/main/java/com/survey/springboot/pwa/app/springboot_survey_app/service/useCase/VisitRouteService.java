package com.survey.springboot.pwa.app.springboot_survey_app.service.useCase;

import com.survey.springboot.pwa.app.springboot_survey_app.model.dto.VisitRouteDTO;
import com.survey.springboot.pwa.app.springboot_survey_app.model.dto.VisitRouteDetailDTO;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface VisitRouteService {
    VisitRouteDTO create(VisitRouteDTO dto);
    VisitRouteDTO getById(Long id);
    List<VisitRouteDTO> getAll();
    VisitRouteDTO update(Long id, VisitRouteDTO dto);
    void delete(Long id);

    List<VisitRouteDetailDTO> getAllDetailed(Optional<LocalDate> date);
}
