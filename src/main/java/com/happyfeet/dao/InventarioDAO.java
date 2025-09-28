package com.happyfeet.dao;

import com.happyfeet.modelo.Inventario;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class InventarioDAO {

    private final Connection conexion;
    private static final Logger logger = Logger.getLogger(InventarioDAO.class.getName());

    public InventarioDAO(Connection conexion) {
        this.conexion = conexion;
    }

    // INSERTAR
    public String insertar(Inventario inv) {
        String sql = "INSERT INTO inventario " +
                "(nombre_producto, producto_tipo_id, descripcion, fabricante, lote, cantidad_stock, stock_minimo, fecha_vencimiento, precio_venta) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement ps = conexion.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, inv.getNombreProducto());
            ps.setInt(2, inv.getProductoTipoId());
            ps.setString(3, inv.getDescripcion());
            ps.setString(4, inv.getFabricante());
            ps.setString(5, inv.getLote());
            ps.setInt(6, inv.getCantidadStock());
            ps.setInt(7, inv.getStockMinimo());
            ps.setDate(8, inv.getFechaVencimiento());
            ps.setDouble(9, inv.getPrecioVenta());

            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) inv.setId(rs.getInt(1));
            }
            return "Inventario insertado con éxito. ID: " + inv.getId();

        } catch (SQLIntegrityConstraintViolationException e) {
            return "Error de integridad: verifica que el producto_tipo exista o que no haya duplicados.";
        } catch (SQLException e) {
            logger.warning("Error al insertar inventario: " + e.getMessage());
            return "Error al insertar inventario: " + e.getMessage();
        }
    }

    // LISTAR TODOS
    public List<Inventario> listarTodos() {
        List<Inventario> lista = new ArrayList<>();
        String sql = "SELECT * FROM inventario";
        try (PreparedStatement ps = conexion.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Inventario i = new Inventario();
                i.setId(rs.getInt("id"));
                i.setNombreProducto(rs.getString("nombre_producto"));
                i.setProductoTipoId(rs.getInt("producto_tipo_id"));
                i.setDescripcion(rs.getString("descripcion"));
                i.setFabricante(rs.getString("fabricante"));
                i.setLote(rs.getString("lote"));
                i.setCantidadStock(rs.getInt("cantidad_stock"));
                i.setStockMinimo(rs.getInt("stock_minimo"));
                i.setFechaVencimiento(rs.getDate("fecha_vencimiento"));
                i.setPrecioVenta(rs.getDouble("precio_venta"));
                lista.add(i);
            }
        } catch (SQLException e) {
            logger.warning("Error al listar inventario: " + e.getMessage());
        }
        return lista;
    }

    // BUSCAR POR ID
    public Inventario buscarPorId(int id) {
        String sql = "SELECT * FROM inventario WHERE id = ?";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Inventario i = new Inventario();
                    i.setId(rs.getInt("id"));
                    i.setNombreProducto(rs.getString("nombre_producto"));
                    i.setProductoTipoId(rs.getInt("producto_tipo_id"));
                    i.setDescripcion(rs.getString("descripcion"));
                    i.setFabricante(rs.getString("fabricante"));
                    i.setLote(rs.getString("lote"));
                    i.setCantidadStock(rs.getInt("cantidad_stock"));
                    i.setStockMinimo(rs.getInt("stock_minimo"));
                    i.setFechaVencimiento(rs.getDate("fecha_vencimiento"));
                    i.setPrecioVenta(rs.getDouble("precio_venta"));
                    return i;
                }
            }
        } catch (SQLException e) {
            logger.warning("Error al buscar inventario: " + e.getMessage());
        }
        return null;
    }

    // ACTUALIZAR
    public String actualizar(Inventario inv) {
        String sql = "UPDATE inventario SET nombre_producto=?, producto_tipo_id=?, descripcion=?, " +
                "fabricante=?, lote=?, cantidad_stock=?, stock_minimo=?, fecha_vencimiento=?, precio_venta=? WHERE id=?";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setString(1, inv.getNombreProducto());
            ps.setInt(2, inv.getProductoTipoId());
            ps.setString(3, inv.getDescripcion());
            ps.setString(4, inv.getFabricante());
            ps.setString(5, inv.getLote());
            ps.setInt(6, inv.getCantidadStock());
            ps.setInt(7, inv.getStockMinimo());
            ps.setDate(8, inv.getFechaVencimiento());
            ps.setDouble(9, inv.getPrecioVenta());
            ps.setInt(10, inv.getId());

            int rows = ps.executeUpdate();
            return rows > 0
                    ? "Inventario actualizado correctamente."
                    : "No se encontró inventario con ID " + inv.getId();

        } catch (SQLIntegrityConstraintViolationException e) {
            return "Error de integridad al actualizar (verifique producto_tipo).";
        } catch (SQLException e) {
            logger.warning("Error al actualizar inventario: " + e.getMessage());
            return "Error al actualizar inventario: " + e.getMessage();
        }
    }

    // ELIMINAR
    public String eliminar(int id) {
        String sql = "DELETE FROM inventario WHERE id=?";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, id);
            int rows = ps.executeUpdate();
            return rows > 0
                    ? "Inventario eliminado correctamente."
                    : "No se encontró inventario con ID " + id;

        } catch (SQLIntegrityConstraintViolationException e) {
            return "No se puede eliminar: el registro está referenciado en otra tabla (ej. item_factura).";
        } catch (SQLException e) {
            logger.warning("Error al eliminar inventario: " + e.getMessage());
            return "Error al eliminar inventario: " + e.getMessage();
        }
    }
}
