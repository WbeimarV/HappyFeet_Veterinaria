package com.happyfeet.modelo;

import java.sql.Timestamp;

public class EventoTipo {
    private int id;
    private String nombre;
    private Timestamp createdAt;

    public EventoTipo(int id, String nombre, Timestamp createdAt) {
        this.id = id;
        this.nombre = nombre;
        this.createdAt = createdAt;
    }

    public EventoTipo(String nombre) {
        this.nombre = nombre;
    }

    public int getId() { return id; }
    public String getNombre() { return nombre; }
    public Timestamp getCreatedAt() { return createdAt; }

    public void setId(int id) { this.id = id; }   // NUEVO
    public void setNombre(String nombre) { this.nombre = nombre; }

    @Override
    public String toString() {
        return "EventoTipo{id=" + id + ", nombre='" + nombre + "'}";
    }
}

