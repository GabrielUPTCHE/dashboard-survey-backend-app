package com.survey.springboot.pwa.app.springboot_survey_app.security.filtersJwt;

import tools.jackson.databind.ObjectMapper;
import com.survey.springboot.pwa.app.springboot_survey_app.security.jwt.JwtUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private JwtUtils jwtUtils;

    public JwtAuthenticationFilter(JwtUtils jwtUtils) {
        this.jwtUtils = jwtUtils;
    }

    @Override
    public Authentication attemptAuthentication (HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        try {
            //credential = new ObjectMapper().readValue(request.getInputStream(), Credential.class);
            ObjectMapper mapper = new ObjectMapper();
            String body = new String(request.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
            Map<String, Object> jsonMap = mapper.readValue(body, Map.class);
            password = (String) jsonMap.get("password");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        User user = (User) authResult.getPrincipal();
        String token = jwtUtils.generateAccesToken(user.getUsername());
        Map<String, Object> httpResponse = new HashMap<>();
        response.getWriter().write(new ObjectMapper().writeValueAsString(httpResponse));
        response.setStatus(200);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().flush();
    }
}
