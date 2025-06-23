package com.incatours.incatours.service;

import com.incatours.incatours.config.UsuarioDetails;
import com.incatours.incatours.model.Usuario;
import com.incatours.incatours.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String correo) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByCorreo(correo)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + correo));

        // Creamos la autoridad con el prefijo ROLE_ + lo que venga en usuario.getRol()
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + usuario.getRol());

        // Pasamos el usuario y las autoridades al constructor de UsuarioDetails
        return new UsuarioDetails(usuario, Collections.singleton(authority));
    }
}

