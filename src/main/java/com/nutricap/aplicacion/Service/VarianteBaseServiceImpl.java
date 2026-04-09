package com.nutricap.aplicacion.Service;

import com.nutricap.aplicacion.Dto.Request.VarianteBaseRequest;
import com.nutricap.aplicacion.Dto.Response.VarianteBaseResponse;
import com.nutricap.aplicacion.Entity.TipoAlimento;
import com.nutricap.aplicacion.Entity.VarianteBase;
import com.nutricap.aplicacion.Repository.TipoAlimentoRepository;
import com.nutricap.aplicacion.Repository.VarianteBaseRepository;
import com.nutricap.aplicacion.Service.VarianteBaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VarianteBaseServiceImpl implements VarianteBaseService {

    @Autowired
    private VarianteBaseRepository varianteBaseRepository;

    @Autowired
    private TipoAlimentoRepository tipoAlimentoRepository;

    private VarianteBaseResponse toResponse(VarianteBase v) {
        VarianteBaseResponse r = new VarianteBaseResponse();
        r.setId(v.getId());
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

    private VarianteBase toEntity(VarianteBaseRequest request, VarianteBase variante) {
        TipoAlimento tipo = tipoAlimentoRepository.findById(request.getTipoId())
                .orElseThrow(() -> new RuntimeException("Tipo de alimento no encontrado"));
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
        variante.setSoftDelete(request.getSoftDelete() != null ? request.getSoftDelete() : false);
        return variante;
    }

    @Override
    public List<VarianteBaseResponse> listVarianteBase() {
        return varianteBaseRepository.findAll()
                .stream().map(this::toResponse).toList();
    }

    @Override
    public List<VarianteBaseResponse> listVarianteBaseActive() {
        return varianteBaseRepository.findBySoftDelete(false)
                .stream().map(this::toResponse).toList();
    }

    @Override
    public List<VarianteBaseResponse> listVarianteBaseByTipo(Long tipoId) {
        return varianteBaseRepository.findByTipoIdAndSoftDelete(tipoId, false)
                .stream().map(this::toResponse).toList();
    }

    @Override
    public VarianteBaseResponse createVarianteBase(VarianteBaseRequest request) {
        VarianteBase variante = toEntity(request, new VarianteBase());
        return toResponse(varianteBaseRepository.save(variante));
    }

    @Override
    public VarianteBaseResponse updateVarianteBase(Long id, VarianteBaseRequest request) {
        VarianteBase variante = varianteBaseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Variante no encontrada"));
        toEntity(request, variante);
        return toResponse(varianteBaseRepository.save(variante));
    }

    @Override
    public VarianteBaseResponse toggleSoftDelete(Long id) {
        VarianteBase variante = varianteBaseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Variante no encontrada"));
        variante.setSoftDelete(!variante.getSoftDelete());
        return toResponse(varianteBaseRepository.save(variante));
    }

    @Override
    public void deleteVarianteBase(Long id) {
        if (!varianteBaseRepository.existsById(id)) {
            throw new RuntimeException("Variante no encontrada");
        }
        varianteBaseRepository.deleteById(id);
    }
}
