package com.nutricap.aplicacion.Dto.Response;

import lombok.Data;

@Data
public class LoginResponse {
    private String token;
    private String tipoAcceso;
    private String nombre;
    private String email;
}
