package com.happyfeet.modelo;

import java.sql.Timestamp;

public class ProductoTipo {
    private int id;
    private String nombre;
    private Timestamp createdAt;

    public ProductoTipo() {}

    public ProductoTipo(String nombre) {
        this.nombre = nombre;
    }

    // Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public Timestamp getCreatedAt() { return createdAt; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }

    @Override
    public String toString() {
        return "ProductoTipo{id=" + id + ", nombre='" + nombre + "'}";
    }
}
