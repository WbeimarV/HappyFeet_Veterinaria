package com.happyfeet.modelo;

import java.time.LocalDateTime;

public class Cita {
    private int id;
    private int mascotaId;
    private LocalDateTime fechaHora;
    private String motivo;
    private int estadoId;
    private LocalDateTime createdAt;

    public Cita() {}

    public Cita(int mascotaId, LocalDateTime fechaHora, String motivo, int estadoId) {
        this.mascotaId = mascotaId;
        this.fechaHora = fechaHora;
        this.motivo = motivo;
        this.estadoId = estadoId;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getMascotaId() { return mascotaId; }
    public void setMascotaId(int mascotaId) { this.mascotaId = mascotaId; }

    public LocalDateTime getFechaHora() { return fechaHora; }
    public void setFechaHora(LocalDateTime fechaHora) { this.fechaHora = fechaHora; }

    public String getMotivo() { return motivo; }
    public void setMotivo(String motivo) { this.motivo = motivo; }

    public int getEstadoId() { return estadoId; }
    public void setEstadoId(int estadoId) { this.estadoId = estadoId; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    @Override
    public String toString() {
        return "Cita{id=" + id + ", mascotaId=" + mascotaId +
               ", fechaHora=" + fechaHora + ", motivo='" + motivo + "'}";
    }
}
