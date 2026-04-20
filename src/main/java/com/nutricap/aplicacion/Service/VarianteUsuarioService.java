package com.nutricap.aplicacion.Service;

import com.nutricap.aplicacion.Dto.Request.VarianteUsuarioRequest;
import com.nutricap.aplicacion.Dto.Response.VarianteUsuarioResponse;
import java.util.List;

public interface VarianteUsuarioService {

    List<VarianteUsuarioResponse> listVarianteUsuarioActive(Long usuarioId);

    List<VarianteUsuarioResponse> listVarianteUsuarioByTipo(Long usuarioId, Long tipoId);

    VarianteUsuarioResponse createVarianteUsuario(Long usuarioId, VarianteUsuarioRequest request);

    VarianteUsuarioResponse updateVarianteUsuario(Long id, Long usuarioId, VarianteUsuarioRequest request);

    VarianteUsuarioResponse toggleVarianteUsuarioSoftDelete(Long id, Long usuarioId);

    void deleteVarianteUsuario(Long id, Long usuarioId);
}