package com.nutricap.aplicacion.Dto.Request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.util.Map;

@Data
public class VarianteUsuarioRequest {

    @NotNull(message = "El tipo de alimento es obligatorio")
    private Long tipoId;

    private String forma;
    private String momento;

    private Double ms;
    private Double em;
    private Double pb;
    private Double fdn;
    private Double calcio;
    private Double fosforo;

    private Map<String, Object> otrosNutrientes;
}