package com.nutricap.aplicacion.Controller;


import com.nutricap.aplicacion.Dto.Request.TipoAlimentoRequest;
import com.nutricap.aplicacion.Dto.Response.TipoAlimentoResponse;
import com.nutricap.aplicacion.Service.TipoAlimentoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/tipos-alimento")
public class TipoAlimentoController {

    @Autowired
    private TipoAlimentoService tipoAlimentoService;

    // Todos los usuarios pueden ver los tipos
    @GetMapping
    public List<TipoAlimentoResponse> listTipoAlimento() {
        return tipoAlimentoService.listTipoAlimento();
    }

    // Filtrar por categoría
    @GetMapping("/categoria/{categoria}")
    public List<TipoAlimentoResponse> listTipoAlimentoByCategoria(
            @PathVariable String categoria) {
        return tipoAlimentoService.listTipoAlimentoByCategoria(categoria);
    }

    // Solo el admin puede crear
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> createTipoAlimento(@Valid @RequestBody TipoAlimentoRequest request) {
        try {
            return ResponseEntity.ok(tipoAlimentoService.createTipoAlimento(request));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    // Solo el admin puede editar
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateTipoAlimento(@PathVariable Long id,
                                        @Valid @RequestBody TipoAlimentoRequest request) {
        try {
            return ResponseEntity.ok(tipoAlimentoService.updateTipoAlimento(id, request));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    // Solo el admin puede eliminar
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteTipoAlimento(@PathVariable Long id) {
        try {
            tipoAlimentoService.deleteTipoAlimento(id);
            return ResponseEntity.ok(Map.of("mensaje", "Eliminado correctamente"));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
}