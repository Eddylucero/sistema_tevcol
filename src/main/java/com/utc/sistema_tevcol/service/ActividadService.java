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

        if (actividad.getCodigoActividad() == null) {
            throw new RuntimeException("Debe ingresar el código de la actividad");
        }

        if (actividad.getFechaIniActividad() != null &&
                actividad.getFechaMaxActividad() != null &&
                actividad.getFechaMaxActividad().isBefore(actividad.getFechaIniActividad())) {

            throw new RuntimeException("La fecha máxima no puede ser menor a la fecha inicial");
        }

        if (actividad.getAccion() == null ||
                actividad.getAccion().getCodigoAccion() == null) {

            throw new RuntimeException("Debe seleccionar una acción válida");
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
}