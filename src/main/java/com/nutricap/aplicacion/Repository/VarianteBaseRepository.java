package com.nutricap.aplicacion.Repository;

import com.nutricap.aplicacion.Entity.VarianteBase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VarianteBaseRepository extends JpaRepository<VarianteBase, Long> {

    List<VarianteBase> findByTipoId(Long tipoId);

    List<VarianteBase> findBySoftDelete(Boolean softDelete);

    List<VarianteBase> findByTipoIdAndSoftDelete(Long tipoId, Boolean softDelete);
}