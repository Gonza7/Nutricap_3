package com.nutricap.aplicacion.Service;

import com.nutricap.aplicacion.Entity.TipoAcceso;
import com.nutricap.aplicacion.Entity.Usuario;

import java.util.Optional;

public interface UsuarioService {
    Usuario register(String nombre, String email, String password);
    String login(String email, String password);
    Optional<Usuario> findUsuarioByEmail(String email);
    Usuario updateTipoAcceso(Long usuarioId, TipoAcceso tipoAcceso);
}
