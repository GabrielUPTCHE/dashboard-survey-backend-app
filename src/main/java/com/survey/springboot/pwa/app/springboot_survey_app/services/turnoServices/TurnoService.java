package com.survey.springboot.pwa.app.springboot_survey_app.services.turnoServices;

import com.survey.springboot.pwa.app.springboot_survey_app.dto.request.TurnoAssignRequest;
import com.survey.springboot.pwa.app.springboot_survey_app.dto.request.TurnoCreateRequest;
import com.survey.springboot.pwa.app.springboot_survey_app.exception.ResourceNotFoundException;
import com.survey.springboot.pwa.app.springboot_survey_app.persistence.models.entity.Program;
import com.survey.springboot.pwa.app.springboot_survey_app.persistence.models.entity.ShiftSchedule;
import com.survey.springboot.pwa.app.springboot_survey_app.persistence.models.entity.Subject;
import com.survey.springboot.pwa.app.springboot_survey_app.persistence.models.entity.VisitRoute;
import com.survey.springboot.pwa.app.springboot_survey_app.persistence.models.user.UserEntity;
import com.survey.springboot.pwa.app.springboot_survey_app.persistence.repository.ProgramRepository;
import com.survey.springboot.pwa.app.springboot_survey_app.persistence.repository.ShiftScheduleRepository;
import com.survey.springboot.pwa.app.springboot_survey_app.persistence.repository.SubjectRepository;
import com.survey.springboot.pwa.app.springboot_survey_app.persistence.repository.UserRepository;
import com.survey.springboot.pwa.app.springboot_survey_app.persistence.repository.VisitRouteRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/** Fachada de "turnos": VisitRoute + ShiftSchedule + Subject + User. */
@Service
@RequiredArgsConstructor
public class TurnoService {

    private static final DateTimeFormatter HORA = DateTimeFormatter.ofPattern("HH:mm");

    private final VisitRouteRepository visitRouteRepository;
    private final ShiftScheduleRepository shiftScheduleRepository;
    private final SubjectRepository subjectRepository;
    private final UserRepository userRepository;
    private final ProgramRepository programRepository;

    private String estado(VisitRoute vr) {
        String raw = vr.getStatus() == null ? "" : vr.getStatus().trim().toLowerCase();
        if (raw.contains("complet")) return "Completada";
        if (raw.contains("progres") || raw.contains("curso")) return "En Progreso";
        if (raw.contains("sin") || vr.getShiftSchedule() == null) return "Sin asignar";
        return "Pendiente";
    }

    private Map<String, Object> toMap(VisitRoute vr) {
        Subject s = vr.getSubject();
        ShiftSchedule ss = vr.getShiftSchedule();

        Map<String, Object> sujeto = new LinkedHashMap<>();
        sujeto.put("nombre", s != null ? s.getBusinessName() : null);
        sujeto.put("direccion", s != null ? s.getPhysicalAddress() : null);
        sujeto.put("barrio", s != null ? s.getNeighborhood() : null);
        sujeto.put("zona", s != null ? s.getZone() : null);

        String encuestadorId = null;
        String encuestadorNombre = null;
        LocalTime inicio = null;
        LocalTime fin = null;
        if (ss != null) {
            inicio = ss.getStartTime();
            fin = ss.getEndTime();
            UserEntity u = ss.getUser();
            if (u != null) {
                encuestadorId = u.getNumberIdentification();
                encuestadorNombre = (u.getName() + " " + u.getLastName()).trim();
            }
        }

        Map<String, Object> item = new LinkedHashMap<>();
        item.put("id", vr.getId());
        item.put("sujeto", sujeto);
        item.put("encuestadorId", encuestadorId);
        item.put("encuestadorNombre", encuestadorNombre);
        item.put("fecha", vr.getScheduledDate() != null ? vr.getScheduledDate().toString() : null);
        item.put("horaInicio", inicio != null ? inicio.format(HORA) : null);
        item.put("horaFin", fin != null ? fin.format(HORA) : null);
        item.put("estado", estado(vr));
        return item;
    }

    @Transactional(readOnly = true)
    public List<Map<String, Object>> getTurnos(LocalDate fecha) {
        return visitRouteRepository.findAll().stream()
                .filter(vr -> fecha == null || fecha.equals(vr.getScheduledDate()))
                .map(this::toMap)
                .toList();
    }

    @Transactional
    public Map<String, Object> assign(Long id, TurnoAssignRequest req) {
        VisitRoute vr = visitRouteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Turno no encontrado con id: " + id));

        UserEntity user = userRepository.findById(req.getEncuestadorId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Encuestador no encontrado: " + req.getEncuestadorId()));

        ShiftSchedule ss = vr.getShiftSchedule();
        if (ss == null) {
            ss = new ShiftSchedule();
            ss.setActivityType("Visita");
        }
        ss.setUser(user);
        ss.setDate(req.getFecha());
        ss.setStartTime(req.getHoraInicio());
        ss.setEndTime(req.getHoraFin());
        ss = shiftScheduleRepository.save(ss);

        vr.setShiftSchedule(ss);
        vr.setScheduledDate(req.getFecha());
        if (!"Completada".equals(estado(vr))) {
            vr.setStatus("Pendiente");
        }
        return toMap(visitRouteRepository.save(vr));
    }

    @Transactional
    public Map<String, Object> create(TurnoCreateRequest req) {
        Subject subject = subjectRepository.findById(req.getSubjectId())
                .orElseThrow(() -> new ResourceNotFoundException("Sujeto no encontrado: " + req.getSubjectId()));

        Program program;
        if (req.getProgramId() != null) {
            program = programRepository.findById(req.getProgramId())
                    .orElseThrow(() -> new ResourceNotFoundException("Programa no encontrado: " + req.getProgramId()));
        } else {
            program = programRepository.findAll().stream().findFirst()
                    .orElseThrow(() -> new RuntimeException("No hay programas configurados en el sistema"));
        }

        UserEntity user = userRepository.findById(req.getEncuestadorId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Encuestador no encontrado: " + req.getEncuestadorId()));

        ShiftSchedule ss = ShiftSchedule.builder()
                .user(user)
                .date(req.getFecha())
                .startTime(req.getHoraInicio())
                .endTime(req.getHoraFin())
                .activityType("Visita")
                .build();
        ss = shiftScheduleRepository.save(ss);

        VisitRoute vr = VisitRoute.builder()
                .shiftSchedule(ss)
                .subject(subject)
                .program(program)
                .scheduledDate(req.getFecha())
                .status("Pendiente")
                .build();
        return toMap(visitRouteRepository.save(vr));
    }
}
