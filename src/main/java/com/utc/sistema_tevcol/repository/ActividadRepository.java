package com.utc.sistema_tevcol.repository;

import com.utc.sistema_tevcol.entity.ActividadAccion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ActividadRepository extends JpaRepository<ActividadAccion, Long> {

    // Buscar actividades por código de acción
    List<ActividadAccion> findByAccionCodigoAccion(Long codigoAccion);

    // Buscar actividades por estado
    List<ActividadAccion> findByEstadoActividad(String estadoActividad);

    // Verificar si existe un número de actividad
    boolean existsByNumeroActividad(Integer numeroActividad);

    // Verificar si existe un número de actividad excluyendo un ID específico (para
    // edición)
    boolean existsByNumeroActividadAndCodigoActividadNot(Integer numeroActividad, Long codigoActividad);

    // Obtener el máximo número de actividad
    @Query("SELECT MAX(a.numeroActividad) FROM ActividadAccion a")
    Optional<Integer> findMaxNumeroActividad();
}