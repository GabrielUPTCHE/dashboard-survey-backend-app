package com.survey.springboot.pwa.app.springboot_survey_app.service.implementation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.survey.springboot.pwa.app.springboot_survey_app.exception.ResourceNotFoundException;
import com.survey.springboot.pwa.app.springboot_survey_app.model.dto.DocumentTypeDTO;
import com.survey.springboot.pwa.app.springboot_survey_app.model.mapper.DocumentTypeMapper;
import com.survey.springboot.pwa.app.springboot_survey_app.persistence.models.entity.DocumentType;
import com.survey.springboot.pwa.app.springboot_survey_app.persistence.repository.DocumentTypeRepository;
import com.survey.springboot.pwa.app.springboot_survey_app.service.useCase.DocumentTypeService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DocumentTypeServiceImpl implements DocumentTypeService {

    private final DocumentTypeRepository repository;
    private final DocumentTypeMapper mapper;

    @Override
    @Transactional
    public DocumentTypeDTO create(DocumentTypeDTO dto) {
        DocumentType entity = mapper.toEntity(dto);
        return mapper.toDTO(repository.save(entity));
    }

    @Override
    @Transactional(readOnly = true)
    public DocumentTypeDTO getById(String id) {
        DocumentType entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tipo de Documento no encontrado con id: " + id));
        return mapper.toDTO(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public List<DocumentTypeDTO> getAll() {
        return repository.findAll().stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public DocumentTypeDTO update(String id, DocumentTypeDTO dto) {
        DocumentType existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tipo de Documento no encontrado con id: " + id));
        mapper.updateEntityFromDTO(dto, existing);
        return mapper.toDTO(repository.save(existing));
    }

    @Override
    @Transactional
    public void delete(String id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Tipo de Documento no encontrado con id: " + id);
        }
        repository.deleteById(id);
    }
}
