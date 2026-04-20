package com.nutricap.aplicacion.Service;

import com.nutricap.aplicacion.Dto.Request.VarianteUsuarioRequest;
import com.nutricap.aplicacion.Dto.Response.VarianteUsuarioResponse;
import com.nutricap.aplicacion.Entity.TipoAlimento;
import com.nutricap.aplicacion.Entity.Usuario;
import com.nutricap.aplicacion.Entity.VarianteUsuario;
import com.nutricap.aplicacion.Repository.TipoAlimentoRepository;
import com.nutricap.aplicacion.Repository.UsuarioRepository;
import com.nutricap.aplicacion.Repository.VarianteUsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class VarianteUsuarioServiceImpl implements VarianteUsuarioService {

    @Autowired
    private VarianteUsuarioRepository varianteUsuarioRepository;

    @Autowired
    private TipoAlimentoRepository tipoAlimentoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    private VarianteUsuarioResponse toResponse(VarianteUsuario v) {
        VarianteUsuarioResponse r = new VarianteUsuarioResponse();
        r.setId(v.getId());
        r.setUsuarioId(v.getUsuario().getId());
        r.setTipoId(v.getTipo().getId());
        r.setTipoNombre(v.getTipo().getNombre());
        r.setTipoCategoria(v.getTipo().getCategoria());
        r.setForma(v.getForma());
        r.setMomento(v.getMomento());
        r.setMs(v.getMs());
        r.setEm(v.getEm());
        r.setPb(v.getPb());
        r.setFdn(v.getFdn());
        r.setCalcio(v.getCalcio());
        r.setFosforo(v.getFosforo());
        r.setOtrosNutrientes(v.getOtrosNutrientes());
        r.setSoftDelete(v.getSoftDelete());
        return r;
    }

    private VarianteUsuario toEntity(VarianteUsuarioRequest request,
                                     VarianteUsuario variante,
                                     Long usuarioId) {
        TipoAlimento tipo = tipoAlimentoRepository.findById(request.getTipoId())
                .orElseThrow(() -> new RuntimeException("Tipo de alimento no encontrado"));
        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        variante.setUsuario(usuario);
        variante.setTipo(tipo);
        variante.setForma(request.getForma());
        variante.setMomento(request.getMomento());
        variante.setMs(request.getMs());
        variante.setEm(request.getEm());
        variante.setPb(request.getPb());
        variante.setFdn(request.getFdn());
        variante.setCalcio(request.getCalcio());
        variante.setFosforo(request.getFosforo());
        variante.setOtrosNutrientes(request.getOtrosNutrientes());
        return variante;
    }

    @Override
    public List<VarianteUsuarioResponse> listVarianteUsuarioActive(Long usuarioId) {
        return varianteUsuarioRepository
                .findByUsuarioIdAndSoftDelete(usuarioId, false)
                .stream().map(this::toResponse).toList();
    }

    @Override
    public List<VarianteUsuarioResponse> listVarianteUsuarioByTipo(Long usuarioId, Long tipoId) {
        return varianteUsuarioRepository
                .findByUsuarioIdAndTipoIdAndSoftDelete(usuarioId, tipoId, false)
                .stream().map(this::toResponse).toList();
    }

    @Override
    public VarianteUsuarioResponse createVarianteUsuario(Long usuarioId,
                                                         VarianteUsuarioRequest request) {
        VarianteUsuario variante = new VarianteUsuario();
        variante.setSoftDelete(false);
        toEntity(request, variante, usuarioId);
        return toResponse(varianteUsuarioRepository.save(variante));
    }

    @Override
    public VarianteUsuarioResponse updateVarianteUsuario(Long id, Long usuarioId,
                                                         VarianteUsuarioRequest request) {
        VarianteUsuario variante = varianteUsuarioRepository
                .findByIdAndUsuarioId(id, usuarioId)
                .orElseThrow(() -> new RuntimeException("Variante no encontrada"));
        Boolean softDeleteActual = variante.getSoftDelete();
        toEntity(request, variante, usuarioId);
        variante.setSoftDelete(softDeleteActual);
        return toResponse(varianteUsuarioRepository.save(variante));
    }

    @Override
    public VarianteUsuarioResponse toggleVarianteUsuarioSoftDelete(Long id, Long usuarioId) {
        VarianteUsuario variante = varianteUsuarioRepository
                .findByIdAndUsuarioId(id, usuarioId)
                .orElseThrow(() -> new RuntimeException("Variante no encontrada"));
        variante.setSoftDelete(!variante.getSoftDelete());
        return toResponse(varianteUsuarioRepository.save(variante));
    }

    @Override
    public void deleteVarianteUsuario(Long id, Long usuarioId) {
        VarianteUsuario variante = varianteUsuarioRepository
                .findByIdAndUsuarioId(id, usuarioId)
                .orElseThrow(() -> new RuntimeException("Variante no encontrada"));
        varianteUsuarioRepository.delete(variante);
    }
}