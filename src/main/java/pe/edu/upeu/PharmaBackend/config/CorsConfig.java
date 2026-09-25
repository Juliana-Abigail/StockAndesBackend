package pe.edu.upeu.PharmaBackend.config;

import org.springframework.context.annotation.*;
import org.springframework.web.servlet.config.annotation.*;

@Configuration

public class CorsConfig implements WebMvcConfigurer {

    public void addCorsMappings(CorsRegistry r) {
        r.addMapping("/api/**").allowedOrigins("http://localhost:4200").allowedMethods("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS").allowedHeaders("*").maxAge(3600);
    }
}
