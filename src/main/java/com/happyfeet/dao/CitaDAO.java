package com.happyfeet.dao;

import com.happyfeet.modelo.Cita;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CitaDAO {

    private static final Logger logger = Logger.getLogger(CitaDAO.class.getName());
    private final Connection conn; // Conexión recibida desde el main

    public CitaDAO(Connection conn) {
        this.conn = conn;
    }

    //  INSERTAR 
    public String insertar(Cita c) {
        String sql = "INSERT INTO cita (mascota_id, fecha_hora, motivo, estado_id) VALUES (?,?,?,?)";
        try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, c.getMascotaId());
            ps.setTimestamp(2, Timestamp.valueOf(c.getFechaHora()));
            ps.setString(3, c.getMotivo());
            ps.setInt(4, c.getEstadoId());
            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) c.setId(rs.getInt(1));
            }

            return "Cita registrada con éxito (ID: " + c.getId() + ")";
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error al registrar cita", e);
            return "Error al registrar cita: " + e.getMessage();
        }
    }

    //  ACTUALIZAR 
    public String actualizar(Cita c) {
        String sql = "UPDATE cita SET mascota_id = ?, fecha_hora = ?, motivo = ?, estado_id = ? WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, c.getMascotaId());
            ps.setTimestamp(2, Timestamp.valueOf(c.getFechaHora()));
            ps.setString(3, c.getMotivo());
            ps.setInt(4, c.getEstadoId());
            ps.setInt(5, c.getId());
            int filas = ps.executeUpdate();

            if (filas > 0) return "Cita actualizada correctamente.";
            else return "No se encontró la cita a actualizar.";
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error al actualizar cita", e);
            return "Error al actualizar cita: " + e.getMessage();
        }
    }

    //  ELIMINAR 
    public String eliminar(int id) {
        String sql = "DELETE FROM cita WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            int filas = ps.executeUpdate();
            if (filas > 0) return "Cita eliminada correctamente.";
            else return "No se encontró la cita a eliminar.";
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error al eliminar cita", e);
            return "Error al eliminar cita: " + e.getMessage();
        }
    }

    //  LISTAR 
    public List<Cita> listar() {
        List<Cita> lista = new ArrayList<>();
        String sql = "SELECT * FROM cita";
        try (Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                Cita c = new Cita();
                c.setId(rs.getInt("id"));
                c.setMascotaId(rs.getInt("mascota_id"));
                c.setFechaHora(rs.getTimestamp("fecha_hora").toLocalDateTime());
                c.setMotivo(rs.getString("motivo"));
                c.setEstadoId(rs.getInt("estado_id"));
                Timestamp ts = rs.getTimestamp("created_at");
                if (ts != null) c.setCreatedAt(ts.toLocalDateTime());
                lista.add(c);
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error al listar citas", e);
        }
        return lista;
    }

    //  BUSCAR POR ID 
    public Cita buscarPorId(int id) {
        String sql = "SELECT * FROM cita WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Cita c = new Cita();
                    c.setId(rs.getInt("id"));
                    c.setMascotaId(rs.getInt("mascota_id"));
                    c.setFechaHora(rs.getTimestamp("fecha_hora").toLocalDateTime());
                    c.setMotivo(rs.getString("motivo"));
                    c.setEstadoId(rs.getInt("estado_id"));
                    Timestamp ts = rs.getTimestamp("created_at");
                    if (ts != null) c.setCreatedAt(ts.toLocalDateTime());
                    return c;
                }
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error al buscar cita por ID", e);
        }
        return null;
    }
}
