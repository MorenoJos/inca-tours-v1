package com.incatours.incatours.repository;

import com.incatours.incatours.model.Tour;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface TourRepository extends JpaRepository<Tour, Long> {
    boolean existsByNombre(String nombre);
    boolean existsByNombreAndIdNot(String nombre, Long id);

    List<Tour> findByFechaHoraInicioBetween(
            LocalDateTime fechaHoraInicioAfter,
            LocalDateTime fechaHoraInicioBefore);
}
