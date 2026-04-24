package com.nutricap.aplicacion.Entity;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Data
@Entity
@Table(name = "usuarios", indexes = {
        @Index(name = "idx_usuarios_email", columnList = "email"),
        @Index(name = "idx_usuarios_tipo_acceso", columnList = "tipo_acceso")
})
public class Usuario implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String passwordHash;

    private String nombre;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoAcceso tipoAcceso = TipoAcceso.GRATUITO;

    private LocalDateTime fechaRegistro = LocalDateTime.now();

    // ── Métodos de UserDetails ──

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + tipoAcceso.name()));
    }

    @Override
    public String getPassword() {
        return passwordHash;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}