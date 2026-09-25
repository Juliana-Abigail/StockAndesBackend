package pe.edu.upeu.PharmaBackend.controller;

import org.springframework.http.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.*;

@RestController

@RequestMapping("/api/v1")

public class HealthController {
    private final JdbcTemplate jdbc;

    public HealthController(JdbcTemplate j) {
        jdbc = j;
    }

    @GetMapping("/health")

    public ResponseEntity<Map<String, Object>> health() {
        Map<String, Object> m = new LinkedHashMap<>();
        m.put("status", "UP");
        m.put("timestamp", LocalDateTime.now());
        try {
            jdbc.queryForObject("SELECT 1 FROM DUAL", Integer.class);
            m.put("database", "UP");
            return ResponseEntity.ok(m);
        }
        catch (Exception e) {
            m.put("status", "DOWN");
            m.put("database", "DOWN");
            return ResponseEntity.status(503).body(m);
        }
    }
}
