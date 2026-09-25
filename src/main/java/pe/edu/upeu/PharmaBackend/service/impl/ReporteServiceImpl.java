package pe.edu.upeu.PharmaBackend.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pe.edu.upeu.PharmaBackend.dto.ProductoDespachadoDTO;
import pe.edu.upeu.PharmaBackend.exception.ReglaNegocioException;
import pe.edu.upeu.PharmaBackend.repository.DespachoRepository;
import pe.edu.upeu.PharmaBackend.service.service.ReporteService;
import java.time.*;
import java.time.format.*;
import java.util.*;

@Service

@Slf4j

public class ReporteServiceImpl implements ReporteService {
    private final DespachoRepository repo;

    public ReporteServiceImpl(DespachoRepository r) {
        repo = r;
    }

    public List<ProductoDespachadoDTO> productosDespachados(String periodo, Long categoriaId) {
        try {
            YearMonth ym = YearMonth.parse(periodo);
            LocalDateTime i = ym.atDay(1).atStartOfDay(), f = ym.plusMonths(1).atDay(1).atStartOfDay();
            log.info("Reporte productos despachados periodo = {} categoria = {}", periodo, categoriaId);
            return repo.reporte(i, f, categoriaId);
        }
        catch (DateTimeParseException e) {
            throw new ReglaNegocioException("periodo debe tener formato AAAA-MM");
        }
    }
}
