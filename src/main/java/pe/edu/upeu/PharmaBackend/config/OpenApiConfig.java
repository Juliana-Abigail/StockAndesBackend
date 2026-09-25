package pe.edu.upeu.PharmaBackend.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.*;

@Configuration

public class OpenApiConfig {

    @Bean OpenAPI inventarioApi() {
        return new OpenAPI().info(new Info().title("Inventario API").version("v1").description("StockAndes - control de inventarios"));
    }
}
