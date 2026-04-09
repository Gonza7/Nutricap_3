package com.nutricap.aplicacion.Controller;


import com.nutricap.aplicacion.Dto.Request.VarianteBaseRequest;
import com.nutricap.aplicacion.Dto.Response.VarianteBaseResponse;
import com.nutricap.aplicacion.Service.VarianteBaseService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/variantes-base")
public class VarianteBaseController {

    @Autowired
    private VarianteBaseService varianteBaseService;

    // Todos los usuarios ven las variantes visibles
    @GetMapping
    public List<VarianteBaseResponse> listVarianteBaseActive() {
        return varianteBaseService.listVarianteBaseActive();
    }

    // Filtrar por tipo de alimento
    @GetMapping("/tipo/{tipoId}")
    public List<VarianteBaseResponse> listVarianteBaseByTipo(@PathVariable Long tipoId) {
        return varianteBaseService.listVarianteBaseByTipo(tipoId);
    }

    // Solo admin ve todas incluyendo las ocultas
    @GetMapping("/todas")
    @PreAuthorize("hasRole('ADMIN')")
    public List<VarianteBaseResponse> listVarianteBase() {
        return varianteBaseService.listVarianteBase();
    }

    // Solo admin crea
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> createVarianteBase(@Valid @RequestBody VarianteBaseRequest request) {
        try {
            return ResponseEntity.ok(varianteBaseService.createVarianteBase(request));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    // Solo admin edita
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateVarianteBase(@PathVariable Long id,
                                        @Valid @RequestBody VarianteBaseRequest request) {
        try {
            return ResponseEntity.ok(varianteBaseService.updateVarianteBase(id, request));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    // Solo admin puede ocultar/mostrar
    @PatchMapping("/{id}/visibilidad")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> toggleSoftDelete(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(varianteBaseService.toggleSoftDelete(id));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    // Solo admin elimina
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteVarianteBase(@PathVariable Long id) {
        try {
            varianteBaseService.deleteVarianteBase(id);
            return ResponseEntity.ok(Map.of("mensaje", "Eliminada correctamente"));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
}