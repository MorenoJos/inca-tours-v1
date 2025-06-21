package com.incatours.incatours.controller;

import com.incatours.incatours.model.Idioma;
import com.incatours.incatours.repository.IdiomaRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/idiomas")
public class IdiomaController {

    @Autowired
    private IdiomaRepository idiomaRepository;

    @GetMapping("/nuevo")
    public String mostrarFormulario(Model model) {
        model.addAttribute("idioma", new Idioma());
        return "idiomas/idioma_form";
    }

    @PostMapping("/guardar")
    public String guardarIdioma(@Valid @ModelAttribute Idioma idioma,
                                BindingResult result,
                                Model model) {

        if (idioma.getId() == null) {
            // Nuevo idioma
            if (idiomaRepository.existsByNombre(idioma.getNombre())) {
                result.rejectValue("nombre", "error.idioma", "Ya existe un idioma con ese nombre.");
            }
        } else {
            // Edición
            if (idiomaRepository.existsByNombreAndIdNot(idioma.getNombre(), idioma.getId())) {
                result.rejectValue("nombre", "error.idioma", "Ya existe otro idioma con ese nombre.");
            }
        }

        if (result.hasErrors()) {
            return "idiomas/idioma_form";
        }

        idiomaRepository.save(idioma);

        return "redirect:/idiomas/lista" + (idioma.getId() == null ? "?exito" : "?editado");
    }


    @GetMapping("/lista")
    public String listarIdiomas(Model model) {
        model.addAttribute("idiomas", idiomaRepository.findAll());
        return "idiomas/lista_idiomas";
    }

    @GetMapping("/editar/{id}")
    public String editarIdioma(@PathVariable Long id, Model model) {
        Optional<Idioma> idiomaOpt = idiomaRepository.findById(id);
        if (idiomaOpt.isPresent()) {
            model.addAttribute("idioma", idiomaOpt.get());
            return "idiomas/idioma_form";
        } else {
            return "redirect:/idiomas/lista?error";
        }
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarIdioma(@PathVariable Long id) {
        idiomaRepository.deleteById(id);
        return "redirect:/idiomas/lista?eliminado";
    }
}
