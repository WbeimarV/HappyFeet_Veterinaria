package com.happyfeet.dao;

import com.happyfeet.modelo.ItemFactura;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class ItemFacturaDAO {

    private final Connection conexion;
    private static final Logger logger = Logger.getLogger(ItemFacturaDAO.class.getName());

    public ItemFacturaDAO(Connection conexion) {
        this.conexion = conexion;
    }

    // INSERTAR
    public String insertar(ItemFactura i) {
        String sql = "INSERT INTO item_factura (factura_id, producto_id, servicio_descripcion, " +
                     "cantidad, precio_unitario, subtotal) VALUES (?,?,?,?,?,?)";
        try (PreparedStatement ps = conexion.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, i.getFacturaId());

            if (i.getProductoId() != null) {
                ps.setInt(2, i.getProductoId());
            } else {
                ps.setNull(2, Types.INTEGER);
            }

            ps.setString(3, i.getServicioDescripcion());
            ps.setInt(4, i.getCantidad());
            ps.setDouble(5, i.getPrecioUnitario());
            ps.setDouble(6, i.getSubtotal());

            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) i.setId(rs.getInt(1));
            }

            return "ItemFactura insertado con éxito. ID: " + i.getId();

        } catch (SQLException e) {
            logger.warning("Error al insertar ItemFactura: " + e.getMessage());
            return "Error al insertar ItemFactura: " + e.getMessage();
        }
    }

    // LISTAR TODOS
    public List<ItemFactura> listar() {
        List<ItemFactura> lista = new ArrayList<>();
        String sql = "SELECT * FROM item_factura";
        try (PreparedStatement ps = conexion.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                ItemFactura i = new ItemFactura();
                i.setId(rs.getInt("id"));
                i.setFacturaId(rs.getInt("factura_id"));
                int pid = rs.getInt("producto_id");
                i.setProductoId(rs.wasNull() ? null : pid);
                i.setServicioDescripcion(rs.getString("servicio_descripcion"));
                i.setCantidad(rs.getInt("cantidad"));
                i.setPrecioUnitario(rs.getDouble("precio_unitario"));
                i.setSubtotal(rs.getDouble("subtotal"));
                Timestamp ts = rs.getTimestamp("created_at");
                if (ts != null) i.setCreatedAt(ts.toLocalDateTime());
                lista.add(i);
            }
        } catch (SQLException e) {
            logger.warning("Error al listar ItemFactura: " + e.getMessage());
        }
        return lista;
    }

    // BUSCAR POR ID
    public ItemFactura buscarPorId(int id) {
        String sql = "SELECT * FROM item_factura WHERE id = ?";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    ItemFactura i = new ItemFactura();
                    i.setId(rs.getInt("id"));
                    i.setFacturaId(rs.getInt("factura_id"));
                    int pid = rs.getInt("producto_id");
                    i.setProductoId(rs.wasNull() ? null : pid);
                    i.setServicioDescripcion(rs.getString("servicio_descripcion"));
                    i.setCantidad(rs.getInt("cantidad"));
                    i.setPrecioUnitario(rs.getDouble("precio_unitario"));
                    i.setSubtotal(rs.getDouble("subtotal"));
                    Timestamp ts = rs.getTimestamp("created_at");
                    if (ts != null) i.setCreatedAt(ts.toLocalDateTime());
                    return i;
                }
            }
        } catch (SQLException e) {
            logger.warning("Error al buscar ItemFactura con ID " + id + ": " + e.getMessage());
        }
        return null;
    }

    // LISTAR POR FACTURA
public List<ItemFactura> listarPorFactura(int facturaId) {
    List<ItemFactura> lista = new ArrayList<>();
    String sql = "SELECT * FROM item_factura WHERE factura_id = ?";
    try (PreparedStatement ps = conexion.prepareStatement(sql)) {
        ps.setInt(1, facturaId);
        try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                ItemFactura i = new ItemFactura();
                i.setId(rs.getInt("id"));
                i.setFacturaId(rs.getInt("factura_id"));
                int pid = rs.getInt("producto_id");
                i.setProductoId(rs.wasNull() ? null : pid);
                i.setServicioDescripcion(rs.getString("servicio_descripcion"));
                i.setCantidad(rs.getInt("cantidad"));
                i.setPrecioUnitario(rs.getDouble("precio_unitario"));
                i.setSubtotal(rs.getDouble("subtotal"));
                Timestamp ts = rs.getTimestamp("created_at");
                if (ts != null) i.setCreatedAt(ts.toLocalDateTime());
                lista.add(i);
            }
        }
    } catch (SQLException e) {
        logger.warning("Error al listar items de factura " + facturaId + ": " + e.getMessage());
    }
    return lista;
}

}
