package com.survey.springboot.pwa.app.springboot_survey_app.services.userServices;

import com.survey.springboot.pwa.app.springboot_survey_app.dto.request.CreateUserRequest;
import com.survey.springboot.pwa.app.springboot_survey_app.persistence.models.user.CrendentialEntity;
import com.survey.springboot.pwa.app.springboot_survey_app.persistence.models.user.ERole;
import com.survey.springboot.pwa.app.springboot_survey_app.persistence.models.user.RoleEntity;
import com.survey.springboot.pwa.app.springboot_survey_app.persistence.models.user.UserEntity;
import com.survey.springboot.pwa.app.springboot_survey_app.persistence.repository.RoleRepository;
import com.survey.springboot.pwa.app.springboot_survey_app.persistence.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    RoleRepository roleRepository;

    @Autowired
    UserRepository userRepository;

    public UserEntity createUser(CreateUserRequest request) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        try {
            RoleEntity role = roleRepository.findByName(ERole.valueOf(request.getRole()))
                    .orElseThrow(() -> new RuntimeException("Rol no encontrado"));

            UserEntity user = UserEntity.builder()
                    .numberIdentification(request.getNumberIdentification())
                    .name(request.getName())
                    .lastName(request.getLastName())
                    .email(request.getEmail())
                    .role(role)
                    .build();

            CrendentialEntity credential = CrendentialEntity.builder()
                    .user(user)
                    .password(passwordEncoder.encode(request.getPassword()))
                    .build();

            user.setCredential(credential);
            return userRepository.save(user);

        } catch (DataIntegrityViolationException e) {
            Throwable root = e.getRootCause();
            String message = root != null ? root.getMessage() : e.getMessage();
            if (message.contains("Detail:")) {
                message = message.substring(message.indexOf("Detail:"));
            }
            throw new RuntimeException(message);
           // throw new RuntimeException("Error al crear el usuario: " + e.getMessage());
        }
    }

    @Bean
    CommandLineRunner initRoles(RoleRepository roleRepository) {
        return args -> {
            if (roleRepository.count() == 0) {
                RoleEntity admin = RoleEntity.builder()
                        .name(ERole.ADMIN)
                        .build();

                roleRepository.save(admin);
            }
        };
    }
}
