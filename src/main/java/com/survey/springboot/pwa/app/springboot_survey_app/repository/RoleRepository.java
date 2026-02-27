package com.survey.springboot.pwa.app.springboot_survey_app.repository;

import com.survey.springboot.pwa.app.springboot_survey_app.models.user.ERole;
import com.survey.springboot.pwa.app.springboot_survey_app.models.user.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<RoleEntity, Integer> {
    Optional<RoleEntity> findByName(ERole role);
}
