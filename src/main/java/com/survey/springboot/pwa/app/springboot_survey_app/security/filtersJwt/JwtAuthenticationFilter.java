package com.survey.springboot.pwa.app.springboot_survey_app.security.filtersJwt;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.survey.springboot.pwa.app.springboot_survey_app.config.AppProperties;
import com.survey.springboot.pwa.app.springboot_survey_app.persistence.models.user.UserEntity;
import com.survey.springboot.pwa.app.springboot_survey_app.persistence.repository.UserRepository;
import com.survey.springboot.pwa.app.springboot_survey_app.security.jwt.JwtUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import org.springframework.security.authentication.BadCredentialsException;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final JwtUtils jwtUtils;
    private final UserRepository userRepository;
    private final AppProperties appProperties;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public JwtAuthenticationFilter(JwtUtils jwtUtils,
                                   UserRepository userRepository,
                                   AppProperties appProperties) {
        this.jwtUtils = jwtUtils;
        this.userRepository = userRepository;
        this.appProperties = appProperties;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException {
        try {
            String body = new String(request.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
            if (body.isBlank()) {
                throw new BadCredentialsException("Credenciales requeridas");
            }
            Map<String, Object> jsonMap = objectMapper.readValue(body, new TypeReference<Map<String, Object>>() {});
            String email = (String) jsonMap.get("email");
            String password = (String) jsonMap.get("password");
            return getAuthenticationManager().authenticate(
                    new UsernamePasswordAuthenticationToken(email, password));
        } catch (AuthenticationException e) {
            throw e;
        } catch (Exception e) {
            throw new BadCredentialsException("Credenciales inválidas", e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {
        User principal = (User) authResult.getPrincipal();
        String email = principal.getUsername();
        String token = jwtUtils.generateAccesToken(email);

        boolean secure = appProperties.getCookie().isSecure();
        Cookie cookie = new Cookie("token", token);
        cookie.setHttpOnly(true);
        cookie.setSecure(secure);
        cookie.setPath("/");
        cookie.setMaxAge(60 * 60 * 24);
        response.addCookie(cookie);

        // Cargar el usuario para devolver nombre, rol, etc.
        UserEntity user = userRepository.findByEmail(email).orElseThrow();

        Map<String, Object> body = new HashMap<>();
        body.put("message", "Autenticación correcta");
        body.put("email", email);
        body.put("name", user.getName());
        body.put("lastName", user.getLastName());
        body.put("numberIdentification", user.getNumberIdentification());
        body.put("role", user.getRole().getName().name());
        body.put("expiresAt", Instant.now().plusSeconds(86400).toString());

        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(objectMapper.writeValueAsString(body));
        response.getWriter().flush();
    }
}
