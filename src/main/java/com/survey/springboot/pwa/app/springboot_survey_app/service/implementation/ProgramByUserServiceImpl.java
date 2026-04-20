package com.survey.springboot.pwa.app.springboot_survey_app.service.implementation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.survey.springboot.pwa.app.springboot_survey_app.exception.ResourceNotFoundException;
import com.survey.springboot.pwa.app.springboot_survey_app.model.dto.ProgramByUserDTO;
import com.survey.springboot.pwa.app.springboot_survey_app.model.mapper.ProgramByUserMapper;
import com.survey.springboot.pwa.app.springboot_survey_app.persistence.models.entity.ProgramByUser;
import com.survey.springboot.pwa.app.springboot_survey_app.persistence.repository.ProgramByUserRepository;
import com.survey.springboot.pwa.app.springboot_survey_app.service.useCase.ProgramByUserService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProgramByUserServiceImpl implements ProgramByUserService {

    private final ProgramByUserRepository repository;
    private final ProgramByUserMapper mapper;

    @Override
    @Transactional
    public ProgramByUserDTO create(ProgramByUserDTO dto) {
        ProgramByUser entity = mapper.toEntity(dto);
        return mapper.toDTO(repository.save(entity));
    }

    @Override
    @Transactional(readOnly = true)
    public ProgramByUserDTO getById(Long id) {
        ProgramByUser entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Asignación de programa no encontrada con id: " + id));
        return mapper.toDTO(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProgramByUserDTO> getAll() {
        return repository.findAll().stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ProgramByUserDTO update(Long id, ProgramByUserDTO dto) {
        ProgramByUser existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Asignación de programa no encontrada con id: " + id));
        mapper.updateEntityFromDTO(dto, existing);
        return mapper.toDTO(repository.save(existing));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Asignación de programa no encontrada con id: " + id);
        }
        repository.deleteById(id);
    }
}