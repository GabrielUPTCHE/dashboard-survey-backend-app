package com.survey.springboot.pwa.app.springboot_survey_app.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
@Order(0)
@RequiredArgsConstructor
@Slf4j
public class DatabaseMigration implements CommandLineRunner {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public void run(String... args) {
        try {
            // Permitir rutas de visita sin turno asignado (estado "Sin asignar")
            jdbcTemplate.execute(
                "ALTER TABLE rutas_visitas ALTER COLUMN programacion_turnos DROP NOT NULL"
            );
            log.info("Migración: programacion_turnos ahora es nullable");
        } catch (Exception e) {
            // Si ya es nullable o la tabla no existe, no es un error crítico
            log.debug("Migración programacion_turnos: {}", e.getMessage());
        }
    }
}
