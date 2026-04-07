package com.nutricap.aplicacion.Dto.Request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class TipoAlimentoRequest {

    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    @NotBlank(message = "La categoría es obligatoria")
    private String categoria;
}