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
    public void run(String... args) throws Exception {
        if (usuarioRepository.findByCorreo("admin@incatours.com").isEmpty()) {
            Usuario admin = new Usuario();
            admin.setNombre("Administrador");
            admin.setDni("00000000");
            admin.setCorreo("admin@incatours.com");
            admin.setTelefono("999999999");
            admin.setPassword(passwordEncoder.encode("admin123")); // Puedes cambiarla
            admin.setRol("ADMIN");

            usuarioRepository.save(admin);
            System.out.println("✅ Usuario ADMIN creado con éxito.");
        } else {
            System.out.println("ℹ️ Ya existe un admin, no se creó otro.");
        }
    }
}
