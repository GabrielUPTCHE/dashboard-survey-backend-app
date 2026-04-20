package com.survey.springboot.pwa.app.springboot_survey_app.persistence.models.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "sujetos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Subject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_sujeto")
    private Long id;

    @Column(name = "nit", length = 20)
    private String nit;

    @Column(name = "razon_social", length = 40)
    private String businessName;

    @Column(name = "representacion_legal", length = 50)
    private String legalRepresentation;

    @Column(name = "latitud")
    private Double latitude;

    @Column(name = "longitud")
    private Double longitude;

    @Column(name = "direccion_fisica", length = 50)
    private String physicalAddress;

    @Column(name = "barrio", length = 50)
    private String neighborhood;

    @Column(name = "zona", length = 50)
    private String zone;

    @Column(name = "estado_censo", length = 50)
    private String censusStatus;

    @Column(name = "created_at")
    private LocalDate createdAt;
}