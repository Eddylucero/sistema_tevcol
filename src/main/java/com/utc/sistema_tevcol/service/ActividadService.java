package com.utc.sistema_tevcol.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.utc.sistema_tevcol.entity.ActividadAccion;
import com.utc.sistema_tevcol.entity.AccionPlanAmbiental;
import com.utc.sistema_tevcol.repository.ActividadRepository;
import com.utc.sistema_tevcol.repository.AccionRepository;

@Service
public class ActividadService {

    private final ActividadRepository actividadRepo;
    private final AccionRepository accionRepo;

    public ActividadService(ActividadRepository actividadRepo,
            AccionRepository accionRepo) {
        this.actividadRepo = actividadRepo;
        this.accionRepo = accionRepo;
    }

    public ActividadAccion guardar(ActividadAccion actividad) {

        // Validar fechas
        if (actividad.getFechaIniActividad() != null &&
                actividad.getFechaMaxActividad() != null &&
                actividad.getFechaMaxActividad().isBefore(actividad.getFechaIniActividad())) {

            throw new RuntimeException("La fecha máxima no puede ser menor a la fecha inicial");
        }

        // Validar acción
        if (actividad.getAccion() == null ||
                actividad.getAccion().getCodigoAccion() == null) {

            throw new RuntimeException("Debe seleccionar una acción válida");
        }

        // Validar número de actividad duplicado
        if (actividad.getNumeroActividad() != null) {
            boolean existeNumero;

            if (actividad.getCodigoActividad() == null) {
                // Es una actividad nueva
                existeNumero = actividadRepo.existsByNumeroActividad(actividad.getNumeroActividad());
                if (existeNumero) {
                    throw new RuntimeException("El número de actividad " + actividad.getNumeroActividad() +
                            " ya existe. El último número registrado es: " + obtenerUltimoNumeroActividad());
                }
            } else {
                // Es una edición
                existeNumero = actividadRepo.existsByNumeroActividadAndCodigoActividadNot(
                        actividad.getNumeroActividad(),
                        actividad.getCodigoActividad());
                if (existeNumero) {
                    throw new RuntimeException("El número de actividad " + actividad.getNumeroActividad() +
                            " ya está siendo usado por otra actividad");
                }
            }
        }

        AccionPlanAmbiental accion = accionRepo.findById(
                actividad.getAccion().getCodigoAccion())
                .orElseThrow(() -> new RuntimeException("Acción no encontrada"));

        actividad.setAccion(accion);

        return actividadRepo.save(actividad);
    }

    public List<ActividadAccion> listar() {
        return actividadRepo.findAll();
    }

    public List<ActividadAccion> listarPorAccion(Long codigoAccion) {
        return actividadRepo.findByAccionCodigoAccion(codigoAccion);
    }

    public ActividadAccion buscarPorId(Long id) {
        return actividadRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Actividad no encontrada"));
    }

    public void eliminar(Long id) {
        actividadRepo.deleteById(id);
    }

    // Método para obtener el último número de actividad
    public Integer obtenerUltimoNumeroActividad() {
        return actividadRepo.findMaxNumeroActividad()
                .map(max -> max + 1)
                .orElse(1); // Si no hay actividades, empieza en 1
    }
}