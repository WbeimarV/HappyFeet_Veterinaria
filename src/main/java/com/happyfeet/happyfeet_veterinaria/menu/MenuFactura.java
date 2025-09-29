package com.happyfeet.happyfeet_veterinaria.menu;

import com.happyfeet.dao.*;
import com.happyfeet.modelo.*;

import javax.swing.*;

import java.sql.Connection;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class MenuFactura {

    public static void mostrar(Connection con) {
        FacturaDAO facturaDAO = new FacturaDAO(con);
        ItemFacturaDAO itemFacturaDAO = new ItemFacturaDAO(con);
        InventarioDAO inventarioDAO = new InventarioDAO(con);
        DuenoDAO duenoDAO = new DuenoDAO(con);

        String opcion;
        do {
            opcion = JOptionPane.showInputDialog("""
                    MENÚ FACTURA
                    1. Registrar factura
                    2. Listar facturas
                    0. Volver
                    Seleccione una opción:""");

            if (opcion == null)
                return;

            switch (opcion) {
                case "1" -> registrar(facturaDAO, itemFacturaDAO, inventarioDAO, duenoDAO);
                case "2" -> listar(facturaDAO, itemFacturaDAO, inventarioDAO, duenoDAO);
                case "0" -> {
                }
                default -> JOptionPane.showMessageDialog(null, "⚠️ Opción inválida.");
            }
        } while (!"0".equals(opcion));
    }

    private static void registrar(FacturaDAO facturaDAO, ItemFacturaDAO itemFacturaDAO, InventarioDAO inventarioDAO,
            DuenoDAO duenoDAO) {
        try {
            // 1. Selección de dueño
            StringBuilder sb = new StringBuilder("=== DUEÑOS ===\n");
            for (Dueno d : duenoDAO.listar()) {
                sb.append(d.getId()).append(" - ").append(d.getNombreCompleto()).append("\n");
            }
            int duenoId = Integer.parseInt(JOptionPane.showInputDialog(sb + "\nDigite el ID del dueño:"));

            // 2. Crear factura vacía
            Factura factura = new Factura();
            factura.setDuenoId(duenoId);
            factura.setFechaEmision(LocalDateTime.now());
            factura.setTotal(0);
            facturaDAO.insertar(factura);

            double total = 0;
            List<ItemFactura> items = new ArrayList<>();

            boolean seguir = true;
            while (seguir) {
                // 3. Mostrar inventario
                StringBuilder inv = new StringBuilder("=== INVENTARIO DISPONIBLE ===\n");
                for (Inventario i : inventarioDAO.listarTodos()) {
                    inv.append("ID: ").append(i.getId())
                            .append(" | ").append(i.getNombreProducto())
                            .append(" | Stock: ").append(i.getCantidadStock())
                            .append(" | Precio: ").append(i.getPrecioVenta())
                            .append("\n");
                }

                int inventarioId = Integer
                        .parseInt(JOptionPane.showInputDialog(inv + "\nDigite ID del producto a agregar:"));
                Inventario prod = inventarioDAO.buscarPorId(inventarioId);
                if (prod == null) {
                    JOptionPane.showMessageDialog(null, "Producto no encontrado.");
                    continue;
                }

                int cantidad = Integer.parseInt(
                        JOptionPane.showInputDialog("Cantidad a agregar (stock: " + prod.getCantidadStock() + "):"));
                if (cantidad > prod.getCantidadStock()) {
                    JOptionPane.showMessageDialog(null, "No hay suficiente stock.");
                    continue;
                }

                double subtotal = cantidad * prod.getPrecioVenta();
                total += subtotal;

                // 4. Insertar ítem
                ItemFactura item = new ItemFactura();
                item.setFacturaId(factura.getId());
                item.setProductoId(inventarioId); // ✅ CORREGIDO
                item.setCantidad(cantidad);
                item.setPrecioUnitario(prod.getPrecioVenta());
                item.setSubtotal(subtotal); // ✅ SUBTOTAL GUARDADO
                itemFacturaDAO.insertar(item);
                items.add(item);

                // 5. Restar del inventario
                prod.setCantidadStock(prod.getCantidadStock() - cantidad);
                inventarioDAO.actualizar(prod);

                // 6. Preguntar si desea seguir
                int resp = JOptionPane.showConfirmDialog(null, "¿Desea agregar otro producto?", "Continuar",
                        JOptionPane.YES_NO_OPTION);
                seguir = (resp == JOptionPane.YES_OPTION);
            }

            // 7. Actualizar total de factura
            factura.setTotal(total);
            facturaDAO.actualizarTotal(factura.getId(), total);

            // 8. Mostrar resumen
            StringBuilder resumen = new StringBuilder(
                    "Factura registrada con éxito.\nTotal: " + total + "\nProductos:\n");
            for (ItemFactura i : items) {
                resumen.append("ID Producto: ").append(i.getProductoId()) // ✅ ahora muestra el producto correcto
                        .append(" | Cantidad: ").append(i.getCantidad())
                        .append(" | Precio U: ").append(i.getPrecioUnitario())
                        .append(" | Subtotal: ").append(i.getSubtotal())
                        .append("\n");
            }
            JOptionPane.showMessageDialog(null, resumen.toString());

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al registrar factura: " + e.getMessage());
        }
    }

    private static void listar(FacturaDAO facturaDAO, ItemFacturaDAO itemFacturaDAO, InventarioDAO inventarioDAO,
            DuenoDAO duenoDAO) {
        StringBuilder sb = new StringBuilder("=== LISTADO DE FACTURAS ===\n");
        for (Factura f : facturaDAO.listarTodos()) {
            Dueno d = duenoDAO.buscarPorId(f.getDuenoId());
            sb.append("Factura ID: ").append(f.getId())
                    .append(" | Cliente: ").append(d != null ? d.getNombreCompleto() : "Desconocido")
                    .append(" | Fecha: ").append(f.getFechaEmision())
                    .append(" | Total: ").append(f.getTotal()).append("\n");

            for (ItemFactura item : itemFacturaDAO.listarPorFactura(f.getId())) {
                Inventario prod = inventarioDAO.buscarPorId(item.getId());
                sb.append("   -> ").append(prod != null ? prod.getNombreProducto() : "Producto desconocido")
                        .append(" | Cantidad: ").append(item.getCantidad())
                        .append(" | Precio U: ").append(item.getPrecioUnitario())
                        .append("\n");
            }
            sb.append("\n");
        }
        JOptionPane.showMessageDialog(null, sb.toString());
    }
}
