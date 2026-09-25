package pe.edu.upeu.PharmaBackend.controller;

import jakarta.validation.Valid;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import pe.edu.upeu.PharmaBackend.dto.*;
import pe.edu.upeu.PharmaBackend.service.service.ProductoService;

@RestController

@RequestMapping("/api/v1/productos")

public class ProductoController {
    private final ProductoService s;

    public ProductoController(ProductoService s) {
        this.s = s;
    }

    @PostMapping

    public ResponseEntity<?> crear(@Valid @RequestBody ProductoRequestDTO r) {
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

    public ResponseEntity<?> actualizar(@PathVariable Long id, @Valid @RequestBody ProductoRequestDTO r) {
        return ResponseEntity.ok(s.update(id, r));
    }

    @DeleteMapping("/{id}")

    public ResponseEntity<Void> borrar(@PathVariable Long id) {
        s.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/buscar")

    public ResponseEntity<?> buscar(@RequestParam(required = false)String nombre, @RequestParam(required = false)Long categoriaId, @RequestParam(required = false)Boolean stockBajo, @RequestParam(defaultValue = "nombre")String orden, @RequestParam(defaultValue = "asc")String dir) {
        return ResponseEntity.ok(s.buscar(nombre, categoriaId, stockBajo, orden, dir));
    }
}
