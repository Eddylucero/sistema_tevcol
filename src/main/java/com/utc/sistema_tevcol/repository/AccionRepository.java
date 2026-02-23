package com.utc.sistema_tevcol.repository;

import com.utc.sistema_tevcol.entity.AccionPlanAmbiental;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccionRepository extends JpaRepository<AccionPlanAmbiental, Long> {

    // Buscar acciones por estado
    List<AccionPlanAmbiental> findByEstadoAplica(Integer estadoAplica);

    // Buscar acciones por sección
    List<AccionPlanAmbiental> findByFkCodSeccion(Long fkCodSeccion);

    // Buscar acciones por estado y sección
    List<AccionPlanAmbiental> findByEstadoAplicaAndFkCodSeccion(Integer estadoAplica, Long fkCodSeccion);

}