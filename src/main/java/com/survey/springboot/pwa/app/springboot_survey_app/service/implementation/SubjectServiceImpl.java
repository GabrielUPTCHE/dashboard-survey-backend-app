package com.survey.springboot.pwa.app.springboot_survey_app.service.implementation;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.survey.springboot.pwa.app.springboot_survey_app.exception.ResourceNotFoundException;
import com.survey.springboot.pwa.app.springboot_survey_app.model.dto.SubjectDTO;
import com.survey.springboot.pwa.app.springboot_survey_app.model.mapper.SubjectMapper;
import com.survey.springboot.pwa.app.springboot_survey_app.persistence.models.entity.Subject;
import com.survey.springboot.pwa.app.springboot_survey_app.persistence.repository.SubjectRepository;
import com.survey.springboot.pwa.app.springboot_survey_app.service.useCase.SubjectService;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SubjectServiceImpl implements SubjectService {

    private final SubjectRepository subjectRepository;
    private final SubjectMapper subjectMapper;

    @Override
    @Transactional
    public SubjectDTO create(SubjectDTO subjectDTO) {
        Subject subject = subjectMapper.toEntity(subjectDTO);
        Subject savedSubject = subjectRepository.save(subject);
        return subjectMapper.toDTO(savedSubject);
    }

    @Override
    @Transactional(readOnly = true)
    public SubjectDTO getById(Long id) {
        Subject subject = subjectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Sujeto no encontrado con id: " + id));
        return subjectMapper.toDTO(subject);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SubjectDTO> getAll() {
        return subjectRepository.findAll().stream()
                .map(subjectMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public SubjectDTO update(Long id, SubjectDTO subjectDTO) {
        Subject existingSubject = subjectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Sujeto no encontrado con id: " + id));
        subjectMapper.updateEntityFromDTO(subjectDTO, existingSubject);
        
        Subject updatedSubject = subjectRepository.save(existingSubject);
        return subjectMapper.toDTO(updatedSubject);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (!subjectRepository.existsById(id)) {
            throw new ResourceNotFoundException("Sujeto no encontrado con id: " + id);
        }
        subjectRepository.deleteById(id);
    }
}