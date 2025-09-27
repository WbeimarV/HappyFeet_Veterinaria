/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.happyfeet.modelo;
import java.time.LocalDate;

public class Mascota {
    private int id;
    private int duenoId;          // FK a dueño
    private String nombre;
    private int razaId;           // FK a raza
    private LocalDate fechaNacimiento;
    private String sexo;          // "Macho" o "Hembra"
    private String urlFoto;
    private String createdAt;     // texto simple para guardar el timestamp

    public Mascota() {}

    // Constructor para insertar nueva mascota
    public Mascota(int duenoId, String nombre, int razaId,
                   LocalDate fechaNacimiento, String sexo, String urlFoto) {
        this.duenoId = duenoId;
        this.nombre = nombre;
        this.razaId = razaId;
        this.fechaNacimiento = fechaNacimiento;
        this.sexo = sexo;
        this.urlFoto = urlFoto;
    }

    // Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getDuenoId() { return duenoId; }
    public void setDuenoId(int duenoId) { this.duenoId = duenoId; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public int getRazaId() { return razaId; }
    public void setRazaId(int razaId) { this.razaId = razaId; }

    public LocalDate getFechaNacimiento() { return fechaNacimiento; }
    public void setFechaNacimiento(LocalDate fechaNacimiento) { this.fechaNacimiento = fechaNacimiento; }

    public String getSexo() { return sexo; }
    public void setSexo(String sexo) { this.sexo = sexo; }

    public String getUrlFoto() { return urlFoto; }
    public void setUrlFoto(String urlFoto) { this.urlFoto = urlFoto; }

    public String getCreatedAt() { return createdAt; }
    public void setCreatedAt(String createdAt) { this.createdAt = createdAt; }

    @Override
    public String toString() {
        return "Mascota: " + nombre + " (" + sexo + ")";
    }
}
