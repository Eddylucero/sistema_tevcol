package com.utc.sistema_tevcol.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.utc.sistema_tevcol.entity.ActividadAccion;
import com.utc.sistema_tevcol.entity.AccionPlanAmbiental;
import com.utc.sistema_tevcol.service.ActividadService;
import com.utc.sistema_tevcol.service.AccionService;

@Controller
@RequestMapping("/actividad")
public class ActividadController {

    private final ActividadService actividadService;
    private final AccionService accionService;

    public ActividadController(ActividadService actividadService,
            AccionService accionService) {
        this.actividadService = actividadService;
        this.accionService = accionService;
    }

    @GetMapping
    public String index(Model model) {
        model.addAttribute("actividades", actividadService.listar());
        return "actividades/index";
    }

    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("actividad", new ActividadAccion());
        model.addAttribute("acciones", accionService.listar());
        return "actividades/actividad";
    }

    @PostMapping("/guardar")
    public String guardar(@ModelAttribute ActividadAccion actividad,
            RedirectAttributes ra) {

        boolean esNuevo = actividadService
                .listar()
                .stream()
                .noneMatch(a -> a.getCodigoActividad()
                        .equals(actividad.getCodigoActividad()));

        try {
            AccionPlanAmbiental accion = accionService
                    .buscarPorId(actividad.getAccion().getCodigoAccion());

            actividad.setAccion(accion);

            actividadService.guardar(actividad);

            ra.addFlashAttribute("success",
                    esNuevo ? "Actividad creada correctamente"
                            : "Actividad editada correctamente");

        } catch (Exception e) {

            ra.addFlashAttribute("error", e.getMessage());

            if (esNuevo) {
                return "redirect:/actividad/nuevo";
            } else {
                return "redirect:/actividad/editar/" + actividad.getCodigoActividad();
            }
        }

        return "redirect:/actividad";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {

        model.addAttribute("actividad", actividadService.buscarPorId(id));
        model.addAttribute("acciones", accionService.listar());

        return "actividades/actividad";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id, RedirectAttributes ra) {

        actividadService.eliminar(id);
        ra.addFlashAttribute("success", "Actividad eliminada correctamente");

        return "redirect:/actividad";
    }
}