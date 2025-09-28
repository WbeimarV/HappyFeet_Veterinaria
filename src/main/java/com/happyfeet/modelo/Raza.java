package com.happyfeet.modelo;

import java.sql.Timestamp;

public class Raza {
    private int id;
    private int especieId;
    private String nombre;
    private Timestamp createdAt;

    public Raza() {}
    public Raza(int especieId, String nombre) {
        this.especieId = especieId;
        this.nombre = nombre;
    }

    // Getters y setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public int getEspecieId() { return especieId; }
    public void setEspecieId(int especieId) { this.especieId = especieId; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public Timestamp getCreatedAt() { return createdAt; }
    public void setCreatedAt(Timestamp createdAt) { this.createdAt = createdAt; }

    @Override
    public String toString() {
        return "Raza{id=" + id + ", especieId=" + especieId +
               ", nombre='" + nombre + "'}";
    }
}
