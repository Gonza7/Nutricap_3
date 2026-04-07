package com.nutricap.aplicacion.Service;

import com.nutricap.aplicacion.Dto.Request.TipoAlimentoRequest;
import com.nutricap.aplicacion.Dto.Response.TipoAlimentoResponse;
import com.nutricap.aplicacion.Entity.TipoAlimento;
import com.nutricap.aplicacion.Repository.TipoAlimentoRepository;
import com.nutricap.aplicacion.Service.TipoAlimentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TipoAlimentoServiceImpl implements TipoAlimentoService {

    @Autowired
    private TipoAlimentoRepository tipoAlimentoRepository;

    // Convierte entidad → DTO de respuesta
    private TipoAlimentoResponse toResponse(TipoAlimento tipo) {
        TipoAlimentoResponse response = new TipoAlimentoResponse();
        response.setId(tipo.getId());
        response.setNombre(tipo.getNombre());
        response.setCategoria(tipo.getCategoria());
        return response;
    }

    @Override
    public List<TipoAlimentoResponse> listTipoAlimento() {
        return tipoAlimentoRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    public List<TipoAlimentoResponse> listTipoAlimentoByCategoria(String categoria) {
        return tipoAlimentoRepository.findByCategoria(categoria)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    public TipoAlimentoResponse createTipoAlimento(TipoAlimentoRequest request) {
        if (tipoAlimentoRepository.existsByNombreAndCategoria(
                request.getNombre(), request.getCategoria())) {
            throw new RuntimeException("Ya existe ese tipo de alimento");
        }
        TipoAlimento tipo = new TipoAlimento();
        tipo.setNombre(request.getNombre());
        tipo.setCategoria(request.getCategoria());
        return toResponse(tipoAlimentoRepository.save(tipo));
    }

    @Override
    public TipoAlimentoResponse updateTipoAlimento(Long id, TipoAlimentoRequest request) {
        TipoAlimento tipo = tipoAlimentoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tipo de alimento no encontrado"));
        tipo.setNombre(request.getNombre());
        tipo.setCategoria(request.getCategoria());
        return toResponse(tipoAlimentoRepository.save(tipo));
    }

    @Override
    public void deleteTipoAlimento(Long id) {
        if (!tipoAlimentoRepository.existsById(id)) {
            throw new RuntimeException("Tipo de alimento no encontrado");
        }
        tipoAlimentoRepository.deleteById(id);
    }
}

