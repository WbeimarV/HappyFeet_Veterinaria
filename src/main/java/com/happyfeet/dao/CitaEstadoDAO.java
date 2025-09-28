package com.happyfeet.dao;

import com.happyfeet.modelo.CitaEstado;
import com.happyfeet.happyfeet_veterinaria.Conexion;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CitaEstadoDAO {

    public String insertar(CitaEstado estado) {
        String sql = "INSERT INTO cita_estado (nombre) VALUES (?)";
        try (Connection conn = Conexion.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, estado.getNombre());
            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    estado.setId(rs.getInt(1));
                }
            }
            return "CitaEstado insertado con ID " + estado.getId();

        } catch (SQLIntegrityConstraintViolationException e) {
            return "No se pudo insertar, el nombre ya existe.";
        } catch (SQLException e) {
            return "Error al insertar CitaEstado: " + e.getMessage();
        }
    }

    public List<CitaEstado> listarTodos() {
        List<CitaEstado> lista = new ArrayList<>();
        String sql = "SELECT * FROM cita_estado";
        try (Connection conn = Conexion.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(new CitaEstado(
                        rs.getInt("id"),
                        rs.getString("nombre"),
                        rs.getString("created_at")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Error al listar: " + e.getMessage());
        }
        return lista;
    }

    public CitaEstado buscarPorId(int id) {
        String sql = "SELECT * FROM cita_estado WHERE id = ?";
        try (Connection conn = Conexion.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

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
            System.err.println("Error al buscar: " + e.getMessage());
        }
        return null;
    }

    public String actualizar(CitaEstado estado) {
        String sql = "UPDATE cita_estado SET nombre = ? WHERE id = ?";
        try (Connection conn = Conexion.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, estado.getNombre());
            ps.setInt(2, estado.getId());

            int filas = ps.executeUpdate();
            return filas > 0 ? "CitaEstado actualizado correctamente."
                             : "No se encontró el registro a actualizar.";
        } catch (SQLIntegrityConstraintViolationException e) {
            return "No se pudo actualizar, el nombre ya existe.";
        } catch (SQLException e) {
            return "Error al actualizar: " + e.getMessage();
        }
    }

    public String eliminar(int id) {
        String sql = "DELETE FROM cita_estado WHERE id = ?";
        try (Connection conn = Conexion.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            int filas = ps.executeUpdate();
            return filas > 0 ? "CitaEstado eliminado correctamente."
                             : "No se encontró el registro a eliminar.";
        } catch (SQLIntegrityConstraintViolationException e) {
            return "No se puede eliminar, el registro está en uso.";
        } catch (SQLException e) {
            return "Error al eliminar: " + e.getMessage();
        }
    }
}
