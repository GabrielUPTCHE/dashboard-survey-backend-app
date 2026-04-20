package com.survey.springboot.pwa.app.springboot_survey_app.model.mapper;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

import com.survey.springboot.pwa.app.springboot_survey_app.model.dto.SecretariatDTO;
import com.survey.springboot.pwa.app.springboot_survey_app.persistence.models.entity.Secretariat;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface SecretariatMapper {
    SecretariatDTO toDTO(Secretariat secretariat);
    Secretariat toEntity(SecretariatDTO secretariatDTO);
    void updateEntityFromDTO(SecretariatDTO dto, @MappingTarget Secretariat entity);
}