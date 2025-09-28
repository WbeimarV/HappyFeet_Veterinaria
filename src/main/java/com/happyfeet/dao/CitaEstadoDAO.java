package com.happyfeet.dao;

import com.happyfeet.modelo.CitaEstado;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CitaEstadoDAO {

    private static final Logger logger = Logger.getLogger(CitaEstadoDAO.class.getName());
    private final Connection conn; // Conexión única

    public CitaEstadoDAO(Connection conn) {
        this.conn = conn;
    }

    //  INSERTAR 
    public String insertar(CitaEstado estado) {
        String sql = "INSERT INTO cita_estado (nombre) VALUES (?)";
        try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, estado.getNombre());
            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) estado.setId(rs.getInt(1));
            }
            return "CitaEstado insertado con éxito (ID: " + estado.getId() + ")";

        } catch (SQLIntegrityConstraintViolationException e) {
            return "No se pudo insertar: el nombre ya existe.";
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error al insertar CitaEstado", e);
            return "Error al insertar CitaEstado: " + e.getMessage();
        }
    }

    //  LISTAR 
    public List<CitaEstado> listarTodos() {
        List<CitaEstado> lista = new ArrayList<>();
        String sql = "SELECT * FROM cita_estado";
        try (PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(new CitaEstado(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("created_at")
                ));
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error al listar CitaEstado", e);
        }
        return lista;
    }

    //  BUSCAR POR ID 
    public CitaEstado buscarPorId(int id) {
        String sql = "SELECT * FROM cita_estado WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new CitaEstado(
                            rs.getInt("id"),
                            rs.getString("nombre"),
                            rs.getString("created_at")
                    );
                }
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error al buscar CitaEstado por ID", e);
        }
        return null;
    }

    //  ACTUALIZAR 
    public String actualizar(CitaEstado estado) {
        String sql = "UPDATE cita_estado SET nombre = ? WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, estado.getNombre());
            ps.setInt(2, estado.getId());

            int filas = ps.executeUpdate();
            return filas > 0 ? "CitaEstado actualizado correctamente."
                             : "No se encontró el registro a actualizar.";
        } catch (SQLIntegrityConstraintViolationException e) {
            return "No se pudo actualizar: el nombre ya existe.";
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error al actualizar CitaEstado", e);
            return "Error al actualizar: " + e.getMessage();
        }
    }

    //  ELIMINAR 
    public String eliminar(int id) {
        String sql = "DELETE FROM cita_estado WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            int filas = ps.executeUpdate();
            return filas > 0 ? "CitaEstado eliminado correctamente."
                             : "No se encontró el registro a eliminar.";
        } catch (SQLIntegrityConstraintViolationException e) {
            return "No se puede eliminar: el registro está en uso.";
        } catch (SQLException e) {
            logger.log(Level.SEVERE, "Error al eliminar CitaEstado", e);
            return "Error al eliminar: " + e.getMessage();
        }
    }
}
