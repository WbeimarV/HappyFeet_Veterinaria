package com.happyfeet.dao;

import com.happyfeet.modelo.Raza;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class RazaDAO {

    private final Connection conexion;
    private static final Logger logger = Logger.getLogger(RazaDAO.class.getName());

    public RazaDAO(Connection conexion) {
        this.conexion = conexion;
    }

    //  INSERTAR 
    public String insertar(Raza raza) {
        String sql = "INSERT INTO raza (especie_id, nombre) VALUES (?, ?)";
        try (PreparedStatement ps = conexion.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

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
            String msg = e.getMessage().toLowerCase();
            if (msg.contains("foreign key")) {
                return "No se puede insertar: la especie asociada no existe.";
            } else if (msg.contains("duplicate")) {
                return "Ya existe una raza con ese nombre en la misma especie.";
            }
            return "Error de restricción de integridad.";
        } catch (SQLException e) {
            logger.warning("Error al insertar raza: " + e.getMessage());
            return "Error al insertar raza: " + e.getMessage();
        }
    }

    //  ACTUALIZAR 
    public String actualizar(Raza raza) {
        String sql = "UPDATE raza SET especie_id = ?, nombre = ? WHERE id = ?";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setInt(1, raza.getEspecieId());
            ps.setString(2, raza.getNombre());
            ps.setInt(3, raza.getId());

            int filas = ps.executeUpdate();
            return filas > 0
                    ? "Raza actualizada correctamente."
                    : "No se encontró la raza para actualizar.";

        } catch (SQLIntegrityConstraintViolationException e) {
            String msg = e.getMessage().toLowerCase();
            if (msg.contains("foreign key")) {
                return "No se puede actualizar: la especie asociada no existe.";
            } else if (msg.contains("duplicate")) {
                return "Ya existe una raza con ese nombre en la misma especie.";
            }
            return "Error de restricción de integridad.";
        } catch (SQLException e) {
            logger.warning("Error al actualizar raza: " + e.getMessage());
            return "Error al actualizar raza: " + e.getMessage();
        }
    }

    //  ELIMINAR 
    public String eliminar(int id) {
        String sql = "DELETE FROM raza WHERE id = ?";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setInt(1, id);
            int filas = ps.executeUpdate();

            return filas > 0
                    ? "Raza eliminada correctamente."
                    : "No se encontró la raza a eliminar.";

        } catch (SQLIntegrityConstraintViolationException e) {
            return "No se puede eliminar: hay registros que dependen de esta raza.";
        } catch (SQLException e) {
            logger.warning("Error al eliminar raza: " + e.getMessage());
            return "Error al eliminar raza: " + e.getMessage();
        }
    }

    //  BUSCAR POR ID 
    public Raza buscarPorId(int id) {
        String sql = "SELECT * FROM raza WHERE id = ?";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
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
            logger.warning("Error al buscar raza: " + e.getMessage());
        }
        return null;
    }

    //  LISTAR TODAS 
    public List<Raza> listarTodos() {
        List<Raza> lista = new ArrayList<>();
        String sql = "SELECT * FROM raza";
        try (PreparedStatement ps = conexion.prepareStatement(sql);
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
            logger.warning("Error al listar razas: " + e.getMessage());
        }
        return lista;
    }

    //  LISTAR POR ESPECIE 
    public List<Raza> listarPorEspecie(int especieId) {
        List<Raza> lista = new ArrayList<>();
        String sql = "SELECT * FROM raza WHERE especie_id = ?";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
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
            logger.warning("Error al listar razas por especie: " + e.getMessage());
        }
        return lista;
    }
}
