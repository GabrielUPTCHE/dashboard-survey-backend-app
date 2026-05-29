package com.survey.springboot.pwa.app.springboot_survey_app.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

import com.survey.springboot.pwa.app.springboot_survey_app.model.dto.LegalDocumentDTO;
import com.survey.springboot.pwa.app.springboot_survey_app.persistence.models.entity.LegalDocument;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface LegalDocumentMapper {

    @Mapping(source = "documentType.id", target = "documentTypeId")
    @Mapping(source = "subject.id", target = "subjectId")
    LegalDocumentDTO toDTO(LegalDocument entity);

    @Mapping(source = "documentTypeId", target = "documentType.id")
    @Mapping(source = "subjectId", target = "subject.id")
    LegalDocument toEntity(LegalDocumentDTO dto);

    @Mapping(source = "documentTypeId", target = "documentType.id")
    @Mapping(source = "subjectId", target = "subject.id")
    void updateEntityFromDTO(LegalDocumentDTO dto, @MappingTarget LegalDocument entity);
}
