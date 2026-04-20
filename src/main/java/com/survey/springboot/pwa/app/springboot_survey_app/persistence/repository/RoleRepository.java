package com.survey.springboot.pwa.app.springboot_survey_app.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.survey.springboot.pwa.app.springboot_survey_app.persistence.models.user.ERole;
import com.survey.springboot.pwa.app.springboot_survey_app.persistence.models.user.RoleEntity;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<RoleEntity, Integer> {
    Optional<RoleEntity> findByName(ERole role);
}
