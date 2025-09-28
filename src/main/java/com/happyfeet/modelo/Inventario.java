package com.happyfeet.modelo;

import java.sql.Date;

public class Inventario {
    private int id;
    private String nombreProducto;
    private int productoTipoId;
    private String descripcion;
    private String fabricante;
    private String lote;
    private int cantidadStock;
    private int stockMinimo;
    private Date fechaVencimiento;
    private double precioVenta;

    public Inventario() {}

    public Inventario(String nombreProducto, int productoTipoId, String descripcion,
                      String fabricante, String lote, int cantidadStock,
                      int stockMinimo, Date fechaVencimiento, double precioVenta) {
        this.nombreProducto = nombreProducto;
        this.productoTipoId = productoTipoId;
        this.descripcion = descripcion;
        this.fabricante = fabricante;
        this.lote = lote;
        this.cantidadStock = cantidadStock;
        this.stockMinimo = stockMinimo;
        this.fechaVencimiento = fechaVencimiento;
        this.precioVenta = precioVenta;
    }

    // Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNombreProducto() { return nombreProducto; }
    public void setNombreProducto(String nombreProducto) { this.nombreProducto = nombreProducto; }

    public int getProductoTipoId() { return productoTipoId; }
    public void setProductoTipoId(int productoTipoId) { this.productoTipoId = productoTipoId; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public String getFabricante() { return fabricante; }
    public void setFabricante(String fabricante) { this.fabricante = fabricante; }

    public String getLote() { return lote; }
    public void setLote(String lote) { this.lote = lote; }

    public int getCantidadStock() { return cantidadStock; }
    public void setCantidadStock(int cantidadStock) { this.cantidadStock = cantidadStock; }

    public int getStockMinimo() { return stockMinimo; }
    public void setStockMinimo(int stockMinimo) { this.stockMinimo = stockMinimo; }

    public Date getFechaVencimiento() { return fechaVencimiento; }
    public void setFechaVencimiento(Date fechaVencimiento) { this.fechaVencimiento = fechaVencimiento; }

    public double getPrecioVenta() { return precioVenta; }
    public void setPrecioVenta(double precioVenta) { this.precioVenta = precioVenta; }

    @Override
    public String toString() {
        return "Inventario{id=" + id + ", nombreProducto='" + nombreProducto + '\'' +
                ", productoTipoId=" + productoTipoId +
                ", descripcion='" + descripcion + '\'' +
                ", fabricante='" + fabricante + '\'' +
                ", lote='" + lote + '\'' +
                ", cantidadStock=" + cantidadStock +
                ", stockMinimo=" + stockMinimo +
                ", fechaVencimiento=" + fechaVencimiento +
                ", precioVenta=" + precioVenta + '}';
    }
}