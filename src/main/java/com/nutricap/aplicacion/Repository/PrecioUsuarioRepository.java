package com.nutricap.aplicacion.Repository;

import com.nutricap.aplicacion.Entity.PrecioUsuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface PrecioUsuarioRepository extends JpaRepository<PrecioUsuario, Long> {

    List<PrecioUsuario> findByUsuarioId(Long usuarioId);

    Optional<PrecioUsuario> findByUsuarioIdAndVarianteBaseId(
            Long usuarioId, Long varianteBaseId);

    Optional<PrecioUsuario> findByUsuarioIdAndVarianteUsuarioId(
            Long usuarioId, Long varianteUsuarioId);

    Optional<PrecioUsuario> findByIdAndUsuarioId(Long id, Long usuarioId);
}