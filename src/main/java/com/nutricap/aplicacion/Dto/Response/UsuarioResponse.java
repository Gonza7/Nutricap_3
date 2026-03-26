package com.nutricap.aplicacion.Dto.Response;

import com.nutricap.aplicacion.Entity.TipoAcceso;
import lombok.Data;

@Data
public class UsuarioResponse {
    private Long id;
    private String nombre;
    private String email;
    private TipoAcceso tipoAcceso;
}
