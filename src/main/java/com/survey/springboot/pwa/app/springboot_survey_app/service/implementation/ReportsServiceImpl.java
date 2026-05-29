package com.survey.springboot.pwa.app.springboot_survey_app.service.implementation;

import com.survey.springboot.pwa.app.springboot_survey_app.model.dto.VisitRouteDetailDTO;
import com.survey.springboot.pwa.app.springboot_survey_app.persistence.models.entity.ShiftSchedule;
import com.survey.springboot.pwa.app.springboot_survey_app.persistence.models.entity.VisitRoute;
import com.survey.springboot.pwa.app.springboot_survey_app.persistence.repository.ShiftScheduleRepository;
import com.survey.springboot.pwa.app.springboot_survey_app.persistence.repository.VisitRouteRepository;
import com.survey.springboot.pwa.app.springboot_survey_app.service.useCase.ReportsService;
import com.survey.springboot.pwa.app.springboot_survey_app.service.useCase.VisitRouteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReportsServiceImpl implements ReportsService {

    private final VisitRouteRepository visitRouteRepository;
    private final ShiftScheduleRepository shiftScheduleRepository;
    private final VisitRouteService visitRouteService;

    @Override
    @Transactional(readOnly = true)
    public Map<String, Object> getSummary() {
        LocalDate now = LocalDate.now();
        LocalDate firstOfMonth = now.withDayOfMonth(1);
        LocalDate lastOfMonth = now.withDayOfMonth(now.lengthOfMonth());

        List<VisitRoute> monthRoutes = visitRouteRepository.findByScheduledDateBetween(firstOfMonth, lastOfMonth);
        long totalMonth = monthRoutes.size();
        long completadas = monthRoutes.stream().filter(r -> "Completada".equals(r.getStatus())).count();
        double completionRate = totalMonth > 0 ? Math.round((completadas * 100.0 / totalMonth) * 10.0) / 10.0 : 0.0;

        List<ShiftSchedule> todayShifts = shiftScheduleRepository.findByDate(now);
        long activeSurveyorsToday = todayShifts.stream()
                .filter(s -> s.getUser() != null)
                .map(s -> s.getUser().getNumberIdentification())
                .distinct()
                .count();

        Map<String, Object> result = new LinkedHashMap<>();
        result.put("totalMonth", totalMonth);
        result.put("completionRate", completionRate);
        result.put("activeSurveyorsToday", activeSurveyorsToday);
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Map<String, Object>> getByDay() {
        LocalDate today = LocalDate.now();
        // Lunes de la semana actual
        LocalDate monday = today.minusDays(today.getDayOfWeek().getValue() - 1L);
        LocalDate sunday = monday.plusDays(6);

        List<VisitRoute> weekRoutes = visitRouteRepository.findByScheduledDateBetween(monday, sunday);

        Map<LocalDate, Long> countByDate = weekRoutes.stream()
                .collect(Collectors.groupingBy(VisitRoute::getScheduledDate, Collectors.counting()));

        List<Map<String, Object>> result = new ArrayList<>();
        String[] labels = {"Lun", "Mar", "Mié", "Jue", "Vie", "Sáb", "Dom"};
        for (int i = 0; i < 7; i++) {
            LocalDate day = monday.plusDays(i);
            Map<String, Object> entry = new LinkedHashMap<>();
            entry.put("day", labels[i]);
            entry.put("count", countByDate.getOrDefault(day, 0L));
            result.add(entry);
        }
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public Map<String, Object> getByStatus() {
        long completadas = visitRouteRepository.countByStatus("Completada");
        long pendientes = visitRouteRepository.countByStatus("Pendiente");
        long sinAsignar = visitRouteRepository.countByStatus("Sin asignar");
        long enProgreso = visitRouteRepository.countByStatus("En Progreso");

        Map<String, Object> result = new LinkedHashMap<>();
        result.put("completadas", completadas);
        result.put("pendientes", pendientes);
        result.put("sinAsignar", sinAsignar);
        result.put("enProgreso", enProgreso);
        return result;
    }

    @Override
    @Transactional(readOnly = true)
    public List<VisitRouteDetailDTO> getRecentVisits(int limit) {
        List<VisitRoute> recent = visitRouteRepository.findTop10ByOrderByScheduledDateDesc();
        List<VisitRoute> limited = recent.subList(0, Math.min(limit, recent.size()));
        return limited.stream()
                .map(r -> visitRouteService.getAllDetailed(Optional.of(r.getScheduledDate()))
                        .stream()
                        .filter(d -> d.getId().equals(r.getId()))
                        .findFirst()
                        .orElse(null))
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }
}
