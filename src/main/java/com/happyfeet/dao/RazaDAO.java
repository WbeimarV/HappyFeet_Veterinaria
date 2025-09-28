package com.happyfeet.dao;

import com.happyfeet.modelo.Raza;
import com.happyfeet.happyfeet_veterinaria.Conexion;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class RazaDAO {

    // ---------------------- INSERTAR ----------------------
    public String insertar(Raza raza) {
        String sql = "INSERT INTO raza (especie_id, nombre) VALUES (?, ?)";
        try (Connection conn = Conexion.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, raza.getEspecieId());
            ps.setString(2, raza.getNombre());
            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    raza.setId(rs.getInt(1));
                }
            }
            return "Raza insertada con éxito: ID " + raza.getId();

        } catch (SQLIntegrityConstraintViolationException e) {
            // Puede ser duplicado (UNIQUE especie_id + nombre)
            // o especie inexistente (FK)
            String msg = e.getMessage().toLowerCase();
            if (msg.contains("foreign key")) {
                return "No se puede insertar: la especie asociada no existe.";
            } else if (msg.contains("duplicate")) {
                return "Ya existe una raza con ese nombre en la misma especie.";
            }
            return "Error de restricción de integridad.";
        } catch (SQLException e) {
            return "Error al insertar raza: " + e.getMessage();
        }
    }

    // ---------------------- ACTUALIZAR ----------------------
    public String actualizar(Raza raza) {
        String sql = "UPDATE raza SET especie_id = ?, nombre = ? WHERE id = ?";
        try (Connection conn = Conexion.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, raza.getEspecieId());
            ps.setString(2, raza.getNombre());
            ps.setInt(3, raza.getId());

            int filas = ps.executeUpdate();
            if (filas > 0) {
                return "Raza actualizada correctamente.";
            } else {
                return "No se encontró la raza para actualizar.";
            }

        } catch (SQLIntegrityConstraintViolationException e) {
            String msg = e.getMessage().toLowerCase();
            if (msg.contains("foreign key")) {
                return "No se puede actualizar: la especie asociada no existe.";
            } else if (msg.contains("duplicate")) {
                return "Ya existe una raza con ese nombre en la misma especie.";
            }
            return "Error de restricción de integridad.";
        } catch (SQLException e) {
            return "Error al actualizar raza: " + e.getMessage();
        }
    }

    // ---------------------- ELIMINAR ----------------------
    public String eliminar(int id) {
        String sql = "DELETE FROM raza WHERE id = ?";
        try (Connection conn = Conexion.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            int filas = ps.executeUpdate();

            if (filas > 0) {
                return "Raza eliminada correctamente.";
            } else {
                return "No se encontró la raza a eliminar.";
            }

        } catch (SQLIntegrityConstraintViolationException e) {
            // Si la raza está referenciada (p. ej., por mascota)
            return "No se puede eliminar: hay registros que dependen de esta raza.";
        } catch (SQLException e) {
            return "Error al eliminar raza: " + e.getMessage();
        }
    }

    // ---------------------- BUSCAR POR ID ----------------------
    public Raza buscarPorId(int id) {
        String sql = "SELECT * FROM raza WHERE id = ?";
        try (Connection conn = Conexion.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Raza raza = new Raza();
                    raza.setId(rs.getInt("id"));
                    raza.setEspecieId(rs.getInt("especie_id"));
                    raza.setNombre(rs.getString("nombre"));
                    raza.setCreatedAt(rs.getTimestamp("created_at"));
                    return raza;
                }
            }

        } catch (SQLException e) {
            System.out.println("Error al buscar raza: " + e.getMessage());
        }
        return null;
    }

    // ---------------------- LISTAR TODAS ----------------------
    public List<Raza> listarTodos() {
        List<Raza> lista = new ArrayList<>();
        String sql = "SELECT * FROM raza";
        try (Connection conn = Conexion.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Raza raza = new Raza();
                raza.setId(rs.getInt("id"));
                raza.setEspecieId(rs.getInt("especie_id"));
                raza.setNombre(rs.getString("nombre"));
                raza.setCreatedAt(rs.getTimestamp("created_at"));
                lista.add(raza);
            }

        } catch (SQLException e) {
            System.out.println("Error al listar razas: " + e.getMessage());
        }
        return lista;
    }

    // ---------------------- LISTAR POR ESPECIE ----------------------
    public List<Raza> listarPorEspecie(int especieId) {
        List<Raza> lista = new ArrayList<>();
        String sql = "SELECT * FROM raza WHERE especie_id = ?";
        try (Connection conn = Conexion.getConexion();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, especieId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Raza raza = new Raza();
                    raza.setId(rs.getInt("id"));
                    raza.setEspecieId(rs.getInt("especie_id"));
                    raza.setNombre(rs.getString("nombre"));
                    raza.setCreatedAt(rs.getTimestamp("created_at"));
                    lista.add(raza);
                }
            }

        } catch (SQLException e) {
            System.out.println("Error al listar razas por especie: " + e.getMessage());
        }
        return lista;
    }
}
