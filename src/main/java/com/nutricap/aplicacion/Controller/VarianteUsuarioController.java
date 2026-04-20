package com.nutricap.aplicacion.Controller;

import com.nutricap.aplicacion.Dto.Request.VarianteUsuarioRequest;
import com.nutricap.aplicacion.Dto.Response.VarianteUsuarioResponse;
import com.nutricap.aplicacion.Entity.Usuario;
import com.nutricap.aplicacion.Service.VarianteUsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/variantes-usuario")
public class VarianteUsuarioController {

    @Autowired
    private VarianteUsuarioService varianteUsuarioService;

    @GetMapping
    public List<VarianteUsuarioResponse> listVarianteUsuarioActive(
            @AuthenticationPrincipal Usuario usuario) {
        return varianteUsuarioService.listVarianteUsuarioActive(usuario.getId());
    }

    @GetMapping("/tipo/{tipoId}")
    public List<VarianteUsuarioResponse> listVarianteUsuarioByTipo(
            @AuthenticationPrincipal Usuario usuario,
            @PathVariable Long tipoId) {
        return varianteUsuarioService.listVarianteUsuarioByTipo(usuario.getId(), tipoId);
    }

    @PostMapping
    public ResponseEntity<?> createVarianteUsuario(
            @AuthenticationPrincipal Usuario usuario,
            @Valid @RequestBody VarianteUsuarioRequest request) {
        try {
            return ResponseEntity.ok(
                    varianteUsuarioService.createVarianteUsuario(usuario.getId(), request)
            );
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateVarianteUsuario(
            @AuthenticationPrincipal Usuario usuario,
            @PathVariable Long id,
            @Valid @RequestBody VarianteUsuarioRequest request) {
        try {
            return ResponseEntity.ok(
                    varianteUsuarioService.updateVarianteUsuario(id, usuario.getId(), request)
            );
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @PatchMapping("/{id}/softdelete")
    public ResponseEntity<?> toggleVarianteUsuarioSoftDelete(
            @AuthenticationPrincipal Usuario usuario,
            @PathVariable Long id) {
        try {
            return ResponseEntity.ok(
                    varianteUsuarioService.toggleVarianteUsuarioSoftDelete(id, usuario.getId())
            );
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteVarianteUsuario(
            @AuthenticationPrincipal Usuario usuario,
            @PathVariable Long id) {
        try {
            varianteUsuarioService.deleteVarianteUsuario(id, usuario.getId());
            return ResponseEntity.ok(Map.of("mensaje", "Eliminada correctamente"));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
}