package com.happyfeet.dao;

import com.happyfeet.modelo.EventoTipo;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class EventoTipoDAO {

    private final Connection conexion;
    private static final Logger logger = Logger.getLogger(EventoTipoDAO.class.getName());

    public EventoTipoDAO(Connection conexion) {
        this.conexion = conexion;
    }

    // INSERTAR
    public String insertar(EventoTipo eventoTipo) {
        String sql = "INSERT INTO evento_tipo (nombre) VALUES (?)";
        try (PreparedStatement ps = conexion.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, eventoTipo.getNombre());
            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    eventoTipo.setId(rs.getInt(1));
                }
            }
            return "EventoTipo insertado con éxito. ID: " + eventoTipo.getId();

        } catch (SQLIntegrityConstraintViolationException e) {
            return "No se pudo insertar, el nombre '" + eventoTipo.getNombre() + "' ya existe.";
        } catch (SQLException e) {
            logger.warning("Error al insertar evento_tipo: " + e.getMessage());
            return "Error al insertar evento_tipo: " + e.getMessage();
        }
    }

    // LISTAR TODOS
    public List<EventoTipo> listarTodos() {
        List<EventoTipo> lista = new ArrayList<>();
        String sql = "SELECT * FROM evento_tipo";
        try (PreparedStatement ps = conexion.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(new EventoTipo(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getTimestamp("created_at")
                ));
            }
        } catch (SQLException e) {
            logger.warning("Error al listar evento_tipo: " + e.getMessage());
        }
        return lista;
    }

    // BUSCAR POR ID
    public EventoTipo buscarPorId(int id) {
        String sql = "SELECT * FROM evento_tipo WHERE id = ?";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new EventoTipo(
                            rs.getInt("id"),
                            rs.getString("nombre"),
                            rs.getTimestamp("created_at")
                    );
                }
            }
        } catch (SQLException e) {
            logger.warning("Error al buscar evento_tipo por ID: " + e.getMessage());
        }
        return null;
    }

    // ACTUALIZAR
    public String actualizar(EventoTipo eventoTipo) {
        String sql = "UPDATE evento_tipo SET nombre = ? WHERE id = ?";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, eventoTipo.getNombre());
            ps.setInt(2, eventoTipo.getId());

            int filas = ps.executeUpdate();
            return filas > 0
                    ? "EventoTipo actualizado correctamente."
                    : "No se encontró un evento_tipo con ID " + eventoTipo.getId();

        } catch (SQLIntegrityConstraintViolationException e) {
            return "No se pudo actualizar, el nombre '" + eventoTipo.getNombre() + "' ya existe.";
        } catch (SQLException e) {
            logger.warning("Error al actualizar evento_tipo: " + e.getMessage());
            return "Error al actualizar evento_tipo: " + e.getMessage();
        }
    }

    // ELIMINAR
    public String eliminar(int id) {
        String sql = "DELETE FROM evento_tipo WHERE id = ?";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, id);
            int filas = ps.executeUpdate();
            return filas > 0
                    ? "EventoTipo eliminado correctamente."
                    : "No se encontró un evento_tipo con ID " + id;

        } catch (SQLIntegrityConstraintViolationException e) {
            return "No se puede eliminar: este evento_tipo está en uso.";
        } catch (SQLException e) {
            logger.warning("Error al eliminar evento_tipo: " + e.getMessage());
            return "Error al eliminar evento_tipo: " + e.getMessage();
        }
    }
}
