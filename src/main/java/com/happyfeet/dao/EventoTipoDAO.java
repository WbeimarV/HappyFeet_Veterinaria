package com.happyfeet.dao;

import com.happyfeet.modelo.EventoTipo;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EventoTipoDAO {

    private final Connection conexion;

    public EventoTipoDAO(Connection conexion) {
        this.conexion = conexion;
    }

    // ➤ INSERTAR
    public boolean insertar(EventoTipo eventoTipo) {
        String sql = "INSERT INTO evento_tipo (nombre) VALUES (?)";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, eventoTipo.getNombre());
            ps.executeUpdate();
            System.out.println("EventoTipo insertado correctamente");
            return true;
        } catch (SQLIntegrityConstraintViolationException e) {
            // Manejo de duplicado por UNIQUE(nombre)
            System.out.println("No se pudo insertar: el nombre '" + eventoTipo.getNombre() + "' ya existe.");
        } catch (SQLException e) {
            System.out.println("Error al insertar evento_tipo: " + e.getMessage());
        }
        return false;
    }

    // ➤ OBTENER TODOS
    public List<EventoTipo> obtenerTodos() {
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
            System.out.println("Error al obtener evento_tipo: " + e.getMessage());
        }
        return lista;
    }

    // ➤ BUSCAR POR ID
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
            System.out.println("Error al buscar evento_tipo por ID: " + e.getMessage());
        }
        return null;
    }

    // ➤ ACTUALIZAR
    public boolean actualizar(EventoTipo eventoTipo) {
        String sql = "UPDATE evento_tipo SET nombre = ? WHERE id = ?";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setString(1, eventoTipo.getNombre());
            ps.setInt(2, eventoTipo.getId());
            int filas = ps.executeUpdate();
            if (filas > 0) {
                System.out.println("EventoTipo actualizado correctamente");
                return true;
            } else {
                System.out.println("No se encontró un evento_tipo con ID " + eventoTipo.getId());
            }
        } catch (SQLIntegrityConstraintViolationException e) {
            System.out.println("No se pudo actualizar: el nombre '" + eventoTipo.getNombre() + "' ya existe.");
        } catch (SQLException e) {
            System.out.println("Error al actualizar evento_tipo: " + e.getMessage());
        }
        return false;
    }

    // ➤ ELIMINAR
    public boolean eliminar(int id) {
        String sql = "DELETE FROM evento_tipo WHERE id = ?";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, id);
            int filas = ps.executeUpdate();
            if (filas > 0) {
                System.out.println("EventoTipo eliminado correctamente");
                return true;
            } else {
                System.out.println("No se encontró un evento_tipo con ID " + id);
            }
        } catch (SQLIntegrityConstraintViolationException e) {
            // Si algún historial_medico lo está usando, saltará esta excepción
            System.out.println("No se puede eliminar: este evento_tipo está en uso.");
        } catch (SQLException e) {
            System.out.println("Error al eliminar evento_tipo: " + e.getMessage());
        }
        return false;
    }
}
