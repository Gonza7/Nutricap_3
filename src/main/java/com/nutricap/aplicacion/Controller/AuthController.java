package com.nutricap.aplicacion.Controller;

import com.nutricap.aplicacion.Dto.Request.LoginRequest;
import com.nutricap.aplicacion.Dto.Request.RegisterRequest;
import com.nutricap.aplicacion.Dto.Response.LoginResponse;
import com.nutricap.aplicacion.Dto.Response.UsuarioResponse;
import com.nutricap.aplicacion.Entity.Usuario;
import com.nutricap.aplicacion.Service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest request) {
        try {
            Usuario usuario = usuarioService.register(
                    request.getNombre(),
                    request.getEmail(),
                    request.getPassword()
            );
            UsuarioResponse response = new UsuarioResponse();
            response.setId(usuario.getId());
            response.setNombre(usuario.getNombre());
            response.setEmail(usuario.getEmail());
            response.setTipoAcceso(usuario.getTipoAcceso());

            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(
                    Map.of("error", e.getMessage())
            );
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest request) {
        try {
            String token = usuarioService.login(
                    request.getEmail(),
                    request.getPassword()
            );
            Usuario usuario = usuarioService.findUsuarioByEmail(request.getEmail()).get();

            LoginResponse response = new LoginResponse();
            response.setToken(token);
            response.setTipoAcceso(usuario.getTipoAcceso().name());
            response.setNombre(usuario.getNombre());
            response.setEmail(usuario.getEmail());

            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(
                    Map.of("error", e.getMessage())
            );
        }
    }
}
