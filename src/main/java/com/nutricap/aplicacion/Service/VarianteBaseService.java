package com.nutricap.aplicacion.Service;

import com.nutricap.aplicacion.Dto.Request.VarianteBaseRequest;
import com.nutricap.aplicacion.Dto.Response.VarianteBaseResponse;

import java.util.List;

public interface VarianteBaseService {

    List<VarianteBaseResponse> listVarianteBase();

    List<VarianteBaseResponse> listVarianteBaseActive();

    List<VarianteBaseResponse> listVarianteBaseByTipo(Long tipoId);

    VarianteBaseResponse createVarianteBase(VarianteBaseRequest request);

    VarianteBaseResponse updateVarianteBase(Long id, VarianteBaseRequest request);

    VarianteBaseResponse toggleSoftDelete(Long id);

    void deleteVarianteBase(Long id);
}
