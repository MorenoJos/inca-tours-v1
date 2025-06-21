package com.incatours.incatours.dto;

public class AuthResponse {

    private String token;
    private String rol;
    private String nombre;

    public AuthResponse(String token, String rol, String nombre) {
        this.token = token;
        this.rol = rol;
        this.nombre = nombre;
    }

    // Getters
    public String getToken() {
        return token;
    }

    public String getRol() {
        return rol;
    }

    public String getNombre() {
        return nombre;
    }
}
