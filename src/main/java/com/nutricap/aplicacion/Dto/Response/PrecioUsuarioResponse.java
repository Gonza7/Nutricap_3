package com.nutricap.aplicacion.Dto.Response;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class PrecioUsuarioResponse {

    private Long id;
    private Long usuarioId;
    private Long varianteBaseId;
    private String varianteBaseNombre;
    private Long varianteUsuarioId;
    private String varianteUsuarioNombre;
    private Double precioKg;
    private LocalDateTime actualizado;
}