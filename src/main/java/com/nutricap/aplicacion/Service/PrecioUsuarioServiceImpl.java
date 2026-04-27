package com.nutricap.aplicacion.Service;

import com.nutricap.aplicacion.Dto.Request.PrecioUsuarioRequest;
import com.nutricap.aplicacion.Dto.Response.PrecioUsuarioResponse;
import com.nutricap.aplicacion.Entity.PrecioUsuario;
import com.nutricap.aplicacion.Entity.Usuario;
import com.nutricap.aplicacion.Entity.VarianteBase;
import com.nutricap.aplicacion.Entity.VarianteUsuario;
import com.nutricap.aplicacion.Repository.PrecioUsuarioRepository;
import com.nutricap.aplicacion.Repository.UsuarioRepository;
import com.nutricap.aplicacion.Repository.VarianteBaseRepository;
import com.nutricap.aplicacion.Repository.VarianteUsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class PrecioUsuarioServiceImpl implements PrecioUsuarioService {

    @Autowired
    private PrecioUsuarioRepository precioUsuarioRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private VarianteBaseRepository varianteBaseRepository;

    @Autowired
    private VarianteUsuarioRepository varianteUsuarioRepository;

    private PrecioUsuarioResponse toResponse(PrecioUsuario p) {
        PrecioUsuarioResponse r = new PrecioUsuarioResponse();
        r.setId(p.getId());
        r.setUsuarioId(p.getUsuario().getId());
        r.setPrecioKg(p.getPrecioKg());
        r.setActualizado(p.getActualizado());

        if (p.getVarianteBase() != null) {
            r.setVarianteBaseId(p.getVarianteBase().getId());
            r.setVarianteBaseNombre(
                    p.getVarianteBase().getTipo().getNombre() + " · " +
                            p.getVarianteBase().getMomento()
            );
        }

        if (p.getVarianteUsuario() != null) {
            r.setVarianteUsuarioId(p.getVarianteUsuario().getId());
            r.setVarianteUsuarioNombre(
                    p.getVarianteUsuario().getTipo().getNombre() + " · " +
                            p.getVarianteUsuario().getMomento()
            );
        }

        return r;
    }

    @Override
    public List<PrecioUsuarioResponse> listPrecioUsuario(Long usuarioId) {
        return precioUsuarioRepository.findByUsuarioId(usuarioId)
                .stream().map(this::toResponse).toList();
    }

    @Override
    public PrecioUsuarioResponse createPrecioUsuario(Long usuarioId,
                                                     PrecioUsuarioRequest request) {
        // Validar que venga exactamente uno de los dos ids
        if (request.getVarianteBaseId() == null && request.getVarianteUsuarioId() == null) {
            throw new RuntimeException("Debe especificar una variante base o una variante propia");
        }
        if (request.getVarianteBaseId() != null && request.getVarianteUsuarioId() != null) {
            throw new RuntimeException("Solo puede especificar una variante a la vez");
        }

        Usuario usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        PrecioUsuario precio = new PrecioUsuario();
        precio.setUsuario(usuario);
        precio.setPrecioKg(request.getPrecioKg());
        precio.setActualizado(LocalDateTime.now());

        if (request.getVarianteBaseId() != null) {
            // Verificar que no tenga ya un precio para esta variante base
            if (precioUsuarioRepository.findByUsuarioIdAndVarianteBaseId(
                    usuarioId, request.getVarianteBaseId()).isPresent()) {
                throw new RuntimeException(
                        "Ya tiene un precio cargado para esta variante — use actualizar");
            }
            VarianteBase variante = varianteBaseRepository
                    .findById(request.getVarianteBaseId())
                    .orElseThrow(() -> new RuntimeException("Variante base no encontrada"));
            precio.setVarianteBase(variante);
        }

        if (request.getVarianteUsuarioId() != null) {
            // Verificar que no tenga ya un precio para esta variante propia
            if (precioUsuarioRepository.findByUsuarioIdAndVarianteUsuarioId(
                    usuarioId, request.getVarianteUsuarioId()).isPresent()) {
                throw new RuntimeException(
                        "Ya tiene un precio cargado para esta variante — use actualizar");
            }
            VarianteUsuario variante = varianteUsuarioRepository
                    .findByIdAndUsuarioId(request.getVarianteUsuarioId(), usuarioId)
                    .orElseThrow(() -> new RuntimeException("Variante propia no encontrada"));
            precio.setVarianteUsuario(variante);
        }

        return toResponse(precioUsuarioRepository.save(precio));
    }

    @Override
    public PrecioUsuarioResponse updatePrecioUsuario(Long id, Long usuarioId,
                                                     PrecioUsuarioRequest request) {
        PrecioUsuario precio = precioUsuarioRepository
                .findByIdAndUsuarioId(id, usuarioId)
                .orElseThrow(() -> new RuntimeException("Precio no encontrado"));
        precio.setPrecioKg(request.getPrecioKg());
        precio.setActualizado(LocalDateTime.now());
        return toResponse(precioUsuarioRepository.save(precio));
    }

    @Override
    public void deletePrecioUsuario(Long id, Long usuarioId) {
        PrecioUsuario precio = precioUsuarioRepository
                .findByIdAndUsuarioId(id, usuarioId)
                .orElseThrow(() -> new RuntimeException("Precio no encontrado"));
        precioUsuarioRepository.delete(precio);
    }
}