# StockAndes - Inventario API
Backend REST Spring Boot 4 / Java 24 / PostgreSQL, adaptado desde la estructura de PharmaBackend.

## Capas
`controller -> service (interfaces + impl) -> repository -> entity`, usando DTO de entrada/salida y manejo global de errores.

## Ejecución
1. Crear/configurar PostgreSQL según `application-dev.yaml`.
2. `./mvnw spring-boot:run -Dspring-boot.run.profiles=dev`
3. Swagger: `/swagger-ui.html`.

## JDK recomendado
Java 24, distribución Eclipse Temurin. Configure el Project SDK/JAVA_HOME con Temurin 24.

## Conexion PostgreSQL en Docker
- Host: `localhost`
- Puerto: `5434`
- Base de datos: `stockandes`
- Usuario: `postgres`
- Contrasena: `12345`
- URL JDBC: `jdbc:postgresql://localhost:5434/stockandes`