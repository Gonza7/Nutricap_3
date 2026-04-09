package com.nutricap.aplicacion.Dto.Response;

import lombok.Data;

import java.util.Map;

@Data
public class VarianteBaseResponse {

    private Long id;
    private Long tipoId;
    private String tipoNombre;
    private String tipoCategoria;
    private String forma;
    private String momento;
    private Double ms;
    private Double em;
    private Double pb;
    private Double fdn;
    private Double calcio;
    private Double fosforo;
    private Map<String, Object> otrosNutrientes;
    private Boolean softDelete;
}
