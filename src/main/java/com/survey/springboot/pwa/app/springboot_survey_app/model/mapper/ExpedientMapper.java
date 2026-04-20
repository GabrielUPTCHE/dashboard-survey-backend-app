package com.survey.springboot.pwa.app.springboot_survey_app.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

import com.survey.springboot.pwa.app.springboot_survey_app.model.dto.ExpedientDTO;
import com.survey.springboot.pwa.app.springboot_survey_app.persistence.models.entity.Expedient;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ExpedientMapper {

    @Mapping(source = "subject.id", target = "subjectId")
    ExpedientDTO toDTO(Expedient entity);

    @Mapping(source = "subjectId", target = "subject.id")
    Expedient toEntity(ExpedientDTO dto);

    @Mapping(source = "subjectId", target = "subject.id")
    void updateEntityFromDTO(ExpedientDTO dto, @MappingTarget Expedient entity);
}
