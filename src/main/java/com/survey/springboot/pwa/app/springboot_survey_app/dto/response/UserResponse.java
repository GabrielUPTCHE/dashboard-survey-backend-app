package com.survey.springboot.pwa.app.springboot_survey_app.dto.response;

import com.survey.springboot.pwa.app.springboot_survey_app.persistence.models.user.UserEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponse {
    private String numberIdentification;
    private String name;
    private String lastName;
    private String email;
    private boolean state;
    private RoleResponse role;

    public static UserResponse fromEntity(UserEntity user) {
        RoleResponse role = null;
        if (user.getRole() != null) {
            role = RoleResponse.builder()
                    .id(user.getRole().getId())
                    .name(user.getRole().getName() != null ? user.getRole().getName().name() : null)
                    .build();
        }
        return UserResponse.builder()
                .numberIdentification(user.getNumberIdentification())
                .name(user.getName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .state(user.isState())
                .role(role)
                .build();
    }
}
