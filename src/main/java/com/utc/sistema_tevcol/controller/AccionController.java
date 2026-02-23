package com.utc.sistema_tevcol.controller;

import com.utc.sistema_tevcol.entity.AccionPlanAmbiental;
import com.utc.sistema_tevcol.service.AccionService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/accion")
public class AccionController {

    private final AccionService accionService;

    public AccionController(AccionService accionService) {
        this.accionService = accionService;
    }

    @GetMapping
    public String index(Model model) {
        model.addAttribute("acciones", accionService.listar());
        return "acciones/index";
    }


    @GetMapping("/nuevo")
    public String nuevo(Model model) {
        model.addAttribute("accion", new AccionPlanAmbiental());
        return "acciones/accion";
    }


    @PostMapping("/guardar")
    public String guardar(@ModelAttribute AccionPlanAmbiental accion,
                          RedirectAttributes ra) {

        boolean esNuevo = accionService
                .listar()
                .stream()
                .noneMatch(a -> a.getCodigoAccion()
                        .equals(accion.getCodigoAccion()));

        try {

            accionService.guardar(accion);

            ra.addFlashAttribute("success",
                    esNuevo ? "Acción creada correctamente"
                            : "Acción editada correctamente");

        } catch (Exception e) {

            ra.addFlashAttribute("error", e.getMessage());

            if (esNuevo) {
                return "redirect:/accion/nuevo";
            } else {
                return "redirect:/accion/editar/" + accion.getCodigoAccion();
            }
        }

        return "redirect:/accion";
    }

    @GetMapping("/editar/{id}")
    public String editar(@PathVariable Long id, Model model) {

        model.addAttribute("accion",
                accionService.buscarPorId(id));

        return "acciones/accion";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminar(@PathVariable Long id,
                           RedirectAttributes ra) {

        try {
            accionService.eliminar(id);
            ra.addFlashAttribute("success",
                    "Acción eliminada correctamente");
        } catch (Exception e) {
            ra.addFlashAttribute("error",
                    "No se pudo eliminar la acción");
        }

        return "redirect:/accion";
    }
}