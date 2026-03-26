package com.nutricap.aplicacion.Service;

import com.nutricap.aplicacion.Config.JwtService;
import com.nutricap.aplicacion.Entity.TipoAcceso;
import com.nutricap.aplicacion.Entity.Usuario;
import com.nutricap.aplicacion.Repository.UsuarioRepository;
import com.nutricap.aplicacion.Service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class UsuarioServiceImpl implements UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    @Override
    public Usuario register(String nombre, String email, String password) {
        if (usuarioRepository.existsByEmail(email)) {
            throw new RuntimeException("Ya existe un usuario con ese email");
        }
        Usuario usuario = new Usuario();
        usuario.setNombre(nombre);
        usuario.setEmail(email);
        usuario.setPasswordHash(passwordEncoder.encode(password));
        usuario.setTipoAcceso(TipoAcceso.GRATUITO);
        return usuarioRepository.save(usuario);
    }

    @Override
    public String login(String email, String password) {
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        if (!passwordEncoder.matches(password, usuario.getPasswordHash())) {
            throw new RuntimeException("Contraseña incorrecta");
        }
        return jwtService.generateToken(
                usuario.getEmail(),
                usuario.getTipoAcceso().name()
        );
    }

    @Override
    public Optional<Usuario> findUsuarioByEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }

    @Override
    public Usuario updateTipoAcceso(Long usuarioId, TipoAcceso nuevoTipo) {
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        usuario.setTipoAcceso(nuevoTipo);
        return usuarioRepository.save(usuario);
    }
}
