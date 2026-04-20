package com.survey.springboot.pwa.app.springboot_survey_app.model;

import java.util.UUID;

import lombok.Data;

@Data
public class Usuario {
    private String id = UUID.randomUUID().toString();
    private String nombre;
    private String email;
    private String rol; 
    private boolean activo = true;
}
