package com.survey.springboot.pwa.app.springboot_survey_app.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.survey.springboot.pwa.app.springboot_survey_app.persistence.models.entity.LegalDocument;

@Repository
public interface LegalDocumentRepository extends JpaRepository<LegalDocument, String> {
}