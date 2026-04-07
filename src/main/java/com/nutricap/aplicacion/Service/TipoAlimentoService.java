package com.nutricap.aplicacion.Service;

import com.nutricap.aplicacion.Dto.Request.TipoAlimentoRequest;
import com.nutricap.aplicacion.Dto.Response.TipoAlimentoResponse;
import java.util.List;

public interface TipoAlimentoService {

    List<TipoAlimentoResponse> listTipoAlimento();

    List<TipoAlimentoResponse> listTipoAlimentoByCategoria(String categoria);

    TipoAlimentoResponse createTipoAlimento(TipoAlimentoRequest request);

    TipoAlimentoResponse updateTipoAlimento(Long id, TipoAlimentoRequest request);

    void deleteTipoAlimento(Long id);
}