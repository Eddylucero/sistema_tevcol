package com.utc.sistema_tevcol.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.utc.sistema_tevcol.entity.AccionPlanAmbiental;
import com.utc.sistema_tevcol.repository.AccionRepository;

@Service
public class AccionService {

    private final AccionRepository accionRepo;

    public AccionService(AccionRepository accionRepo) {
        this.accionRepo = accionRepo;
    }

    public AccionPlanAmbiental guardar(AccionPlanAmbiental accion) {

        if (accion.getCodigoAccion() == null) {
            throw new RuntimeException("Debe ingresar el código de la acción");
        }

        if (accion.getAspectoAmbientalAccion() == null ||
            accion.getAspectoAmbientalAccion().isEmpty()) {

            throw new RuntimeException("El aspecto ambiental no puede estar vacío");
        }

        return accionRepo.save(accion);
    }

    public List<AccionPlanAmbiental> listar() {
        return accionRepo.findAll();
    }

    public AccionPlanAmbiental buscarPorId(Long id) {
        return accionRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Acción no encontrada"));
    }

    public void eliminar(Long id) {
        accionRepo.deleteById(id);
    }

    public List<AccionPlanAmbiental> buscarPorEstado(Integer estado) {
        return accionRepo.findByEstadoAplica(estado);
    }

    public List<AccionPlanAmbiental> buscarPorSeccion(Long seccionId) {
        return accionRepo.findByFkCodSeccion(seccionId);
    }

    public List<AccionPlanAmbiental> buscarPorEstadoYSeccion(Integer estado, Long seccionId) {
        return accionRepo.findByEstadoAplicaAndFkCodSeccion(estado, seccionId);
    }
}