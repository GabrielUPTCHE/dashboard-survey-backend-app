package com.survey.springboot.pwa.app.springboot_survey_app.persistence.repository;

import com.survey.springboot.pwa.app.springboot_survey_app.persistence.models.entity.VisitRoute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface VisitRouteRepository extends JpaRepository<VisitRoute, Long> {
    List<VisitRoute> findByScheduledDate(LocalDate date);
    List<VisitRoute> findByScheduledDateBetween(LocalDate from, LocalDate to);
    long countByStatus(String status);
    List<VisitRoute> findTop10ByOrderByScheduledDateDesc();
}
