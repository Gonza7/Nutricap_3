package com.nutricap.aplicacion.Entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;
import java.util.Map;

@Data
@Entity
@Table(name = "variantes_usuario")
public class VarianteUsuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "tipo_id", nullable = false)
    private TipoAlimento tipo;

    private String forma;
    private String momento;

    private Double ms;
    private Double em;
    private Double pb;
    private Double fdn;
    private Double calcio;
    private Double fosforo;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "jsonb")
    private Map<String, Object> otrosNutrientes;

    @Column(nullable = false)
    private Boolean softDelete = false;
}