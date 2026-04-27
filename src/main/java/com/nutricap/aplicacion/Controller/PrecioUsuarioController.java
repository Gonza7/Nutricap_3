package com.nutricap.aplicacion.Controller;

import com.nutricap.aplicacion.Dto.Request.PrecioUsuarioRequest;
import com.nutricap.aplicacion.Dto.Response.PrecioUsuarioResponse;
import com.nutricap.aplicacion.Entity.Usuario;
import com.nutricap.aplicacion.Service.PrecioUsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/precios")
public class PrecioUsuarioController {

    @Autowired
    private PrecioUsuarioService precioUsuarioService;

    @GetMapping
    public List<PrecioUsuarioResponse> listPrecioUsuario(
            @AuthenticationPrincipal Usuario usuario) {
        return precioUsuarioService.listPrecioUsuario(usuario.getId());
    }

    @PostMapping
    public ResponseEntity<?> createPrecioUsuario(
            @AuthenticationPrincipal Usuario usuario,
            @Valid @RequestBody PrecioUsuarioRequest request) {
        try {
            return ResponseEntity.ok(
                    precioUsuarioService.createPrecioUsuario(usuario.getId(), request)
            );
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updatePrecioUsuario(
            @AuthenticationPrincipal Usuario usuario,
            @PathVariable Long id,
            @Valid @RequestBody PrecioUsuarioRequest request) {
        try {
            return ResponseEntity.ok(
                    precioUsuarioService.updatePrecioUsuario(id, usuario.getId(), request)
            );
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePrecioUsuario(
            @AuthenticationPrincipal Usuario usuario,
            @PathVariable Long id) {
        try {
            precioUsuarioService.deletePrecioUsuario(id, usuario.getId());
            return ResponseEntity.ok(Map.of("mensaje", "Precio eliminado correctamente"));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
}