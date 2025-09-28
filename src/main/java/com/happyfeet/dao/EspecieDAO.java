package com.happyfeet.dao;

import com.happyfeet.modelo.Especie;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class EspecieDAO {

    private static final Logger LOGGER = Logger.getLogger(EspecieDAO.class.getName());
    private final Connection conn; // Conexión única

    public EspecieDAO(Connection conn) {
        this.conn = conn;
    }

    // INSERTAR 
    public String insertar(Especie especie) {
        String sql = "INSERT INTO especie (nombre) VALUES (?)";
        try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, especie.getNombre());
            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    especie.setId(rs.getInt(1));
                }
            }
            LOGGER.info("Especie insertada con éxito: ID " + especie.getId());
            return "Especie insertada con éxito: ID " + especie.getId();

        } catch (SQLIntegrityConstraintViolationException e) {
            LOGGER.warning("No se pudo insertar especie, nombre duplicado: " + e.getMessage());
            return "⚠️ No se pudo insertar, el nombre ya existe.";
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al insertar especie", e);
            return "Error al insertar especie: " + e.getMessage();
        }
    }

    // ACTUALIZAR 
    public String actualizar(Especie especie) {
        String sql = "UPDATE especie SET nombre = ? WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, especie.getNombre());
            ps.setInt(2, especie.getId());

            int filas = ps.executeUpdate();
            if (filas > 0) {
                LOGGER.info("Especie con ID " + especie.getId() + " actualizada correctamente.");
                return "Especie actualizada correctamente.";
            } else {
                LOGGER.warning("No se encontró especie con ID " + especie.getId() + " para actualizar.");
                return "No se encontró la especie para actualizar.";
            }

        } catch (SQLIntegrityConstraintViolationException e) {
            LOGGER.warning("No se pudo actualizar especie, nombre duplicado: " + e.getMessage());
            return "No se pudo actualizar, el nombre ya existe.";
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al actualizar especie con id=" + especie.getId(), e);
            return "Error al actualizar especie: " + e.getMessage();
        }
    }

    // ELIMINAR 
    public String eliminar(int id) {
        String sql = "DELETE FROM especie WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            int filas = ps.executeUpdate();
            if (filas > 0) {
                LOGGER.info("Especie con ID " + id + " eliminada correctamente.");
                return "Especie eliminada correctamente.";
            } else {
                LOGGER.warning("No se encontró especie con ID " + id + " para eliminar.");
                return "No se encontró la especie a eliminar.";
            }

        } catch (SQLIntegrityConstraintViolationException e) {
            LOGGER.warning("No se puede eliminar especie con ID " + id + ", tiene razas asociadas.");
            return "No se puede eliminar: hay razas asociadas a esta especie.";
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al eliminar especie con id=" + id, e);
            return "Error al eliminar especie: " + e.getMessage();
        }
    }

    // BUSCAR POR ID 
    public Especie buscarPorId(int id) {
        String sql = "SELECT * FROM especie WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Especie especie = new Especie();
                    especie.setId(rs.getInt("id"));
                    especie.setNombre(rs.getString("nombre"));
                    especie.setCreatedAt(rs.getTimestamp("created_at"));
                    return especie;
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al buscar especie con id=" + id, e);
        }
        return null;
    }

    // LISTAR TODAS 
    public List<Especie> listarTodos() {
        List<Especie> lista = new ArrayList<>();
        String sql = "SELECT * FROM especie";
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Especie especie = new Especie();
                especie.setId(rs.getInt("id"));
                especie.setNombre(rs.getString("nombre"));
                especie.setCreatedAt(rs.getTimestamp("created_at"));
                lista.add(especie);
            }
            LOGGER.info("Se listaron " + lista.size() + " especies.");
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al listar especies", e);
        }
        return lista;
    }
}
