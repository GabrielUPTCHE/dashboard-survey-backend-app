package com.survey.springboot.pwa.app.springboot_survey_app.model.dto;

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
    private RoleInfo role;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RoleInfo {
        private Integer id;
        private String name;
    }
}
