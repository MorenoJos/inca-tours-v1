package com.incatours.incatours.repository;

import com.incatours.incatours.model.Guia;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GuiaRepository extends JpaRepository<Guia, Long> {
    boolean existsByCorreo(String correo);
    boolean existsByDni(String dni);
    boolean existsByCorreoAndIdNot(String correo, Long id);
    boolean existsByDniAndIdNot(String dni, Long id);
}
