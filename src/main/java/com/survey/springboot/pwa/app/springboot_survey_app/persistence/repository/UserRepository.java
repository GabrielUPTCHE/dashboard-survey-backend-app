package com.survey.springboot.pwa.app.springboot_survey_app.persistence.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.survey.springboot.pwa.app.springboot_survey_app.persistence.models.user.UserEntity;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, String> {
    Optional<UserEntity> findByEmail(String email);
}
