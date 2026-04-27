package com.nutricap.aplicacion.Dto.Request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class PrecioUsuarioRequest {

    // Solo uno de los dos puede venir — base o usuario
    private Long varianteBaseId;
    private Long varianteUsuarioId;

    @NotNull(message = "El precio es obligatorio")
    @Positive(message = "El precio debe ser mayor a cero")
    private Double precioKg;
}