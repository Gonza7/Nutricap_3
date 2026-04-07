package com.nutricap.aplicacion.Repository;

import com.nutricap.aplicacion.Entity.TipoAlimento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TipoAlimentoRepository extends JpaRepository<TipoAlimento, Long> {

    List<TipoAlimento> findByCategoria(String categoria);

    boolean existsByNombreAndCategoria(String nombre, String categoria);
}