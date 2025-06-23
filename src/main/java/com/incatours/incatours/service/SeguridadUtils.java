package com.incatours.incatours.service;

import com.incatours.incatours.config.UsuarioDetails;
import com.incatours.incatours.model.Usuario;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component("seguridad")
public class SeguridadUtils {

    public Usuario getUsuario() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth != null && auth.getPrincipal() instanceof UsuarioDetails) {
            return ((UsuarioDetails) auth.getPrincipal()).getUsuario();
        }

        return null;
    }
}
