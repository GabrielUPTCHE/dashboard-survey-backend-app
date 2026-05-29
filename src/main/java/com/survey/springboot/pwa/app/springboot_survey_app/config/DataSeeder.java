package com.survey.springboot.pwa.app.springboot_survey_app.config;

import com.survey.springboot.pwa.app.springboot_survey_app.persistence.models.entity.*;
import com.survey.springboot.pwa.app.springboot_survey_app.persistence.models.user.CrendentialEntity;
import com.survey.springboot.pwa.app.springboot_survey_app.persistence.models.user.ERole;
import com.survey.springboot.pwa.app.springboot_survey_app.persistence.models.user.RoleEntity;
import com.survey.springboot.pwa.app.springboot_survey_app.persistence.models.user.UserEntity;
import com.survey.springboot.pwa.app.springboot_survey_app.persistence.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Component
@Order(2)
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final SecretariatRepository secretariatRepository;
    private final DirectorateRepository directorateRepository;
    private final ProgramRepository programRepository;
    private final SubjectRepository subjectRepository;
    private final ShiftScheduleRepository shiftScheduleRepository;
    private final VisitRouteRepository visitRouteRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        seedUsers();
        seedOrgStructure();
        seedSubjectsAndVisits();
    }

    private void seedUsers() {
        if (userRepository.count() > 0) return;

        RoleEntity adminRole = roleRepository.findByName(ERole.ADMIN)
                .orElseThrow(() -> new RuntimeException("Roles no inicializados"));
        RoleEntity surveyorRole = roleRepository.findByName(ERole.SURVEYOR)
                .orElseThrow(() -> new RuntimeException("Roles no inicializados"));

        createUser("9000000001", "Admin", "Principal", "admin@censo.gov.co", "admin123", adminRole);
        createUser("9000000002", "Juan", "García", "juan.garcia@censo.gov.co", "encuesta123", surveyorRole);
        createUser("9000000003", "María", "López", "maria.lopez@censo.gov.co", "encuesta123", surveyorRole);
        createUser("9000000004", "Carlos", "Ruiz", "carlos.ruiz@censo.gov.co", "encuesta123", surveyorRole);
        createUser("9000000005", "Ana", "Martínez", "ana.martinez@censo.gov.co", "encuesta123", surveyorRole);
        createUser("9000000006", "Pedro", "Gómez", "pedro.gomez@censo.gov.co", "encuesta123", surveyorRole);
    }

    private void createUser(String id, String name, String lastName, String email, String rawPass, RoleEntity role) {
        UserEntity user = UserEntity.builder()
                .numberIdentification(id)
                .name(name)
                .lastName(lastName)
                .email(email)
                .role(role)
                .build();
        CrendentialEntity cred = CrendentialEntity.builder()
                .user(user)
                .password(passwordEncoder.encode(rawPass))
                .build();
        user.setCredential(cred);
        userRepository.save(user);
    }

    private Program defaultProgram;

    private void seedOrgStructure() {
        if (secretariatRepository.count() > 0) {
            defaultProgram = programRepository.findAll().stream().findFirst().orElse(null);
            return;
        }

        Secretariat sec = secretariatRepository.save(
                Secretariat.builder().name("Secretaría de Desarrollo Económico").build());

        Directorate dir = directorateRepository.save(
                Directorate.builder().name("Dirección de Censo y Estadística").secretariat(sec).build());

        defaultProgram = programRepository.save(
                Program.builder().name("Programa Censo Empresarial 2026").directorate(dir).build());
    }

    private void seedSubjectsAndVisits() {
        if (subjectRepository.count() > 0) return;

        List<Subject> subjects = subjectRepository.saveAll(List.of(
                subject("900123001", "Panadería El Trigo", "Carlos Pérez", "Calle 45 # 12-34", "Centro", "Zona 1"),
                subject("900123002", "Tienda La Esquina", "Rosa Gómez", "Av. Principal # 3-10", "Sur", "Zona 2"),
                subject("900123003", "Ferretería Centro", "Luis Torres", "Carrera 8 # 5-20", "Centro", "Zona 1"),
                subject("900123004", "Supermercado Norte", "Patricia Mora", "Calle 12 # 8-45", "Norte", "Zona 3"),
                subject("900123005", "Droguería Salud", "Jorge Silva", "Cra 15 # 20-10", "Oriental", "Zona 2"),
                subject("900123006", "Restaurante Buen Sabor", "Sandra Castro", "Calle 30 # 6-18", "Centro", "Zona 1"),
                subject("900123007", "Papelería Escolar", "Roberto Díaz", "Carrera 5 # 14-22", "Sur", "Zona 2"),
                subject("900123008", "Café Central", "Laura Reyes", "Calle 10 # 9-33", "Centro", "Zona 1")
        ));

        UserEntity surveyor1 = userRepository.findById("9000000002").orElseThrow();
        UserEntity surveyor2 = userRepository.findById("9000000003").orElseThrow();
        UserEntity surveyor3 = userRepository.findById("9000000004").orElseThrow();

        LocalDate today = LocalDate.now();
        LocalDate yesterday = today.minusDays(1);
        LocalDate tomorrow = today.plusDays(1);

        // Turnos
        ShiftSchedule shiftHoyMañana = saveShift(surveyor1, today, LocalTime.of(8, 0), LocalTime.of(12, 0));
        ShiftSchedule shiftHoyTarde  = saveShift(surveyor2, today, LocalTime.of(14, 0), LocalTime.of(17, 0));
        ShiftSchedule shiftAyer      = saveShift(surveyor3, yesterday, LocalTime.of(8, 0), LocalTime.of(12, 0));
        ShiftSchedule shiftMañana    = saveShift(surveyor1, tomorrow, LocalTime.of(9, 0), LocalTime.of(13, 0));

        // Rutas de visita para hoy mañana
        saveVisit(subjects.get(0), shiftHoyMañana, today, "En Progreso");
        saveVisit(subjects.get(1), shiftHoyMañana, today, "Pendiente");
        saveVisit(subjects.get(2), shiftHoyMañana, today, "Pendiente");

        // Rutas para hoy tarde
        saveVisit(subjects.get(3), shiftHoyTarde, today, "Pendiente");
        saveVisit(subjects.get(4), null, today, "Sin asignar");

        // Rutas de ayer (completadas)
        saveVisit(subjects.get(5), shiftAyer, yesterday, "Completada");
        saveVisit(subjects.get(6), shiftAyer, yesterday, "Completada");
        saveVisit(subjects.get(7), shiftAyer, yesterday, "Completada");

        // Rutas de mañana
        saveVisit(subjects.get(0), shiftMañana, tomorrow, "Pendiente");
        saveVisit(subjects.get(1), null, tomorrow, "Sin asignar");
    }

    private Subject subject(String nit, String businessName, String legalRep,
                            String address, String neighborhood, String zone) {
        return Subject.builder()
                .nit(nit)
                .businessName(businessName)
                .legalRepresentation(legalRep)
                .physicalAddress(address)
                .neighborhood(neighborhood)
                .zone(zone)
                .censusStatus("Pendiente")
                .createdAt(LocalDate.now())
                .build();
    }

    private ShiftSchedule saveShift(UserEntity user, LocalDate date,
                                    LocalTime start, LocalTime end) {
        return shiftScheduleRepository.save(ShiftSchedule.builder()
                .user(user)
                .date(date)
                .startTime(start)
                .endTime(end)
                .activityType("Inspección")
                .build());
    }

    private void saveVisit(Subject subject, ShiftSchedule shift, LocalDate date, String status) {
        visitRouteRepository.save(VisitRoute.builder()
                .subject(subject)
                .shiftSchedule(shift)
                .program(defaultProgram)
                .scheduledDate(date)
                .status(status)
                .build());
    }
}
