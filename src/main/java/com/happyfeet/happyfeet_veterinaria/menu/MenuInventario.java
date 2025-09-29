package com.happyfeet.happyfeet_veterinaria.menu;

import com.happyfeet.dao.InventarioDAO;
import com.happyfeet.dao.ProductoTipoDAO;
import com.happyfeet.modelo.Inventario;
import com.happyfeet.modelo.ProductoTipo;

import javax.swing.*;
import java.sql.Connection;
import java.sql.Date;
import java.util.List;

public class MenuInventario {

    public static void mostrar(Connection con) {
        InventarioDAO inventarioDAO = new InventarioDAO(con);
        ProductoTipoDAO productoTipoDAO = new ProductoTipoDAO(con);

        while (true) {
            String opcion = JOptionPane.showInputDialog("""
                    MENÚ INVENTARIO
                    1. Insertar inventario
                    2. Listar inventario
                    3. Eliminar inventario
                    4. Buscar producto por ID
                    0. Volver al menú principal
                    """);

            if (opcion == null || opcion.equals("0"))
                break;

            switch (opcion) {
                case "1" -> insertarInventario(inventarioDAO, productoTipoDAO);
                case "2" -> listarInventario(inventarioDAO, productoTipoDAO);
                case "3" -> eliminarInventario(inventarioDAO);
                case "4" -> buscarInventario(inventarioDAO);
                default -> JOptionPane.showMessageDialog(null, "Opción no válida");
            }
        }
    }

    private static void insertarInventario(InventarioDAO inventarioDAO, ProductoTipoDAO productoTipoDAO) {
        try {
            // Mostrar tipos de producto disponibles y pedir id en el mismo cuadro
            List<ProductoTipo> tipos = productoTipoDAO.listarTodos();
            if (tipos.isEmpty()) {
                JOptionPane.showMessageDialog(null, "No hay tipos de producto registrados.");
                return;
            }

            StringBuilder sb = new StringBuilder("Tipos de producto disponibles:\n");
            for (ProductoTipo t : tipos) {
                sb.append(t.getId()).append(". ").append(t.getNombre()).append("\n");
            }
            int tipoId = Integer.parseInt(JOptionPane.showInputDialog(sb + "\nDigite el ID del tipo de producto:"));

            Inventario inv = new Inventario();
            inv.setNombreProducto(JOptionPane.showInputDialog("Nombre del producto:"));
            inv.setProductoTipoId(tipoId);
            inv.setDescripcion(JOptionPane.showInputDialog("Descripción:"));
            inv.setFabricante(JOptionPane.showInputDialog("Fabricante:"));
            inv.setLote(JOptionPane.showInputDialog("Lote:"));
            inv.setCantidadStock(Integer.parseInt(JOptionPane.showInputDialog("Cantidad en stock:")));
            inv.setStockMinimo(Integer.parseInt(JOptionPane.showInputDialog("Stock mínimo:")));
            inv.setFechaVencimiento(Date.valueOf(JOptionPane.showInputDialog("Fecha de vencimiento (YYYY-MM-DD):")));
            inv.setPrecioVenta(Double.parseDouble(JOptionPane.showInputDialog("Precio de venta:")));

            JOptionPane.showMessageDialog(null, inventarioDAO.insertar(inv));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al insertar inventario: " + e.getMessage());
        }
    }

    private static void listarInventario(InventarioDAO inventarioDAO, ProductoTipoDAO productoTipoDAO) {
        List<Inventario> lista = inventarioDAO.listarTodos();
        if (lista.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No hay productos en inventario.");
            return;
        }

        StringBuilder sb = new StringBuilder("=== INVENTARIO ===\n");
        for (Inventario i : lista) {
            ProductoTipo tipo = productoTipoDAO.buscarPorId(i.getProductoTipoId());
            sb.append("ID: ").append(i.getId())
                    .append(" | Producto: ").append(i.getNombreProducto())
                    .append(" | Tipo: ").append(tipo != null ? tipo.getNombre() : "Desconocido")
                    .append(" | Fabricante: ").append(i.getFabricante())
                    .append(" | Stock: ").append(i.getCantidadStock())
                    .append(" | Stock mínimo: ").append(i.getStockMinimo())
                    .append(" | Vence: ").append(i.getFechaVencimiento())
                    .append(" | Precio: $").append(i.getPrecioVenta())
                    .append("\n");
        }
        JOptionPane.showMessageDialog(null, sb.toString());
    }

    private static void eliminarInventario(InventarioDAO inventarioDAO) {
        try {
            int id = Integer.parseInt(JOptionPane.showInputDialog("Ingrese ID del inventario a eliminar:"));
            JOptionPane.showMessageDialog(null, inventarioDAO.eliminar(id));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al eliminar inventario: " + e.getMessage());
        }
    }

    private static void buscarInventario(InventarioDAO inventarioDAO) {
        try {
            int id = Integer.parseInt(JOptionPane.showInputDialog("Ingrese ID del inventario a buscar:"));
            Inventario inv = inventarioDAO.buscarPorId(id);
            if (inv != null) {
                JOptionPane.showMessageDialog(null,
                        "ID: " + inv.getId() +
                                "\nProducto: " + inv.getNombreProducto() +
                                "\nTipo: " + inv.getProductoTipoId() +
                                "\nDescripción: " + inv.getDescripcion() +
                                "\nFabricante: " + inv.getFabricante() +
                                "\nLote: " + inv.getLote() +
                                "\nStock: " + inv.getCantidadStock() +
                                "\nStock mínimo: " + inv.getStockMinimo() +
                                "\nVencimiento: " + inv.getFechaVencimiento() +
                                "\nPrecio: " + inv.getPrecioVenta());
            } else {
                JOptionPane.showMessageDialog(null, "Inventario no encontrado.");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error en búsqueda: " + e.getMessage());
        }
    }

}
