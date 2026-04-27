package com.nutricap.aplicacion.Service;

import com.nutricap.aplicacion.Dto.Request.PrecioUsuarioRequest;
import com.nutricap.aplicacion.Dto.Response.PrecioUsuarioResponse;
import java.util.List;

public interface PrecioUsuarioService {

    List<PrecioUsuarioResponse> listPrecioUsuario(Long usuarioId);

    PrecioUsuarioResponse createPrecioUsuario(Long usuarioId, PrecioUsuarioRequest request);

    PrecioUsuarioResponse updatePrecioUsuario(Long id, Long usuarioId, PrecioUsuarioRequest request);

    void deletePrecioUsuario(Long id, Long usuarioId);
}