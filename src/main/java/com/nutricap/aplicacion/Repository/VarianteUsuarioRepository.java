package com.nutricap.aplicacion.Repository;

import com.nutricap.aplicacion.Entity.VarianteUsuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface VarianteUsuarioRepository extends JpaRepository<VarianteUsuario, Long> {

    List<VarianteUsuario> findByUsuarioIdAndSoftDelete(Long usuarioId, Boolean softDelete);

    List<VarianteUsuario> findByUsuarioIdAndTipoIdAndSoftDelete(Long usuarioId, Long tipoId, Boolean softDelete);

    Optional<VarianteUsuario> findByIdAndUsuarioId(Long id, Long usuarioId);
}
