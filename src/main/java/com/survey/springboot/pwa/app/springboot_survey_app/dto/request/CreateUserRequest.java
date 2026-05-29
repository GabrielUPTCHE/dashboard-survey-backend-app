package com.survey.springboot.pwa.app.springboot_survey_app.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateUserRequest {
    private String numberIdentification;
    private String name;
    private String lastName;
    private String email;
    private String role;
    private String password;
}
