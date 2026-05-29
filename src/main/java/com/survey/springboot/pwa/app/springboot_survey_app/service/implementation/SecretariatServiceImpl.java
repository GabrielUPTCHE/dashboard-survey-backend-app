package com.survey.springboot.pwa.app.springboot_survey_app.service.implementation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.survey.springboot.pwa.app.springboot_survey_app.exception.ResourceNotFoundException;
import com.survey.springboot.pwa.app.springboot_survey_app.model.dto.SecretariatDTO;
import com.survey.springboot.pwa.app.springboot_survey_app.model.mapper.SecretariatMapper;
import com.survey.springboot.pwa.app.springboot_survey_app.persistence.models.entity.Secretariat;
import com.survey.springboot.pwa.app.springboot_survey_app.persistence.repository.SecretariatRepository;
import com.survey.springboot.pwa.app.springboot_survey_app.service.useCase.SecretariatService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SecretariatServiceImpl implements SecretariatService {

    private final SecretariatRepository repository;
    private final SecretariatMapper mapper;

    @Override
    @Transactional
    public SecretariatDTO create(SecretariatDTO dto) {
        Secretariat entity = mapper.toEntity(dto);
        return mapper.toDTO(repository.save(entity));
    }

    @Override
    @Transactional(readOnly = true)
    public SecretariatDTO getById(Long id) {
        Secretariat entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Secretaría no encontrada con id: " + id));
        return mapper.toDTO(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SecretariatDTO> getAll() {
        return repository.findAll().stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public SecretariatDTO update(Long id, SecretariatDTO dto) {
        Secretariat existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Secretaría no encontrada con id: " + id));
        mapper.updateEntityFromDTO(dto, existing);
        return mapper.toDTO(repository.save(existing));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!repository.existsById(id)) throw new ResourceNotFoundException("Secretaría no encontrada con id: " + id);
        repository.deleteById(id);
    }
}