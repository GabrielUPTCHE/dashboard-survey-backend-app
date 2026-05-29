package com.survey.springboot.pwa.app.springboot_survey_app.persistence.models.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tipo_documento")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DocumentType {

    @Id
    @Column(name = "id_tipo_documento", length = 5)
    private String id;

    @Column(name = "nombre_documeto", length = 50) 
    private String name;
}