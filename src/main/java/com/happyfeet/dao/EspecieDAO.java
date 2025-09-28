package com.happyfeet.dao;

import com.happyfeet.modelo.Especie;
import com.happyfeet.happyfeet_veterinaria.Conexion;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EspecieDAO {

    // ---------------------- INSERTAR ----------------------
    public String insertar(Especie especie) {
        String sql = "INSERT INTO especie (nombre) VALUES (?)";
        try (Connection conn = Conexion.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, especie.getNombre());
            ps.executeUpdate();

            // Recuperar ID generado automáticamente
            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    especie.setId(rs.getInt(1));
                }
            }
            return "Especie insertada con éxito: ID " + especie.getId();

        } catch (SQLIntegrityConstraintViolationException e) {
            // Ocurre si se intenta insertar un nombre que ya existe (UNIQUE)
            return "⚠️ No se pudo insertar, el nombre ya existe.";
        } catch (SQLException e) {
            return "Error al insertar especie: " + e.getMessage();
        }
    }

    // ---------------------- ACTUALIZAR ----------------------
    public String actualizar(Especie especie) {
        String sql = "UPDATE especie SET nombre = ? WHERE id = ?";
        try (Connection conn = Conexion.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, especie.getNombre());
            ps.setInt(2, especie.getId());

            int filas = ps.executeUpdate();
            if (filas > 0) {
                return "Especie actualizada correctamente.";
            } else {
                return "No se encontró la especie para actualizar.";
            }

        } catch (SQLIntegrityConstraintViolationException e) {
            // Si intentas actualizar a un nombre que ya existe
            return "No se pudo actualizar, el nombre ya existe.";
        } catch (SQLException e) {
            return "Error al actualizar especie: " + e.getMessage();
        }
    }

    // ---------------------- ELIMINAR ----------------------
    public String eliminar(int id) {
        String sql = "DELETE FROM especie WHERE id = ?";
        try (Connection conn = Conexion.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            int filas = ps.executeUpdate();

            if (filas > 0) {
                return "Especie eliminada correctamente.";
            } else {
                return "No se encontró la especie a eliminar.";
            }

        } catch (SQLIntegrityConstraintViolationException e) {
            // FK: no se puede eliminar porque tiene razas asociadas
            return "No se puede eliminar: hay razas asociadas a esta especie.";
        } catch (SQLException e) {
            return "Error al eliminar especie: " + e.getMessage();
        }
    }

    // ---------------------- BUSCAR POR ID ----------------------
    public Especie buscarPorId(int id) {
        String sql = "SELECT * FROM especie WHERE id = ?";
        try (Connection conn = Conexion.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

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
            System.out.println("Error al buscar especie: " + e.getMessage());
        }
        return null; // Si no se encuentra
    }

    // ---------------------- LISTAR TODAS ----------------------
    public List<Especie> listarTodos() {
        List<Especie> lista = new ArrayList<>();
        String sql = "SELECT * FROM especie";
        try (Connection conn = Conexion.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Especie especie = new Especie();
                especie.setId(rs.getInt("id"));
                especie.setNombre(rs.getString("nombre"));
                especie.setCreatedAt(rs.getTimestamp("created_at"));
                lista.add(especie);
            }

        } catch (SQLException e) {
            System.out.println("Error al listar especies: " + e.getMessage());
        }
        return lista;
    }
}