package com.nutricap.aplicacion.Entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "tipos_alimento", indexes = {
        @Index(name = "idx_tipos_alimento_categoria", columnList = "categoria"),
        @Index(name = "idx_tipos_alimento_nombre_categoria", columnList = "nombre, categoria")
})
public class TipoAlimento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String categoria;
}
