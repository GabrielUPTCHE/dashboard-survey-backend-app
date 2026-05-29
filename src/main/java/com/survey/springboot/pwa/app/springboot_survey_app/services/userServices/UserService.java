package com.survey.springboot.pwa.app.springboot_survey_app.services.userServices;

import com.survey.springboot.pwa.app.springboot_survey_app.dto.request.CreateUserRequest;
import com.survey.springboot.pwa.app.springboot_survey_app.dto.request.UpdateUserRequest;
import com.survey.springboot.pwa.app.springboot_survey_app.exception.ResourceNotFoundException;
import com.survey.springboot.pwa.app.springboot_survey_app.model.dto.UserResponse;
import com.survey.springboot.pwa.app.springboot_survey_app.persistence.models.user.CrendentialEntity;
import com.survey.springboot.pwa.app.springboot_survey_app.persistence.models.user.ERole;
import com.survey.springboot.pwa.app.springboot_survey_app.persistence.models.user.RoleEntity;
import com.survey.springboot.pwa.app.springboot_survey_app.persistence.models.user.UserEntity;
import com.survey.springboot.pwa.app.springboot_survey_app.persistence.repository.RoleRepository;
import com.survey.springboot.pwa.app.springboot_survey_app.persistence.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    UserRepository userRepository;

    public List<UserEntity> getUsers(String role) {
        if (role != null && !role.isBlank()) {
            return userRepository.findByRole_Name(ERole.valueOf(role.toUpperCase()));
        }
        return userRepository.findAll();
    }

    public UserEntity createUser(CreateUserRequest request) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        try {
            RoleEntity role = roleRepository.findByName(ERole.valueOf(request.getRole()))
                    .orElseThrow(() -> new RuntimeException("Rol no encontrado: " + request.getRole()));

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
            if (message != null && message.contains("Detail:")) {
                message = message.substring(message.indexOf("Detail:"));
            }
            throw new RuntimeException(message);
        }
    }

    public List<UserResponse> getUsers(Optional<String> roleFilter) {
        List<UserEntity> users;
        if (roleFilter.isPresent() && StringUtils.hasText(roleFilter.get())) {
            ERole eRole = ERole.valueOf(roleFilter.get().toUpperCase());
            users = userRepository.findByRole_Name(eRole);
        } else {
            users = userRepository.findAll();
        }
        return users.stream().map(this::toResponse).collect(Collectors.toList());
    }

    public UserResponse updateUser(String numberIdentification, UpdateUserRequest request) {
        UserEntity user = userRepository.findById(numberIdentification)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado: " + numberIdentification));

        if (StringUtils.hasText(request.getName())) {
            user.setName(request.getName());
        }
        if (StringUtils.hasText(request.getLastName())) {
            user.setLastName(request.getLastName());
        }
        if (StringUtils.hasText(request.getRole())) {
            RoleEntity role = roleRepository.findByName(ERole.valueOf(request.getRole().toUpperCase()))
                    .orElseThrow(() -> new RuntimeException("Rol no encontrado: " + request.getRole()));
            user.setRole(role);
        }
        if (request.getState() != null) {
            user.setState(request.getState());
        }

        return toResponse(userRepository.save(user));
    }

    public UserResponse toResponse(UserEntity user) {
        return UserResponse.builder()
                .numberIdentification(user.getNumberIdentification())
                .name(user.getName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .state(user.isState())
                .role(UserResponse.RoleInfo.builder()
                        .id(user.getRole().getId())
                        .name(user.getRole().getName().name())
                        .build())
                .build();
    }

    @Bean
    @Order(1)
    CommandLineRunner initRoles(RoleRepository roleRepository) {
        return args -> {
            if (roleRepository.count() == 0) {
                roleRepository.saveAll(List.of(
                        RoleEntity.builder().name(ERole.ADMIN).build(),
                        RoleEntity.builder().name(ERole.ASSISTANT).build(),
                        RoleEntity.builder().name(ERole.SURVEYOR).build()
                ));
            }
        };
    }
}
