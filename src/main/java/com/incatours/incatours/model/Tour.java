package com.incatours.incatours.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

@Entity
public class Tour {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre del tour es obligatorio.")
    private String nombre;

    @NotBlank(message = "El tipo de tour es obligatorio.")
    private String tipo;

    @Min(value = 1, message = "La duración debe ser al menos 1 hora.")
    private int duracionHoras; // duración estimada en horas

    @Min(value = 1, message = "Debe haber al menos 1 pasajero.")
    private int cantidadPasajeros;

    @Future(message = "La fecha y hora debe ser futura.")
    private LocalDateTime fechaHoraInicio;

    @Enumerated(EnumType.STRING)
    private EstadoTour estado;

    @ManyToOne
    @JoinColumn(name = "guia_id")
    @NotNull(message = "Debes asignar un guía.")
    private Guia guiaAsignado;

    public Tour() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getDuracionHoras() {
        return duracionHoras;
    }

    public void setDuracionHoras(int duracionHoras) {
        this.duracionHoras = duracionHoras;
    }

    public int getCantidadPasajeros() {
        return cantidadPasajeros;
    }

    public void setCantidadPasajeros(int cantidadPasajeros) {
        this.cantidadPasajeros = cantidadPasajeros;
    }

    public LocalDateTime getFechaHoraInicio() {
        return fechaHoraInicio;
    }

    public void setFechaHoraInicio(LocalDateTime fechaHoraInicio) {
        this.fechaHoraInicio = fechaHoraInicio;
    }

    public EstadoTour getEstado() {
        return estado;
    }

    public void setEstado(EstadoTour estado) {
        this.estado = estado;
    }

    public Guia getGuiaAsignado() {
        return guiaAsignado;
    }

    public void setGuiaAsignado(Guia guiaAsignado) {
        this.guiaAsignado = guiaAsignado;
    }
}
