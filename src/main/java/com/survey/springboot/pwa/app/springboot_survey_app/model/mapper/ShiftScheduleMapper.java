package com.survey.springboot.pwa.app.springboot_survey_app.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

import com.survey.springboot.pwa.app.springboot_survey_app.model.dto.ShiftScheduleDTO;
import com.survey.springboot.pwa.app.springboot_survey_app.persistence.models.entity.ShiftSchedule;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ShiftScheduleMapper {

    @Mapping(source = "user.numberIdentification", target = "userId") // Ajustar "user.id" si es necesario
    ShiftScheduleDTO toDTO(ShiftSchedule entity);

    @Mapping(source = "userId", target = "user.numberIdentification")
    ShiftSchedule toEntity(ShiftScheduleDTO dto);

    @Mapping(source = "userId", target = "user.numberIdentification")
    void updateEntityFromDTO(ShiftScheduleDTO dto, @MappingTarget ShiftSchedule entity);
}