package com.happyfeet.modelo;

import java.time.LocalDateTime;

public class ItemFactura {
    private int id;
    private int facturaId;
    private Integer productoId; // puede ser null
    private String servicioDescripcion;
    private int cantidad;
    private double precioUnitario;
    private double subtotal;
    private LocalDateTime createdAt;

    public ItemFactura() {}

    public ItemFactura(int facturaId, Integer productoId, String servicioDescripcion,
                       int cantidad, double precioUnitario, double subtotal) {
        this.facturaId = facturaId;
        this.productoId = productoId;
        this.servicioDescripcion = servicioDescripcion;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
        this.subtotal = subtotal;
    }

    // Getters/Setters ...
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getFacturaId() { return facturaId; }
    public void setFacturaId(int facturaId) { this.facturaId = facturaId; }

    public Integer getProductoId() { return productoId; }
    public void setProductoId(Integer productoId) { this.productoId = productoId; }

    public String getServicioDescripcion() { return servicioDescripcion; }
    public void setServicioDescripcion(String servicioDescripcion) { this.servicioDescripcion = servicioDescripcion; }

    public int getCantidad() { return cantidad; }
    public void setCantidad(int cantidad) { this.cantidad = cantidad; }

    public double getPrecioUnitario() { return precioUnitario; }
    public void setPrecioUnitario(double precioUnitario) { this.precioUnitario = precioUnitario; }

    public double getSubtotal() { return subtotal; }
    public void setSubtotal(double subtotal) { this.subtotal = subtotal; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    @Override
    public String toString() {
        return "ItemFactura{id=" + id + ", facturaId=" + facturaId +
               ", productoId=" + productoId + ", subtotal=" + subtotal + "}";
    }
}
