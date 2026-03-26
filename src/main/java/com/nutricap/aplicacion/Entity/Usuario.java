package com.nutricap.aplicacion.Entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "usuarios")
public class Usuario {

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
}