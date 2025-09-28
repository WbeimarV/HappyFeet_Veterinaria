package com.happyfeet.dao;

import com.happyfeet.modelo.ItemFactura;
import com.happyfeet.happyfeet_veterinaria.Conexion;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ItemFacturaDAO {

    public void insertar(ItemFactura i) throws SQLException {
        String sql = "INSERT INTO item_factura (factura_id, producto_id, servicio_descripcion, " +
                     "cantidad, precio_unitario, subtotal) VALUES (?,?,?,?,?,?)";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, i.getFacturaId());
            if (i.getProductoId() != null) ps.setInt(2, i.getProductoId());
            else ps.setNull(2, Types.INTEGER);
            ps.setString(3, i.getServicioDescripcion());
            ps.setInt(4, i.getCantidad());
            ps.setDouble(5, i.getPrecioUnitario());
            ps.setDouble(6, i.getSubtotal());
            ps.executeUpdate();
        }
    }

    public List<ItemFactura> listar() throws SQLException {
        List<ItemFactura> lista = new ArrayList<>();
        String sql = "SELECT * FROM item_factura";
        try (Connection con = Conexion.getConexion();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

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
        return lista;
    }

    public ItemFactura buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM item_factura WHERE id = ?";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
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
        }
        return null;
    }
}
