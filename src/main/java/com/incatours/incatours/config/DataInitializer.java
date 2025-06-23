package com.incatours.incatours.config;

import com.incatours.incatours.model.Usuario;
import com.incatours.incatours.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {
        crearUsuarioSiNoExiste("admin@incatours.com", "Administrador", "00000000", "999999999", "admin123", "ADMIN");
        crearUsuarioSiNoExiste("usuario@incatours.com", "Usuario Prueba", "11111111", "987654321", "user123", "USER");
    }

    private void crearUsuarioSiNoExiste(String correo, String nombre, String dni, String telefono, String clave, String rol) {
        usuarioRepository.findByCorreo(correo).ifPresentOrElse(
                u -> System.out.printf("ℹ️ Ya existe un usuario con correo %s, no se creó otro.%n", correo),
                () -> {
                    Usuario nuevo = new Usuario();
                    nuevo.setCorreo(correo);
                    nuevo.setNombre(nombre);
                    nuevo.setDni(dni);
                    nuevo.setTelefono(telefono);
                    nuevo.setPassword(passwordEncoder.encode(clave));
                    nuevo.setRol(rol);

                    usuarioRepository.save(nuevo);
                    System.out.printf("✅ Usuario con rol %s creado con éxito.%n", rol);
                }
        );
    }
}
