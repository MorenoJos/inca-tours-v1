package com.incatours.incatours.controller;

import com.incatours.incatours.model.EstadoTour;
import com.incatours.incatours.model.Tour;
import com.incatours.incatours.repository.GuiaRepository;
import com.incatours.incatours.repository.TourRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/tours")
public class TourController {

    @Autowired
    private TourRepository tourRepository;

    @Autowired
    private GuiaRepository guiaRepository;

    @GetMapping("/nuevo")
    public String mostrarFormularioTour(Model model) {
        model.addAttribute("tour", new Tour());
        model.addAttribute("guiaList", guiaRepository.findAll());
        model.addAttribute("estadoList", EstadoTour.values());
        return "tour/tour_form";
    }

    @PostMapping("/guardar")
    public String guardarTour(@Valid @ModelAttribute Tour tour,
                              BindingResult result,
                              Model model) {

        // Verificar nombre duplicado
        if (tour.getId() == null && tourRepository.existsByNombre(tour.getNombre())) {
            result.rejectValue("nombre", "error.tour", "Ya existe un tour con ese nombre.");
        } else if (tour.getId() != null && tourRepository.existsByNombreAndIdNot(tour.getNombre(), tour.getId())) {
            result.rejectValue("nombre", "error.tour", "Ya existe otro tour con ese nombre.");
        }

        if (result.hasErrors()) {
            model.addAttribute("guiaList", guiaRepository.findAll());
            model.addAttribute("estadoList", EstadoTour.values());
            return "tour/tour_form";
        }

        tourRepository.save(tour);
        return tour.getId() == null
                ? "redirect:/tours/lista?exito"
                : "redirect:/tours/lista?editado";
    }

    @GetMapping("/editar/{id}")
    public String editarTour(@PathVariable Long id, Model model) {
        Tour tour = tourRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("ID de tour inválido: " + id));
        model.addAttribute("tour", tour);
        model.addAttribute("guiaList", guiaRepository.findAll());
        model.addAttribute("estadoList", EstadoTour.values());
        return "tour/tour_form";
    }

    @GetMapping("/lista")
    public String listarTours(
            Model model,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size) {

        Page<Tour> toursPage = tourRepository.findAll(PageRequest.of(page, size, Sort.by("fechaHoraInicio").descending()));
        model.addAttribute("toursPage", toursPage);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", toursPage.getTotalPages());

        return "tour/listar_tours";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarTour(@PathVariable Long id) {
        tourRepository.deleteById(id);
        return "redirect:/tours/lista?eliminado";
    }
}
