package com.survey.springboot.pwa.app.springboot_survey_app.repository;


import com.survey.springboot.pwa.app.springboot_survey_app.models.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, String> {
    Optional<UserEntity> findByEmail(String email);
}
