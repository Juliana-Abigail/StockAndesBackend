package pe.edu.upeu.PharmaBackend.controller;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import pe.edu.upeu.PharmaBackend.service.service.ReporteService;

@RestController

@RequestMapping("/api/v1/reportes")

public class ReporteController {
    private final ReporteService s;

    public ReporteController(ReporteService s) {
        this.s = s;
    }

    @GetMapping("/productos-despachados")

    public ResponseEntity<?> reporte(@RequestParam String periodo, @RequestParam(required = false)Long categoriaId) {
        return ResponseEntity.ok(s.productosDespachados(periodo, categoriaId));
    }
}
