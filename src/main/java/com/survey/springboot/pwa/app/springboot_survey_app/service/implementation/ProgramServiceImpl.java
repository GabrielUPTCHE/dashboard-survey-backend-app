package com.survey.springboot.pwa.app.springboot_survey_app.service.implementation;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.survey.springboot.pwa.app.springboot_survey_app.exception.ResourceNotFoundException;
import com.survey.springboot.pwa.app.springboot_survey_app.model.dto.ProgramDTO;
import com.survey.springboot.pwa.app.springboot_survey_app.model.mapper.ProgramMapper;
import com.survey.springboot.pwa.app.springboot_survey_app.persistence.models.entity.Program;
import com.survey.springboot.pwa.app.springboot_survey_app.persistence.repository.ProgramRepository;
import com.survey.springboot.pwa.app.springboot_survey_app.service.useCase.ProgramService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProgramServiceImpl implements ProgramService {

    private final ProgramRepository repository;
    private final ProgramMapper mapper;

    @Override
    public ProgramDTO create(ProgramDTO dto) {
        Program entity = mapper.toEntity(dto);
        return mapper.toDTO(repository.save(entity));
    }

    @Override
    public ProgramDTO getById(Long id) {
        Program entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Programa no encontrado con id: " + id));
        return mapper.toDTO(entity);
    }

    @Override
    public List<ProgramDTO> getAll() {
        return repository.findAll().stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ProgramDTO update(Long id, ProgramDTO dto) {
        Program existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Programa no encontrado con id: " + id));
        mapper.updateEntityFromDTO(dto, existing);
        return mapper.toDTO(repository.save(existing));
    }

    @Override
    public void delete(Long id) {
        if (!repository.existsById(id)) throw new ResourceNotFoundException("Programa no encontrado con id: " + id);
        repository.deleteById(id);
    }
}