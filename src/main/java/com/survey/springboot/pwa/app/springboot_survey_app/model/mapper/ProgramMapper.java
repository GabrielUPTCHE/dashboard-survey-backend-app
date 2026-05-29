package com.survey.springboot.pwa.app.springboot_survey_app.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

import com.survey.springboot.pwa.app.springboot_survey_app.model.dto.ProgramDTO;
import com.survey.springboot.pwa.app.springboot_survey_app.persistence.models.entity.Program;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ProgramMapper {

    @Mapping(source = "directorate.id", target = "directorateId")
    ProgramDTO toDTO(Program program);

    @Mapping(source = "directorateId", target = "directorate.id")
    Program toEntity(ProgramDTO dto);

    @Mapping(source = "directorateId", target = "directorate.id")
    void updateEntityFromDTO(ProgramDTO dto, @MappingTarget Program entity);
}
