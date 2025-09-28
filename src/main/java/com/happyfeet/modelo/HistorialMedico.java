package com.happyfeet.modelo;

import java.time.LocalDateTime;

public class HistorialMedico {
    private int id;
    private int mascotaId;
    private LocalDateTime fechaEvento;
    private int eventoTipoId;
    private String descripcion;
    private String diagnostico;
    private String tratamientoRecomendado;
    private LocalDateTime createdAt;

    public HistorialMedico() {}

    public HistorialMedico(int mascotaId, LocalDateTime fechaEvento, int eventoTipoId,
                           String descripcion, String diagnostico, String tratamientoRecomendado) {
        this.mascotaId = mascotaId;
        this.fechaEvento = fechaEvento;
        this.eventoTipoId = eventoTipoId;
        this.descripcion = descripcion;
        this.diagnostico = diagnostico;
        this.tratamientoRecomendado = tratamientoRecomendado;
    }

    // Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getMascotaId() { return mascotaId; }
    public void setMascotaId(int mascotaId) { this.mascotaId = mascotaId; }

    public LocalDateTime getFechaEvento() { return fechaEvento; }
    public void setFechaEvento(LocalDateTime fechaEvento) { this.fechaEvento = fechaEvento; }

    public int getEventoTipoId() { return eventoTipoId; }
    public void setEventoTipoId(int eventoTipoId) { this.eventoTipoId = eventoTipoId; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public String getDiagnostico() { return diagnostico; }
    public void setDiagnostico(String diagnostico) { this.diagnostico = diagnostico; }

    public String getTratamientoRecomendado() { return tratamientoRecomendado; }
    public void setTratamientoRecomendado(String tratamientoRecomendado) { this.tratamientoRecomendado = tratamientoRecomendado; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    @Override
    public String toString() {
        return "HistorialMedico{id=" + id +
               ", mascotaId=" + mascotaId +
               ", fechaEvento=" + fechaEvento +
               ", eventoTipoId=" + eventoTipoId +
               ", diagnostico='" + diagnostico + "'}";
    }
}
