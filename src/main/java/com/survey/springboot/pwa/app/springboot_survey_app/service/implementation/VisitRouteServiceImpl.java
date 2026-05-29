package com.survey.springboot.pwa.app.springboot_survey_app.service.implementation;

import com.survey.springboot.pwa.app.springboot_survey_app.exception.ResourceNotFoundException;
import com.survey.springboot.pwa.app.springboot_survey_app.model.dto.VisitRouteDTO;
import com.survey.springboot.pwa.app.springboot_survey_app.model.dto.VisitRouteDetailDTO;
import com.survey.springboot.pwa.app.springboot_survey_app.model.mapper.VisitRouteMapper;
import com.survey.springboot.pwa.app.springboot_survey_app.persistence.models.entity.Program;
import com.survey.springboot.pwa.app.springboot_survey_app.persistence.models.entity.ShiftSchedule;
import com.survey.springboot.pwa.app.springboot_survey_app.persistence.models.entity.Subject;
import com.survey.springboot.pwa.app.springboot_survey_app.persistence.models.entity.VisitRoute;
import com.survey.springboot.pwa.app.springboot_survey_app.persistence.models.user.UserEntity;
import com.survey.springboot.pwa.app.springboot_survey_app.persistence.repository.ProgramRepository;
import com.survey.springboot.pwa.app.springboot_survey_app.persistence.repository.ShiftScheduleRepository;
import com.survey.springboot.pwa.app.springboot_survey_app.persistence.repository.SubjectRepository;
import com.survey.springboot.pwa.app.springboot_survey_app.persistence.repository.VisitRouteRepository;
import com.survey.springboot.pwa.app.springboot_survey_app.service.useCase.VisitRouteService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VisitRouteServiceImpl implements VisitRouteService {

    private final VisitRouteRepository repository;
    private final VisitRouteMapper mapper;
    private final SubjectRepository subjectRepository;
    private final ProgramRepository programRepository;
    private final ShiftScheduleRepository shiftScheduleRepository;

    @Override
    @Transactional
    public VisitRouteDTO create(VisitRouteDTO dto) {
        VisitRoute entity = buildEntity(dto);
        return mapper.toDTO(repository.save(entity));
    }

    @Override
    @Transactional(readOnly = true)
    public VisitRouteDTO getById(Long id) {
        return mapper.toDTO(repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ruta de visita no encontrada con id: " + id)));
    }

    @Override
    @Transactional(readOnly = true)
    public List<VisitRouteDTO> getAll() {
        return repository.findAll().stream().map(mapper::toDTO).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public VisitRouteDTO update(Long id, VisitRouteDTO dto) {
        VisitRoute existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ruta de visita no encontrada con id: " + id));
        if (dto.getStatus() != null)       existing.setStatus(dto.getStatus());
        if (dto.getScheduledDate() != null) existing.setScheduledDate(dto.getScheduledDate());
        if (dto.getSubjectId() != null)
            existing.setSubject(resolveSubject(dto.getSubjectId()));
        if (dto.getProgramId() != null)
            existing.setProgram(resolveProgram(dto.getProgramId()));
        if (dto.getShiftScheduleId() != null)
            existing.setShiftSchedule(resolveShift(dto.getShiftScheduleId()));
        return mapper.toDTO(repository.save(existing));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Ruta de visita no encontrada con id: " + id);
        }
        repository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<VisitRouteDetailDTO> getAllDetailed(Optional<LocalDate> date) {
        List<VisitRoute> routes = date.isPresent()
                ? repository.findByScheduledDate(date.get())
                : repository.findAll();
        return routes.stream().map(this::toDetail).collect(Collectors.toList());
    }

    private VisitRoute buildEntity(VisitRouteDTO dto) {
        VisitRoute entity = new VisitRoute();
        entity.setStatus(dto.getStatus());
        entity.setScheduledDate(dto.getScheduledDate());
        entity.setSubject(resolveSubject(dto.getSubjectId()));
        entity.setProgram(resolveProgram(dto.getProgramId()));
        if (dto.getShiftScheduleId() != null) {
            entity.setShiftSchedule(resolveShift(dto.getShiftScheduleId()));
        }
        return entity;
    }

    private Subject resolveSubject(Long id) {
        return subjectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Sujeto no encontrado: " + id));
    }

    private Program resolveProgram(Long id) {
        return programRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Programa no encontrado: " + id));
    }

    private ShiftSchedule resolveShift(Long id) {
        return shiftScheduleRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Turno no encontrado: " + id));
    }

    private VisitRouteDetailDTO toDetail(VisitRoute r) {
        VisitRouteDetailDTO.SubjectInfo subjectInfo = null;
        if (r.getSubject() != null) {
            Subject s = r.getSubject();
            subjectInfo = VisitRouteDetailDTO.SubjectInfo.builder()
                    .id(s.getId())
                    .businessName(s.getBusinessName())
                    .physicalAddress(s.getPhysicalAddress())
                    .neighborhood(s.getNeighborhood())
                    .zone(s.getZone())
                    .build();
        }

        VisitRouteDetailDTO.SurveyorInfo surveyorInfo = null;
        VisitRouteDetailDTO.ShiftInfo shiftInfo = null;
        if (r.getShiftSchedule() != null) {
            ShiftSchedule shift = r.getShiftSchedule();
            shiftInfo = VisitRouteDetailDTO.ShiftInfo.builder()
                    .id(shift.getId())
                    .date(shift.getDate())
                    .startTime(shift.getStartTime())
                    .endTime(shift.getEndTime())
                    .build();
            UserEntity surveyor = shift.getUser();
            if (surveyor != null) {
                surveyorInfo = VisitRouteDetailDTO.SurveyorInfo.builder()
                        .numberIdentification(surveyor.getNumberIdentification())
                        .name(surveyor.getName())
                        .lastName(surveyor.getLastName())
                        .build();
            }
        }

        VisitRouteDetailDTO.ProgramInfo programInfo = null;
        if (r.getProgram() != null) {
            programInfo = VisitRouteDetailDTO.ProgramInfo.builder()
                    .id(r.getProgram().getId())
                    .name(r.getProgram().getName())
                    .build();
        }

        return VisitRouteDetailDTO.builder()
                .id(r.getId())
                .status(r.getStatus())
                .scheduledDate(r.getScheduledDate())
                .subject(subjectInfo)
                .surveyor(surveyorInfo)
                .shift(shiftInfo)
                .program(programInfo)
                .build();
    }
}
