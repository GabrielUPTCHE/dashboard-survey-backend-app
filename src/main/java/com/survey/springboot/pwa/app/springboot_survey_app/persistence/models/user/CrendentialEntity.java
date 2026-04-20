package com.survey.springboot.pwa.app.springboot_survey_app.persistence.models.user;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name ="CREDENCIALES")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CrendentialEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_credential")
    @Getter @Setter
    private Integer id;

    @OneToOne
    @JoinColumn(name = "numero_identificacion", referencedColumnName = "numero_identificacion")
    @Getter @Setter
    private UserEntity user;

    @Column(name = "contrasena")
    @Getter @Setter
    private String password;

    @Column(name = "estado")
    @Getter @Setter
    private boolean state = true;

}
