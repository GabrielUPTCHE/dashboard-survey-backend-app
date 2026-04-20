package com.survey.springboot.pwa.app.springboot_survey_app.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

import com.survey.springboot.pwa.app.springboot_survey_app.model.dto.DirectorateDTO;
import com.survey.springboot.pwa.app.springboot_survey_app.persistence.models.entity.Directorate;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface DirectorateMapper {
    
    @Mapping(source = "secretariat.id", target = "secretariatId")
    DirectorateDTO toDTO(Directorate directorate);

    @Mapping(source = "secretariatId", target = "secretariat.id")
    Directorate toEntity(DirectorateDTO dto);

    @Mapping(source = "secretariatId", target = "secretariat.id")
    void updateEntityFromDTO(DirectorateDTO dto, @MappingTarget Directorate entity);
}
