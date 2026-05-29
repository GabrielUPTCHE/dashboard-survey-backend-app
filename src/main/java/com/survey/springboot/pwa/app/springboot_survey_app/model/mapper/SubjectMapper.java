package com.survey.springboot.pwa.app.springboot_survey_app.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

import com.survey.springboot.pwa.app.springboot_survey_app.model.dto.SubjectDTO;
import com.survey.springboot.pwa.app.springboot_survey_app.persistence.models.entity.Subject;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SubjectMapper {

    SubjectDTO toDTO(Subject subject);

    Subject toEntity(SubjectDTO subjectDTO);

    void updateEntityFromDTO(SubjectDTO subjectDTO, @MappingTarget Subject subject);
}