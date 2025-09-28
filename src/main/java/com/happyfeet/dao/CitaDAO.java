package com.happyfeet.dao;

import com.happyfeet.modelo.Cita;
import com.happyfeet.happyfeet_veterinaria.Conexion;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class CitaDAO {

    public void insertar(Cita c) throws SQLException {
        String sql = "INSERT INTO cita (mascota_id, fecha_hora, motivo, estado_id) VALUES (?,?,?,?)";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, c.getMascotaId());
            ps.setTimestamp(2, Timestamp.valueOf(c.getFechaHora()));
            ps.setString(3, c.getMotivo());
            ps.setInt(4, c.getEstadoId());
            ps.executeUpdate();
        }
    }

    public List<Cita> listar() throws SQLException {
        List<Cita> lista = new ArrayList<>();
        String sql = "SELECT * FROM cita";
        try (Connection con = Conexion.getConexion();
             Statement st = con.createStatement();
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
        }
        return lista;
    }

    public Cita buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM cita WHERE id = ?";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
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
        }
        return null;
    }
}
