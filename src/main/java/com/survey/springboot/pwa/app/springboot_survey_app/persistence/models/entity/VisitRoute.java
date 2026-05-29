package com.survey.springboot.pwa.app.springboot_survey_app.persistence.models.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "rutas_visitas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VisitRoute {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_rutas")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "programacion_turnos", nullable = true)
    private ShiftSchedule shiftSchedule;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_sujeto", nullable = false)
    private Subject subject;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_programa", nullable = false)
    private Program program;

    @Column(name = "fecha_programada")
    private LocalDate scheduledDate;

    @Column(name = "estado", length = 20)
    private String status;
}