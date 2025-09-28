package com.happyfeet.dao;

import com.happyfeet.modelo.HistorialMedico;
import com.happyfeet.happyfeet_veterinaria.Conexion;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class HistorialMedicoDAO {

    // Insertar
    public void insertar(HistorialMedico h) throws SQLException {
        String sql = "INSERT INTO historial_medico " +
                     "(mascota_id, fecha_evento, evento_tipo_id, descripcion, diagnostico, tratamiento_recomendado) " +
                     "VALUES (?,?,?,?,?,?)";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, h.getMascotaId());
            ps.setTimestamp(2, Timestamp.valueOf(h.getFechaEvento()));
            ps.setInt(3, h.getEventoTipoId());
            ps.setString(4, h.getDescripcion());
            ps.setString(5, h.getDiagnostico());
            ps.setString(6, h.getTratamientoRecomendado());
            ps.executeUpdate();
        }
    }

    // Listar todos
    public List<HistorialMedico> listar() throws SQLException {
        List<HistorialMedico> lista = new ArrayList<>();
        String sql = "SELECT * FROM historial_medico";
        try (Connection con = Conexion.getConexion();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                HistorialMedico h = new HistorialMedico();
                h.setId(rs.getInt("id"));
                h.setMascotaId(rs.getInt("mascota_id"));
                h.setFechaEvento(rs.getTimestamp("fecha_evento").toLocalDateTime());
                h.setEventoTipoId(rs.getInt("evento_tipo_id"));
                h.setDescripcion(rs.getString("descripcion"));
                h.setDiagnostico(rs.getString("diagnostico"));
                h.setTratamientoRecomendado(rs.getString("tratamiento_recomendado"));
                Timestamp ts = rs.getTimestamp("created_at");
                if (ts != null) h.setCreatedAt(ts.toLocalDateTime());
                lista.add(h);
            }
        }
        return lista;
    }

    // Buscar por ID
    public HistorialMedico buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM historial_medico WHERE id = ?";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    HistorialMedico h = new HistorialMedico();
                    h.setId(rs.getInt("id"));
                    h.setMascotaId(rs.getInt("mascota_id"));
                    h.setFechaEvento(rs.getTimestamp("fecha_evento").toLocalDateTime());
                    h.setEventoTipoId(rs.getInt("evento_tipo_id"));
                    h.setDescripcion(rs.getString("descripcion"));
                    h.setDiagnostico(rs.getString("diagnostico"));
                    h.setTratamientoRecomendado(rs.getString("tratamiento_recomendado"));
                    Timestamp ts = rs.getTimestamp("created_at");
                    if (ts != null) h.setCreatedAt(ts.toLocalDateTime());
                    return h;
                }
            }
        }
        return null;
    }
}
