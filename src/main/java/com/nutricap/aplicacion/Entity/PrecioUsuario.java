package com.nutricap.aplicacion.Entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "precios_usuario", indexes = {
        @Index(name = "idx_precios_usuario_uid", columnList = "usuario_id"),
        @Index(name = "idx_precios_usuario_vbase", columnList = "variante_base_id"),
        @Index(name = "idx_precios_usuario_vusuario", columnList = "variante_usuario_id")
})
public class PrecioUsuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "variante_base_id")
    private VarianteBase varianteBase;

    @ManyToOne
    @JoinColumn(name = "variante_usuario_id")
    private VarianteUsuario varianteUsuario;

    private Double precioKg;

    @Column(nullable = false)
    private LocalDateTime actualizado = LocalDateTime.now();
}