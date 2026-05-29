package com.survey.springboot.pwa.app.springboot_survey_app.persistence.repository;

import com.survey.springboot.pwa.app.springboot_survey_app.persistence.models.entity.ShiftSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ShiftScheduleRepository extends JpaRepository<ShiftSchedule, Long> {
    List<ShiftSchedule> findByDate(LocalDate date);
}
