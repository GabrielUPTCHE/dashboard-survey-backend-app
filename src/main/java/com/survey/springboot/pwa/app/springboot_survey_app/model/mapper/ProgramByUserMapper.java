package com.survey.springboot.pwa.app.springboot_survey_app.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

import com.survey.springboot.pwa.app.springboot_survey_app.model.dto.ProgramByUserDTO;
import com.survey.springboot.pwa.app.springboot_survey_app.persistence.models.entity.ProgramByUser;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ProgramByUserMapper {

    @Mapping(source = "program.id", target = "programId")
    @Mapping(source = "user.numberIdentification", target = "userId") 
    ProgramByUserDTO toDTO(ProgramByUser entity);

    @Mapping(source = "programId", target = "program.id")
    @Mapping(source = "userId", target = "user.numberIdentification")
    ProgramByUser toEntity(ProgramByUserDTO dto);

    @Mapping(source = "programId", target = "program.id")
    @Mapping(source = "userId", target = "user.numberIdentification")
    void updateEntityFromDTO(ProgramByUserDTO dto, @MappingTarget ProgramByUser entity);
}
