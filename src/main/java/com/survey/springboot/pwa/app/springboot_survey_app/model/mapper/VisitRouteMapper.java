package com.survey.springboot.pwa.app.springboot_survey_app.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

import com.survey.springboot.pwa.app.springboot_survey_app.model.dto.VisitRouteDTO;
import com.survey.springboot.pwa.app.springboot_survey_app.persistence.models.entity.VisitRoute;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface VisitRouteMapper {

    @Mapping(source = "shiftSchedule.id", target = "shiftScheduleId")
    @Mapping(source = "subject.id", target = "subjectId")
    @Mapping(source = "program.id", target = "programId")
    VisitRouteDTO toDTO(VisitRoute entity);

    @Mapping(source = "shiftScheduleId", target = "shiftSchedule.id")
    @Mapping(source = "subjectId", target = "subject.id")
    @Mapping(source = "programId", target = "program.id")
    VisitRoute toEntity(VisitRouteDTO dto);

    @Mapping(source = "shiftScheduleId", target = "shiftSchedule.id")
    @Mapping(source = "subjectId", target = "subject.id")
    @Mapping(source = "programId", target = "program.id")
    void updateEntityFromDTO(VisitRouteDTO dto, @MappingTarget VisitRoute entity);
}