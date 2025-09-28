package com.happyfeet.dao;

import com.happyfeet.modelo.Factura;
import com.happyfeet.happyfeet_veterinaria.Conexion;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class FacturaDAO {

    public void insertar(Factura f) throws SQLException {
        String sql = "INSERT INTO factura (dueno_id, total) VALUES (?, ?)";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, f.getDuenoId());
            ps.setDouble(2, f.getTotal());
            ps.executeUpdate();
        }
    }

    public List<Factura> listar() throws SQLException {
        List<Factura> lista = new ArrayList<>();
        String sql = "SELECT * FROM factura";
        try (Connection con = Conexion.getConexion();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                Factura f = new Factura();
                f.setId(rs.getInt("id"));
                f.setDuenoId(rs.getInt("dueno_id"));
                Timestamp fe = rs.getTimestamp("fecha_emision");
                if (fe != null) f.setFechaEmision(fe.toLocalDateTime());
                f.setTotal(rs.getDouble("total"));
                Timestamp ts = rs.getTimestamp("created_at");
                if (ts != null) f.setCreatedAt(ts.toLocalDateTime());
                lista.add(f);
            }
        }
        return lista;
    }

    public Factura buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM factura WHERE id = ?";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Factura f = new Factura();
                    f.setId(rs.getInt("id"));
                    f.setDuenoId(rs.getInt("dueno_id"));
                    Timestamp fe = rs.getTimestamp("fecha_emision");
                    if (fe != null) f.setFechaEmision(fe.toLocalDateTime());
                    f.setTotal(rs.getDouble("total"));
                    Timestamp ts = rs.getTimestamp("created_at");
                    if (ts != null) f.setCreatedAt(ts.toLocalDateTime());
                    return f;
                }
            }
        }
        return null;
    }
}
