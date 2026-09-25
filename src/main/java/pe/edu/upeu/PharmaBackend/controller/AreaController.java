package pe.edu.upeu.PharmaBackend.controller;

import jakarta.validation.Valid;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import pe.edu.upeu.PharmaBackend.dto.*;
import pe.edu.upeu.PharmaBackend.service.service.AreaService;

@RestController

@RequestMapping("/api/v1/areas")

public class AreaController {
    private final AreaService s;

    public AreaController(AreaService s) {
        this.s = s;
    }

    @PostMapping

    public ResponseEntity<?> crear(@Valid @RequestBody AreaRequestDTO r) {
        return ResponseEntity.status(201).body(s.create(r));
    }

    @GetMapping

    public ResponseEntity<?> listar() {
        return ResponseEntity.ok(s.readAll());
    }

    @GetMapping("/{id}")

    public ResponseEntity<?> uno(@PathVariable Long id) {
        return ResponseEntity.ok(s.read(id));
    }

    @PutMapping("/{id}")

    public ResponseEntity<?> actualizar(@PathVariable Long id, @Valid @RequestBody AreaRequestDTO r) {
        return ResponseEntity.ok(s.update(id, r));
    }
}
