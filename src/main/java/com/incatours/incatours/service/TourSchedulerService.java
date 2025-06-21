package com.incatours.incatours.service;

import com.incatours.incatours.model.EstadoTour;
import com.incatours.incatours.model.Tour;
import com.incatours.incatours.repository.TourRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TourSchedulerService {

    @Autowired
    private TourRepository tourRepository;

    @Scheduled(fixedRate = 60000) // cada 60 segundos
    public void actualizarEstadosTours() {
        List<Tour> tours = tourRepository.findAll();
        LocalDateTime ahora = LocalDateTime.now();

        for (Tour tour : tours) {
            // Validación básica
            if (tour.getFechaHoraInicio() == null || tour.getEstado() == EstadoTour.CULMINADO) {
                continue;
            }

            LocalDateTime inicio = tour.getFechaHoraInicio();
            LocalDateTime mitad = inicio.plusMinutes(tour.getDuracionHoras() * 30L); // duración en horas * 30 = mitad
            LocalDateTime fin = inicio.plusHours(tour.getDuracionHoras());

            EstadoTour nuevoEstado;

            if (ahora.isAfter(fin)) {
                nuevoEstado = EstadoTour.CULMINADO;
            } else if (ahora.isAfter(mitad)) {
                nuevoEstado = EstadoTour.EN_PROCESO;
            } else if (!ahora.isBefore(inicio)) {
                nuevoEstado = EstadoTour.INICIADO;
            } else {
                nuevoEstado = EstadoTour.PENDIENTE;
            }

            // Solo guardar si cambia el estado
            if (nuevoEstado != tour.getEstado()) {
                tour.setEstado(nuevoEstado);
                tourRepository.save(tour);
                System.out.println("Actualizado tour " + tour.getId() + " a estado " + nuevoEstado);
            }
        }
    }

    // Opcional: actualizar al iniciar la app
    @PostConstruct
    public void inicializarEstadosAlArrancar() {
        actualizarEstadosTours();
    }
}
