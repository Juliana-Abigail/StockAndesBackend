package pe.edu.upeu.PharmaBackend.exception;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import pe.edu.upeu.PharmaBackend.dto.ErrorResponseDTO;
import java.time.LocalDateTime;
import java.util.*;

@RestControllerAdvice

@Slf4j

public class GlobalExceptionHandler {

    private ErrorResponseDTO e(HttpStatus s, String m, String p, Map<String, String> v) {
        return new ErrorResponseDTO(LocalDateTime.now(), s.value(), s.getReasonPhrase(), m, p, v);
    }

    @ExceptionHandler(RecursosNoEncontradoException.class) ResponseEntity<ErrorResponseDTO> nf(RecursosNoEncontradoException x, HttpServletRequest r) {
        return ResponseEntity.status(404).body(e(HttpStatus.NOT_FOUND, x.getMessage(), r.getRequestURI(), null));
    }

    @ExceptionHandler(ReglaNegocioException.class) ResponseEntity<ErrorResponseDTO> rn(ReglaNegocioException x, HttpServletRequest r) {
        return ResponseEntity.status(409).body(e(HttpStatus.CONFLICT, x.getMessage(), r.getRequestURI(), null));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class) ResponseEntity<ErrorResponseDTO> val(MethodArgumentNotValidException x, HttpServletRequest r) {
        Map<String, String> v = new LinkedHashMap<>();
        x.getBindingResult().getFieldErrors().forEach(a->v.put(a.getField(), a.getDefaultMessage()));
        return ResponseEntity.badRequest().body(e(HttpStatus.BAD_REQUEST, "Existen errores de validacion", r.getRequestURI(), v));
    }

    @ExceptionHandler(Exception.class) ResponseEntity<ErrorResponseDTO> gen(Exception x, HttpServletRequest r) {
        log.error("Error no controlado", x);
        return ResponseEntity.status(500).body(e(HttpStatus.INTERNAL_SERVER_ERROR, "Error interno del servidor", r.getRequestURI(), null));
    }
}
