package com.incatours.incatours.controller;

import com.incatours.incatours.model.Tour;
import com.incatours.incatours.repository.TourRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Controller
public class HomeController {
    @Autowired
    private TourRepository tourRepository;

    @GetMapping({"/", "/home"})
    public String mostrarHome(Authentication auth, Model model, HttpServletRequest request) {
        String username = auth.getName();
        boolean isAdmin = request.isUserInRole("ADMIN");

        LocalDateTime inicioDia = LocalDate.now().atStartOfDay();
        LocalDateTime finDia = LocalDateTime.of(LocalDate.now(), LocalTime.MAX);
        List<Tour> toursHoy = tourRepository.findByFechaHoraInicioBetween(inicioDia, finDia);

        model.addAttribute("toursHoy", toursHoy);
        model.addAttribute("isAdmin", isAdmin);
        model.addAttribute("usuario", username);

        return "home";
    }
}
