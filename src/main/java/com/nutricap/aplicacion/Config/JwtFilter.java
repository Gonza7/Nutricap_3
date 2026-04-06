package com.nutricap.aplicacion.Config;

import com.nutricap.aplicacion.Entity.Usuario;
import com.nutricap.aplicacion.Service.UsuarioService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import java.util.List;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UsuarioService usuarioService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        // 1. Leer el header Authorization
        String authHeader = request.getHeader("Authorization");

        // 2. Si no hay token o no empieza con "Bearer ", dejar pasar
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // 3. Extraer el token — sacar el "Bearer " del principio
        String token = authHeader.substring(7);

        // 4. Verificar que el token sea válido
        if (!jwtService.isValid(token)) {
            filterChain.doFilter(request, response);
            return;
        }

        // 5. Extraer el email y tipo de acceso del token
        String email = jwtService.getEmail(token);
        String tipoAcceso = jwtService.getTipoAcceso(token);

        // 6. Verificar que el usuario existe en la base de datos
        Usuario usuario = usuarioService.findUsuarioByEmail(email).orElse(null);
        if (usuario == null) {
            filterChain.doFilter(request, response);
            return;
        }

        // 7. Crear la autenticación con el rol del usuario
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + tipoAcceso);
        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(
                        usuario,
                        null,
                        List.of(authority)
                );
        authentication.setDetails(
                new WebAuthenticationDetailsSource().buildDetails(request)
        );

        // 8. Registrar la autenticación en Spring Security
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // 9. Dejar pasar el request
        filterChain.doFilter(request, response);
    }
}