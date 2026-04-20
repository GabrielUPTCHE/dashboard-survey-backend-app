package com.survey.springboot.pwa.app.springboot_survey_app.service.implementation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.survey.springboot.pwa.app.springboot_survey_app.exception.ResourceNotFoundException;
import com.survey.springboot.pwa.app.springboot_survey_app.model.dto.VisitRouteDTO;
import com.survey.springboot.pwa.app.springboot_survey_app.model.mapper.VisitRouteMapper;
import com.survey.springboot.pwa.app.springboot_survey_app.persistence.models.entity.VisitRoute;
import com.survey.springboot.pwa.app.springboot_survey_app.persistence.repository.VisitRouteRepository;
import com.survey.springboot.pwa.app.springboot_survey_app.service.useCase.VisitRouteService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VisitRouteServiceImpl implements VisitRouteService {

    private final VisitRouteRepository repository;
    private final VisitRouteMapper mapper;

    @Override
    @Transactional
    public VisitRouteDTO create(VisitRouteDTO dto) {
        VisitRoute entity = mapper.toEntity(dto);
        return mapper.toDTO(repository.save(entity));
    }

    @Override
    @Transactional(readOnly = true)
    public VisitRouteDTO getById(Long id) {
        VisitRoute entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ruta de visita no encontrada con id: " + id));
        return mapper.toDTO(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public List<VisitRouteDTO> getAll() {
        return repository.findAll().stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public VisitRouteDTO update(Long id, VisitRouteDTO dto) {
        VisitRoute existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ruta de visita no encontrada con id: " + id));
        mapper.updateEntityFromDTO(dto, existing);
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
}