package com.happyfeet.modelo;

import java.sql.Timestamp;

public class Especie {
    private int id;
    private String nombre;
    private Timestamp createdAt; // opcional para mostrar fecha de creación

    public Especie() {}
    public Especie(String nombre) { this.nombre = nombre; }
    public Especie(int id, String nombre) { this.id = id; this.nombre = nombre; }

    // Getters y setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public Timestamp getCreatedAt() { return createdAt; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }

    @Override
    public String toString() {
        return "Especie{id=" + id + ", nombre='" + nombre + "'}";
    }
}