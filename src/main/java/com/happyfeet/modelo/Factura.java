package com.happyfeet.modelo;

import java.time.LocalDateTime;

public class Factura {
    private int id;
    private int duenoId;
    private LocalDateTime fechaEmision;
    private double total;
    private LocalDateTime createdAt;

    public Factura() {}

    public Factura(int duenoId, double total) {
        this.duenoId = duenoId;
        this.total = total;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getDuenoId() { return duenoId; }
    public void setDuenoId(int duenoId) { this.duenoId = duenoId; }

    public LocalDateTime getFechaEmision() { return fechaEmision; }
    public void setFechaEmision(LocalDateTime fechaEmision) { this.fechaEmision = fechaEmision; }

    public double getTotal() { return total; }
    public void setTotal(double total) { this.total = total; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    @Override
    public String toString() {
        return "Factura{id=" + id + ", duenoId=" + duenoId +
               ", total=" + total + ", fecha=" + fechaEmision + "}";
    }
}
