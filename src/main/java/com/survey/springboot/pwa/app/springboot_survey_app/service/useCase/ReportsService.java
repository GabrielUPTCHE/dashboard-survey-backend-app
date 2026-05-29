package com.survey.springboot.pwa.app.springboot_survey_app.service.useCase;

import com.survey.springboot.pwa.app.springboot_survey_app.model.dto.VisitRouteDetailDTO;

import java.util.List;
import java.util.Map;

public interface ReportsService {
    Map<String, Object> getSummary();
    List<Map<String, Object>> getByDay();
    Map<String, Object> getByStatus();
    List<VisitRouteDetailDTO> getRecentVisits(int limit);
}
