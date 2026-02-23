package com.utc.sistema_tevcol.repository;

import com.utc.sistema_tevcol.entity.ActividadAccion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActividadRepository extends JpaRepository<ActividadAccion, Long> {

    // Buscar actividades por código de acción
    List<ActividadAccion> findByAccionCodigoAccion(Long codigoAccion);

    // Buscar actividades por estado
    List<ActividadAccion> findByEstadoActividad(String estadoActividad);

}