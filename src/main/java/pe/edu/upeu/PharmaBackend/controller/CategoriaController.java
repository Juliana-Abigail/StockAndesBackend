package pe.edu.upeu.PharmaBackend.controller;

import jakarta.validation.Valid;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import pe.edu.upeu.PharmaBackend.dto.*;
import pe.edu.upeu.PharmaBackend.service.service.*;

@RestController

@RequestMapping("/api/v1/categorias")

public class CategoriaController {
    private final CategoriaService s;
    private final ProductoService ps;

    public CategoriaController(CategoriaService s, ProductoService ps) {
        this.s = s;
        this.ps = ps;
    }

    @PostMapping

    public ResponseEntity<?> crear(@Valid @RequestBody CategoriaRequestDTO r) {
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

    public ResponseEntity<?> actualizar(@PathVariable Long id, @Valid @RequestBody CategoriaRequestDTO r) {
        return ResponseEntity.ok(s.update(id, r));
    }

    @DeleteMapping("/{id}")

    public ResponseEntity<Void> borrar(@PathVariable Long id) {
        s.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/productos")

    public ResponseEntity<?> productos(@PathVariable Long id) {
        return ResponseEntity.ok(ps.porCategoria(id));
    }
}
