package com.happyfeet.dao;

import com.happyfeet.modelo.Estado;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class EstadoDAO {

    private final Connection conexion;
    private static final Logger logger = Logger.getLogger(EstadoDAO.class.getName());

    public EstadoDAO(Connection conexion) {
        this.conexion = conexion;
    }

    // LISTAR TODOS
    public List<Estado> listar() {
        List<Estado> lista = new ArrayList<>();
        String sql = "SELECT * FROM cita_estado";

        try (PreparedStatement ps = conexion.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Estado e = new Estado();
                e.setId(rs.getInt("id"));
                e.setNombre(rs.getString("nombre"));
                Timestamp ts = rs.getTimestamp("created_at");
                if (ts != null) e.setCreatedAt(ts.toLocalDateTime());
                lista.add(e);
            }

        } catch (SQLException ex) {
            logger.warning("Error al listar estados: " + ex.getMessage());
        }
        return lista;
    }

    // BUSCAR POR ID
    public Estado buscarPorId(int id) {
        String sql = "SELECT * FROM cita_estado WHERE id = ?";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Estado e = new Estado();
                    e.setId(rs.getInt("id"));
                    e.setNombre(rs.getString("nombre"));
                    Timestamp ts = rs.getTimestamp("created_at");
                    if (ts != null) e.setCreatedAt(ts.toLocalDateTime());
                    return e;
                }
            }
        } catch (SQLException ex) {
            logger.warning("Error al buscar estado: " + ex.getMessage());
        }
        return null;
    }
}
