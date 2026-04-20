package com.survey.springboot.pwa.app.springboot_survey_app.persistence.models.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "secretaria")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Secretariat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_secretaria")
    private Long id;

    @Column(name = "nombre_secretaria", length = 50)
    private String name;

    @OneToMany(mappedBy = "secretariat", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Directorate> directorates;
}