package com.survey.springboot.pwa.app.springboot_survey_app.persistence.models.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "direcciones")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Directorate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_direccion")
    private Long id;

    @Column(name = "nombre_direccion", length = 50)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_secretaria", nullable = false)
    private Secretariat secretariat;

    @OneToMany(mappedBy = "directorate", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Program> programs;
}