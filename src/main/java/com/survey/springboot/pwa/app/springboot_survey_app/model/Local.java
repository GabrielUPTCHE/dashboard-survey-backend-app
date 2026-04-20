package com.survey.springboot.pwa.app.springboot_survey_app.model;

import lombok.Data;

@Data
public class Local { 
    private String nit;
    private String razonSocial;
    private String propietario;
    private double latitud;  
    private double longitud;
}