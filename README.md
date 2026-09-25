# StockAndes - Inventario API
Backend REST Spring Boot 4 / Java 24 / PostgreSQL, adaptado desde la estructura de PharmaBackend.

## Capas
`controller -> service (interfaces + impl) -> repository -> entity`, usando DTO de entrada/salida y manejo global de errores.

## Ejecución
1. Crear/configurar PostgreSQL según `application-dev.yaml`.
2. `./mvnw spring-boot:run -Dspring-boot.run.profiles=dev`
3. Ejecutar `datos_semilla.sql` después del primer arranque.
4. Swagger: `/swagger-ui.html`.

El perfil prod usa variables DB_URL, DB_USERNAME y DB_PASSWORD, `ddl-auto: validate` y Swagger deshabilitado. CORS acepta solo `http://localhost:4200`.


## JDK recomendado
Java 24, distribución Eclipse Temurin. Configure el Project SDK/JAVA_HOME con Temurin 24.

## Conexion PostgreSQL en Docker

Configuracion de desarrollo incluida en `application-dev.yaml`:

- Host: `localhost`
- Puerto: `5434`
- Base de datos: `stockandes`
- Usuario: `postgres`
- Contrasena: `12345`
- URL JDBC: `jdbc:postgresql://localhost:5434/stockandes`

Antes de iniciar la API, asegurese de que la base `stockandes` exista en el contenedor PostgreSQL.

