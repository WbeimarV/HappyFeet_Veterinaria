package com.happyfeet.modelo;

public class CitaEstado {
    private int id;
    private String nombre;
    private String createdAt;

    public CitaEstado() {}

    public CitaEstado(String nombre) {
        this.nombre = nombre;
    }

    public CitaEstado(int id, String nombre, String createdAt) {
        this.id = id;
        this.nombre = nombre;
        this.createdAt = createdAt;
    }

    // Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getCreatedAt() { return createdAt; }
    public void setCreatedAt(String createdAt) { this.createdAt = createdAt; }

    @Override
    public String toString() {
        return "CitaEstado{id=" + id + ", nombre='" + nombre + "', createdAt='" + createdAt + "'}";
    }
}
