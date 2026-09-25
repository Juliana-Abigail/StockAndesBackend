package pe.edu.upeu.PharmaBackend.controller;

import jakarta.validation.Valid;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import pe.edu.upeu.PharmaBackend.dto.*;
import pe.edu.upeu.PharmaBackend.service.service.DespachoService;

@RestController

@RequestMapping("/api/v1/despachos")

public class DespachoController {
    private final DespachoService s;

    public DespachoController(DespachoService s) {
        this.s = s;
    }

    @PostMapping

    public ResponseEntity<?> crear(@Valid @RequestBody DespachoRequestDTO r) {
        return ResponseEntity.status(201).body(s.registrar(r));
    }

    @GetMapping

    public ResponseEntity<?> listar() {
        return ResponseEntity.ok(s.listar());
    }

    @GetMapping("/{id}")

    public ResponseEntity<?> uno(@PathVariable Long id) {
        return ResponseEntity.ok(s.buscar(id));
    }

    @PatchMapping("/{id}/anular")

    public ResponseEntity<?> anular(@PathVariable Long id) {
        return ResponseEntity.ok(s.anular(id));
    }
}
