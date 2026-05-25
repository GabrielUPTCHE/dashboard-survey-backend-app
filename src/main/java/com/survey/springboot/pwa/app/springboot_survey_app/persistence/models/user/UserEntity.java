package com.survey.springboot.pwa.app.springboot_survey_app.persistence.models.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Set;

@Entity
@Table(name ="USUARIOS")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserEntity {
    @Id
    @NotNull(message = "El numero de identificacion es obligatorio")
    @Column(name = "numero_identificacion", unique = true, length = 20, nullable = false)
    @Getter @Setter
    private String numberIdentification;

    @Getter @Setter
    @NotNull(message = "El nombre es obligatorio")
    @Column(name= "nombre", length = 50, nullable = false)
    private String name;

    @NotNull(message = "El apellido es obligatorio")
    @Getter @Setter
    @Column(name= "apellido", length = 50, nullable = false)
    private String lastName;

    @NotNull(message = "El tipo de identificacion es obligatorio")
    @Column(name= "correo", unique = true, length = 70 , nullable = false)
    @Email
    @Getter @Setter
    private String email;

    @Column(name = "estado")
    @Getter @Setter
    private boolean state = true;

    @JsonIgnore
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    @Getter @Setter
    private CrendentialEntity credential;

    @ManyToOne
    @Getter @Setter
    @JoinColumn(name = "rol_id")
    private RoleEntity role;

    @PrePersist
    public void prePersist() {
        if (!this.state) {
            this.state = true;  // Establecer valor por defecto si no se ha asignado
        }
    }
}
