package com.survey.springboot.pwa.app.springboot_survey_app.service.implementation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.survey.springboot.pwa.app.springboot_survey_app.exception.ResourceNotFoundException;
import com.survey.springboot.pwa.app.springboot_survey_app.model.dto.ExpedientDTO;
import com.survey.springboot.pwa.app.springboot_survey_app.model.mapper.ExpedientMapper;
import com.survey.springboot.pwa.app.springboot_survey_app.persistence.models.entity.Expedient;
import com.survey.springboot.pwa.app.springboot_survey_app.persistence.repository.ExpedientRepository;
import com.survey.springboot.pwa.app.springboot_survey_app.service.useCase.ExpedientService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ExpedientServiceImpl implements ExpedientService {

    private final ExpedientRepository repository;
    private final ExpedientMapper mapper;

    @Override
    @Transactional
    public ExpedientDTO create(ExpedientDTO dto) {
        Expedient entity = mapper.toEntity(dto);
        return mapper.toDTO(repository.save(entity));
    }

    @Override
    @Transactional(readOnly = true)
    public ExpedientDTO getById(Long id) {
        Expedient entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Expediente no encontrado con id: " + id));
        return mapper.toDTO(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ExpedientDTO> getAll() {
        return repository.findAll().stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ExpedientDTO update(Long id, ExpedientDTO dto) {
        Expedient existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Expediente no encontrado con id: " + id));
        mapper.updateEntityFromDTO(dto, existing);
        return mapper.toDTO(repository.save(existing));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Expediente no encontrado con id: " + id);
        }
        repository.deleteById(id);
    }
}