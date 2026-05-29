package com.survey.springboot.pwa.app.springboot_survey_app.services.reportServices;

import com.survey.springboot.pwa.app.springboot_survey_app.persistence.models.entity.ShiftSchedule;
import com.survey.springboot.pwa.app.springboot_survey_app.persistence.models.entity.Subject;
import com.survey.springboot.pwa.app.springboot_survey_app.persistence.models.entity.VisitRoute;
import com.survey.springboot.pwa.app.springboot_survey_app.persistence.models.user.UserEntity;
import com.survey.springboot.pwa.app.springboot_survey_app.persistence.repository.VisitRouteRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/** Reportes del dashboard, agregados sobre VisitRoute. */
@Service
@RequiredArgsConstructor
public class ReportService {

    private static final String COMPLETADA = "Completada";
    private static final String PENDIENTE = "Pendiente";
    private static final String EN_PROGRESO = "En Progreso";
    private static final String SIN_ASIGNAR = "Sin asignar";

    private final VisitRouteRepository visitRouteRepository;

    /** Normaliza el estado crudo + la asignación a las etiquetas de la UI. */
    private String estado(VisitRoute vr) {
        String raw = vr.getStatus() == null ? "" : vr.getStatus().trim().toLowerCase();
        if (raw.contains("complet")) return COMPLETADA;
        if (raw.contains("progres") || raw.contains("curso")) return EN_PROGRESO;
        if (raw.contains("sin") || vr.getShiftSchedule() == null) return SIN_ASIGNAR;
        return PENDIENTE;
    }

    @Transactional(readOnly = true)
    public Map<String, Object> resumen() {
        List<VisitRoute> all = visitRouteRepository.findAll();
        LocalDate today = LocalDate.now();

        long totalMes = all.stream()
                .filter(vr -> vr.getScheduledDate() != null
                        && vr.getScheduledDate().getMonthValue() == today.getMonthValue()
                        && vr.getScheduledDate().getYear() == today.getYear())
                .count();

        long completadas = all.stream()
                .filter(vr -> vr.getScheduledDate() != null
                        && vr.getScheduledDate().getMonthValue() == today.getMonthValue()
                        && vr.getScheduledDate().getYear() == today.getYear())
                .filter(vr -> COMPLETADA.equals(estado(vr)))
                .count();

        int tasaCompletadas = totalMes > 0 ? (int) Math.round(completadas * 100.0 / totalMes) : 0;

        Set<String> activos = new HashSet<>();
        for (VisitRoute vr : all) {
            ShiftSchedule ss = vr.getShiftSchedule();
            if (ss != null && today.equals(ss.getDate()) && ss.getUser() != null) {
                activos.add(ss.getUser().getNumberIdentification());
            }
        }

        LocalDate startThisWeek = today.with(DayOfWeek.MONDAY);
        LocalDate startLastWeek = startThisWeek.minusWeeks(1);
        long thisWeek = countBetween(all, startThisWeek, startThisWeek.plusDays(6));
        long lastWeek = countBetween(all, startLastWeek, startLastWeek.plusDays(6));
        int tendenciaSemana = lastWeek > 0
                ? (int) Math.round((thisWeek - lastWeek) * 100.0 / lastWeek)
                : (thisWeek > 0 ? 100 : 0);

        Map<String, Object> result = new LinkedHashMap<>();
        result.put("totalMes", totalMes);
        result.put("tasaCompletadas", tasaCompletadas);
        result.put("encuestadoresActivos", activos.size());
        result.put("tendenciaSemana", tendenciaSemana);
        return result;
    }

    private long countBetween(List<VisitRoute> all, LocalDate from, LocalDate to) {
        return all.stream()
                .filter(vr -> vr.getScheduledDate() != null
                        && !vr.getScheduledDate().isBefore(from)
                        && !vr.getScheduledDate().isAfter(to))
                .count();
    }

    @Transactional(readOnly = true)
    public List<Map<String, Object>> porDia() {
        List<VisitRoute> all = visitRouteRepository.findAll();
        LocalDate monday = LocalDate.now().with(DayOfWeek.MONDAY);
        String[] labels = {"Lun", "Mar", "Mié", "Jue", "Vie", "Sáb", "Dom"};
        List<Map<String, Object>> result = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            LocalDate day = monday.plusDays(i);
            long cantidad = all.stream()
                    .filter(vr -> day.equals(vr.getScheduledDate()))
                    .count();
            Map<String, Object> item = new LinkedHashMap<>();
            item.put("dia", labels[i]);
            item.put("cantidad", cantidad);
            result.add(item);
        }
        return result;
    }

    @Transactional(readOnly = true)
    public Map<String, Object> porEstado() {
        List<VisitRoute> all = visitRouteRepository.findAll();
        long completadas = all.stream().filter(vr -> COMPLETADA.equals(estado(vr))).count();
        long pendientes = all.stream().filter(vr -> {
            String e = estado(vr);
            return PENDIENTE.equals(e) || EN_PROGRESO.equals(e);
        }).count();
        long sinAsignar = all.stream().filter(vr -> SIN_ASIGNAR.equals(estado(vr))).count();

        Map<String, Object> result = new LinkedHashMap<>();
        result.put("completadas", completadas);
        result.put("pendientes", pendientes);
        result.put("sinAsignar", sinAsignar);
        return result;
    }

    @Transactional(readOnly = true)
    public List<Map<String, Object>> recientes(int limit) {
        return visitRouteRepository.findAll().stream()
                .sorted(Comparator.comparing(VisitRoute::getId).reversed())
                .limit(limit)
                .map(vr -> {
                    Subject s = vr.getSubject();
                    String encuestador = null;
                    ShiftSchedule ss = vr.getShiftSchedule();
                    if (ss != null && ss.getUser() != null) {
                        UserEntity u = ss.getUser();
                        encuestador = (u.getName() + " " + u.getLastName()).trim();
                    }
                    Map<String, Object> item = new LinkedHashMap<>();
                    item.put("id", vr.getId());
                    item.put("sujetoNombre", s != null ? s.getBusinessName() : null);
                    item.put("direccion", s != null ? s.getPhysicalAddress() : null);
                    item.put("encuestador", encuestador);
                    item.put("fecha", vr.getScheduledDate());
                    item.put("estado", estado(vr));
                    return item;
                })
                .toList();
    }
}
