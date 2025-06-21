package com.incatours.incatours.repository;

import com.incatours.incatours.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    boolean existsByCorreo(String correo);
    boolean existsByDni(String dni);
    boolean existsByCorreoAndIdNot(String correo, Long id);
    boolean existsByDniAndIdNot(String dni, Long id);
    Optional<Usuario> findByCorreo(String correo); // Para login
}
