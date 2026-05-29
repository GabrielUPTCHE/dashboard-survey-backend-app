package com.survey.springboot.pwa.app.springboot_survey_app.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateUserRequest {
    private String name;
    private String lastName;
    private String role;
    private Boolean state;
}
