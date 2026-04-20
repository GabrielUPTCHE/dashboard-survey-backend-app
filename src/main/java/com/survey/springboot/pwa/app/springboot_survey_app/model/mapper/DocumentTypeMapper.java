package com.survey.springboot.pwa.app.springboot_survey_app.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

import com.survey.springboot.pwa.app.springboot_survey_app.model.dto.DocumentTypeDTO;
import com.survey.springboot.pwa.app.springboot_survey_app.persistence.models.entity.DocumentType;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface DocumentTypeMapper {
    DocumentTypeDTO toDTO(DocumentType entity);
    DocumentType toEntity(DocumentTypeDTO dto);
    void updateEntityFromDTO(DocumentTypeDTO dto, @MappingTarget DocumentType entity);
}