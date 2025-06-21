package com.incatours.incatours.controller;

import com.incatours.incatours.model.Guia;
import com.incatours.incatours.model.Idioma;
import com.incatours.incatours.repository.GuiaRepository;
import com.incatours.incatours.repository.IdiomaRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/guias")
public class GuiaController {

    @Autowired
    private GuiaRepository guiaRepository;

    @Autowired
    private IdiomaRepository idiomaRepository;

    // Mostrar formulario para nuevo guía
    @GetMapping("/nuevo")
    public String mostrarFormularioGuia(Model model) {
        model.addAttribute("guia", new Guia());
        model.addAttribute("idiomas", idiomaRepository.findAll());
        return "guia/guia_form";
    }

    // Guardar nuevo guía
    @PostMapping("/guardar")
    public String guardarGuia(@Valid @ModelAttribute Guia guia,
                              BindingResult result,
                              Model model) {
        // Validación de correo duplicado
        if (guiaRepository.existsByCorreo(guia.getCorreo())) {
            result.rejectValue("correo", "error.guia", "Ya existe un guía con ese correo.");
        }

        // Validación de DNI duplicado
        if (guiaRepository.existsByDni(guia.getDni())) {
            result.rejectValue("dni", "error.guia", "Ya existe un guía con ese DNI.");
        }

        if (result.hasErrors()) {
            model.addAttribute("idiomas", idiomaRepository.findAll());
            return "guia/guia_form";
        }

        guiaRepository.save(guia);
        return "redirect:/guias/lista?exito";
    }

    // Listar guías
    @GetMapping("/lista")
    public String listarGuias(Model model,
                              @RequestParam(defaultValue = "0") int page,
                              @RequestParam(defaultValue = "5") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("nombre").ascending());
        Page<Guia> pagina = guiaRepository.findAll(pageable);

        model.addAttribute("pagina", pagina);
        model.addAttribute("guias", pagina.getContent());
        model.addAttribute("paginaActual", page);
        model.addAttribute("totalPaginas", pagina.getTotalPages());
        return "guia/lista_guias";
    }


    // Mostrar formulario para editar guía
    @GetMapping("/editar/{id}")
    public String editarGuia(@PathVariable Long id, Model model) {
        Optional<Guia> guiaOpt = guiaRepository.findById(id);
        if (guiaOpt.isPresent()) {
            model.addAttribute("guia", guiaOpt.get());
            model.addAttribute("idiomas", idiomaRepository.findAll());
            return "guia/guia_form";
        } else {
            return "redirect:/guias/lista?error";
        }
    }

    // Actualizar guía (usa mismo método guardar para crear o editar)
    @PostMapping("/actualizar")
    public String actualizarGuia(@Valid @ModelAttribute Guia guia,
                                 BindingResult result,
                                 Model model) {
        // Validar que no haya otro guía con el mismo correo
        if (guiaRepository.existsByCorreoAndIdNot(guia.getCorreo(), guia.getId())) {
            result.rejectValue("correo", "error.guia", "Ya existe otro guía con ese correo.");
        }

        if (guiaRepository.existsByDniAndIdNot(guia.getDni(), guia.getId())) {
            result.rejectValue("dni", "error.guia", "Ya existe otro guía con ese DNI.");
        }

        if (result.hasErrors()) {
            model.addAttribute("idiomas", idiomaRepository.findAll());
            return "guia/guia_form";
        }

        guiaRepository.save(guia);
        return "redirect:/guias/lista?editado";
    }
}
