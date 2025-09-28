package com.happyfeet.dao;

import com.happyfeet.modelo.HistorialMedico;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class HistorialMedicoDAO {

    private final Connection conexion;
    private static final Logger logger = Logger.getLogger(HistorialMedicoDAO.class.getName());

    public HistorialMedicoDAO(Connection conexion) {
        this.conexion = conexion;
    }

    // INSERTAR
    public String insertar(HistorialMedico h) {
        String sql = "INSERT INTO historial_medico " +
                "(mascota_id, fecha_evento, evento_tipo_id, descripcion, diagnostico, tratamiento_recomendado) " +
                "VALUES (?,?,?,?,?,?)";
        try (PreparedStatement ps = conexion.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, h.getMascotaId());
            ps.setTimestamp(2, Timestamp.valueOf(h.getFechaEvento()));
            ps.setInt(3, h.getEventoTipoId());
            ps.setString(4, h.getDescripcion());
            ps.setString(5, h.getDiagnostico());
            ps.setString(6, h.getTratamientoRecomendado());
            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    h.setId(rs.getInt(1));
                }
            }
            return "Historial médico insertado con éxito. ID: " + h.getId();

        } catch (SQLIntegrityConstraintViolationException e) {
            return "No se pudo insertar: mascota o tipo de evento inexistente.";
        } catch (SQLException e) {
            logger.warning("Error al insertar historial médico: " + e.getMessage());
            return "Error al insertar historial médico: " + e.getMessage();
        }
    }

    // LISTAR TODOS
    public List<HistorialMedico> listarTodos() {
        List<HistorialMedico> lista = new ArrayList<>();
        String sql = "SELECT * FROM historial_medico";
        try (PreparedStatement ps = conexion.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

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
        } catch (SQLException e) {
            logger.warning("Error al listar historial médico: " + e.getMessage());
        }
        return lista;
    }

    // BUSCAR POR ID
    public HistorialMedico buscarPorId(int id) {
        String sql = "SELECT * FROM historial_medico WHERE id = ?";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
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
        } catch (SQLException e) {
            logger.warning("Error al buscar historial médico: " + e.getMessage());
        }
        return null;
    }

    // ACTUALIZAR
    public String actualizar(HistorialMedico h) {
        String sql = "UPDATE historial_medico " +
                "SET mascota_id = ?, fecha_evento = ?, evento_tipo_id = ?, descripcion = ?, diagnostico = ?, tratamiento_recomendado = ? " +
                "WHERE id = ?";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, h.getMascotaId());
            ps.setTimestamp(2, Timestamp.valueOf(h.getFechaEvento()));
            ps.setInt(3, h.getEventoTipoId());
            ps.setString(4, h.getDescripcion());
            ps.setString(5, h.getDiagnostico());
            ps.setString(6, h.getTratamientoRecomendado());
            ps.setInt(7, h.getId());

            int filas = ps.executeUpdate();
            return filas > 0
                    ? "Historial médico actualizado correctamente."
                    : "No se encontró historial médico con ID " + h.getId();

        } catch (SQLIntegrityConstraintViolationException e) {
            return "No se pudo actualizar: mascota o tipo de evento inexistente.";
        } catch (SQLException e) {
            logger.warning("Error al actualizar historial médico: " + e.getMessage());
            return "Error al actualizar historial médico: " + e.getMessage();
        }
    }

    // ELIMINAR
    public String eliminar(int id) {
        String sql = "DELETE FROM historial_medico WHERE id = ?";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, id);
            int filas = ps.executeUpdate();
            return filas > 0
                    ? "Historial médico eliminado correctamente."
                    : "No se encontró historial médico con ID " + id;

        } catch (SQLException e) {
            logger.warning("Error al eliminar historial médico: " + e.getMessage());
            return "Error al eliminar historial médico: " + e.getMessage();
        }
    }
}
