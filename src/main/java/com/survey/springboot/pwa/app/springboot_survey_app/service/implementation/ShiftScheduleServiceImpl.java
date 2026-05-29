package com.survey.springboot.pwa.app.springboot_survey_app.service.implementation;

import com.survey.springboot.pwa.app.springboot_survey_app.exception.ResourceNotFoundException;
import com.survey.springboot.pwa.app.springboot_survey_app.model.dto.ShiftScheduleDTO;
import com.survey.springboot.pwa.app.springboot_survey_app.model.mapper.ShiftScheduleMapper;
import com.survey.springboot.pwa.app.springboot_survey_app.persistence.models.entity.ShiftSchedule;
import com.survey.springboot.pwa.app.springboot_survey_app.persistence.models.user.UserEntity;
import com.survey.springboot.pwa.app.springboot_survey_app.persistence.repository.ShiftScheduleRepository;
import com.survey.springboot.pwa.app.springboot_survey_app.persistence.repository.UserRepository;
import com.survey.springboot.pwa.app.springboot_survey_app.service.useCase.ShiftScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ShiftScheduleServiceImpl implements ShiftScheduleService {

    private final ShiftScheduleRepository repository;
    private final ShiftScheduleMapper mapper;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public ShiftScheduleDTO create(ShiftScheduleDTO dto) {
        ShiftSchedule entity = mapper.toEntity(dto);
        entity.setUser(resolveUser(dto.getUserId()));
        return mapper.toDTO(repository.save(entity));
    }

    @Override
    @Transactional(readOnly = true)
    public ShiftScheduleDTO getById(Long id) {
        return mapper.toDTO(repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Turno no encontrado con id: " + id)));
    }

    @Override
    @Transactional(readOnly = true)
    public List<ShiftScheduleDTO> getAll() {
        return repository.findAll().stream().map(mapper::toDTO).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ShiftScheduleDTO update(Long id, ShiftScheduleDTO dto) {
        ShiftSchedule existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Turno no encontrado con id: " + id));
        mapper.updateEntityFromDTO(dto, existing);
        if (dto.getUserId() != null) {
            existing.setUser(resolveUser(dto.getUserId()));
        }
        return mapper.toDTO(repository.save(existing));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Turno no encontrado con id: " + id);
        }
        repository.deleteById(id);
    }

    private UserEntity resolveUser(String numberIdentification) {
        return userRepository.findById(numberIdentification)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado: " + numberIdentification));
    }
}
