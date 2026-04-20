package com.survey.springboot.pwa.app.springboot_survey_app.service.implementation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.survey.springboot.pwa.app.springboot_survey_app.exception.ResourceNotFoundException;
import com.survey.springboot.pwa.app.springboot_survey_app.model.dto.DirectorateDTO;
import com.survey.springboot.pwa.app.springboot_survey_app.model.mapper.DirectorateMapper;
import com.survey.springboot.pwa.app.springboot_survey_app.persistence.models.entity.Directorate;
import com.survey.springboot.pwa.app.springboot_survey_app.persistence.repository.DirectorateRepository;
import com.survey.springboot.pwa.app.springboot_survey_app.service.useCase.DirectorateService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DirectorateServiceImpl implements DirectorateService {

    private final DirectorateRepository repository;
    private final DirectorateMapper mapper;

    @Override
    @Transactional
    public DirectorateDTO create(DirectorateDTO dto) {
        Directorate entity = mapper.toEntity(dto);
        return mapper.toDTO(repository.save(entity));
    }

    @Override
    @Transactional(readOnly = true)
    public DirectorateDTO getById(Long id) {
        Directorate entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Dirección no encontrada con id: " + id));
        return mapper.toDTO(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public List<DirectorateDTO> getAll() {
        return repository.findAll().stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public DirectorateDTO update(Long id, DirectorateDTO dto) {
        Directorate existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Dirección no encontrada con id: " + id));
        mapper.updateEntityFromDTO(dto, existing);
        return mapper.toDTO(repository.save(existing));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!repository.existsById(id)) throw new ResourceNotFoundException("Dirección no encontrada con id: " + id);
        repository.deleteById(id);
    }
}