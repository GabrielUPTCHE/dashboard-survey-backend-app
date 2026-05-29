package com.survey.springboot.pwa.app.springboot_survey_app.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "app")
@Getter
@Setter
public class AppProperties {

    private Cors cors = new Cors();
    private Cookie cookie = new Cookie();

    @Getter @Setter
    public static class Cors {
        private String allowedOrigins = "http://localhost:5174";
    }

    @Getter @Setter
    public static class Cookie {
        private String sameSite = "Lax";
        private boolean secure = false;
    }
}
