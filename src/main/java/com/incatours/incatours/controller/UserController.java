package com.incatours.incatours.controller;

import com.incatours.incatours.model.Usuario;
import com.incatours.incatours.repository.UsuarioRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/usuarios")
public class UserController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Mostrar formulario para registrar un nuevo usuario
    @GetMapping("/nuevo")
    public String mostrarFormularioUsuario(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "usuario/usuario_form";
    }

    // Guardar o actualizar usuario
    @PostMapping("/guardar")
    public String guardarUsuario(@Valid @ModelAttribute Usuario usuario,
                                 BindingResult result,
                                 Model model) {
        if (result.hasErrors()) {
            model.addAttribute("usuario", usuario);
            return "usuario/usuario_form";
        }

        // Si el usuario ya existe (edición), no sobreescribas la contraseña si está vacía
        if (usuario.getId() != null) {
            Optional<Usuario> existenteOpt = usuarioRepository.findById(usuario.getId());
            if (existenteOpt.isPresent()) {
                Usuario existente = existenteOpt.get();

                // Si se ingresó nueva contraseña, la actualizamos encriptada
                if (usuario.getPassword() != null && !usuario.getPassword().isBlank()) {
                    existente.setPassword(passwordEncoder.encode(usuario.getPassword()));
                }

                // Actualizamos los demás campos
                existente.setNombre(usuario.getNombre());
                existente.setCorreo(usuario.getCorreo());
                existente.setDni(usuario.getDni());
                existente.setTelefono(usuario.getTelefono());
                existente.setRol(usuario.getRol());

                usuarioRepository.save(existente);
            }
        } else {
            // Nuevo usuario, encriptar siempre
            usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
            usuarioRepository.save(usuario);
        }

        return "redirect:/usuarios/lista?exito";
    }

    // Listar todos los usuarios
    @GetMapping("/lista")
    public String listarUsuarios(Model model,
                                 @RequestParam(defaultValue = "0") int page,
                                 @RequestParam(defaultValue = "5") int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("nombre").ascending());
        Page<Usuario> pagina = usuarioRepository.findAll(pageable);

        model.addAttribute("pagina", pagina);
        model.addAttribute("usuarios", pagina.getContent());
        model.addAttribute("paginaActual", page);
        model.addAttribute("totalPaginas", pagina.getTotalPages());
        return "usuario/lista_usuarios";
    }


    // Editar usuario
    @GetMapping("/editar/{id}")
    public String editarUsuario(@PathVariable Long id, Model model) {
        Optional<Usuario> usuario = usuarioRepository.findById(id);
        if (usuario.isPresent()) {
            model.addAttribute("usuario", usuario.get());
            return "usuario/usuario_form";
        } else {
            return "redirect:/usuarios/lista?error";
        }
    }

    // Eliminar usuario
    @GetMapping("/eliminar/{id}")
    public String eliminarUsuario(@PathVariable Long id) {
        usuarioRepository.deleteById(id);
        return "redirect:/usuarios/lista?eliminado";
    }
}
