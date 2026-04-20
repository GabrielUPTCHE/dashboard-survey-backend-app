package com.survey.springboot.pwa.app.springboot_survey_app.service.implementation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.survey.springboot.pwa.app.springboot_survey_app.exception.ResourceNotFoundException;
import com.survey.springboot.pwa.app.springboot_survey_app.model.dto.ShiftScheduleDTO;
import com.survey.springboot.pwa.app.springboot_survey_app.model.mapper.ShiftScheduleMapper;
import com.survey.springboot.pwa.app.springboot_survey_app.persistence.models.entity.ShiftSchedule;
import com.survey.springboot.pwa.app.springboot_survey_app.persistence.repository.ShiftScheduleRepository;
import com.survey.springboot.pwa.app.springboot_survey_app.service.useCase.ShiftScheduleService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ShiftScheduleServiceImpl implements ShiftScheduleService {

    private final ShiftScheduleRepository repository;
    private final ShiftScheduleMapper mapper;

    @Override
    @Transactional
    public ShiftScheduleDTO create(ShiftScheduleDTO dto) {
        ShiftSchedule entity = mapper.toEntity(dto);
        return mapper.toDTO(repository.save(entity));
    }

    @Override
    @Transactional(readOnly = true)
    public ShiftScheduleDTO getById(Long id) {
        ShiftSchedule entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Turno programado no encontrado con id: " + id));
        return mapper.toDTO(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ShiftScheduleDTO> getAll() {
        return repository.findAll().stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ShiftScheduleDTO update(Long id, ShiftScheduleDTO dto) {
        ShiftSchedule existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Turno programado no encontrado con id: " + id));
        mapper.updateEntityFromDTO(dto, existing);
        return mapper.toDTO(repository.save(existing));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Turno programado no encontrado con id: " + id);
        }
        repository.deleteById(id);
    }
}