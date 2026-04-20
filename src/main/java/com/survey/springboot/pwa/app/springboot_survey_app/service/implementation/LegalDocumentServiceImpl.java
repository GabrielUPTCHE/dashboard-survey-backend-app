package com.survey.springboot.pwa.app.springboot_survey_app.service.implementation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.survey.springboot.pwa.app.springboot_survey_app.exception.ResourceNotFoundException;
import com.survey.springboot.pwa.app.springboot_survey_app.model.dto.LegalDocumentDTO;
import com.survey.springboot.pwa.app.springboot_survey_app.model.mapper.LegalDocumentMapper;
import com.survey.springboot.pwa.app.springboot_survey_app.persistence.models.entity.LegalDocument;
import com.survey.springboot.pwa.app.springboot_survey_app.persistence.repository.LegalDocumentRepository;
import com.survey.springboot.pwa.app.springboot_survey_app.service.useCase.LegalDocumentService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LegalDocumentServiceImpl implements LegalDocumentService {

    private final LegalDocumentRepository repository;
    private final LegalDocumentMapper mapper;

    @Override
    @Transactional
    public LegalDocumentDTO create(LegalDocumentDTO dto) {
        LegalDocument entity = mapper.toEntity(dto);
        return mapper.toDTO(repository.save(entity));
    }

    @Override
    @Transactional(readOnly = true)
    public LegalDocumentDTO getById(String id) {
        LegalDocument entity = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Documento legal no encontrado con id: " + id));
        return mapper.toDTO(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public List<LegalDocumentDTO> getAll() {
        return repository.findAll().stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public LegalDocumentDTO update(String id, LegalDocumentDTO dto) {
        LegalDocument existing = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Documento legal no encontrado con id: " + id));
        mapper.updateEntityFromDTO(dto, existing);
        return mapper.toDTO(repository.save(existing));
    }

    @Override
    @Transactional
    public void delete(String id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("Documento legal no encontrado con id: " + id);
        }
        repository.deleteById(id);
    }
}
