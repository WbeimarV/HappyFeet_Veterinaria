package com.happyfeet.dao;

import com.happyfeet.modelo.Factura;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class FacturaDAO {

    private final Connection conexion;
    private static final Logger logger = Logger.getLogger(FacturaDAO.class.getName());

    public FacturaDAO(Connection conexion) {
        this.conexion = conexion;
    }

    // INSERTAR
    public String insertar(Factura f) {
        String sql = "INSERT INTO factura (dueno_id, fecha_emision, total) VALUES (?, ?, ?)";
        try (PreparedStatement ps = conexion.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, f.getDuenoId());
            // Insertar la fecha actual si no viene definida
            Timestamp fecha = (f.getFechaEmision() != null)
                    ? Timestamp.valueOf(f.getFechaEmision())
                    : Timestamp.valueOf(java.time.LocalDateTime.now());
            ps.setTimestamp(2, fecha);
            ps.setDouble(3, f.getTotal());

            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    f.setId(rs.getInt(1));
                }
            }
            return "Factura insertada con éxito. ID: " + f.getId();

        } catch (SQLIntegrityConstraintViolationException e) {
            return "No se pudo insertar: el dueño con ID " + f.getDuenoId() + " no existe.";
        } catch (SQLException e) {
            logger.warning("Error al insertar factura: " + e.getMessage());
            return "Error al insertar factura: " + e.getMessage();
        }
    }

    // LISTAR TODOS
    public List<Factura> listarTodos() {
        List<Factura> lista = new ArrayList<>();
        String sql = "SELECT * FROM factura";
        try (PreparedStatement ps = conexion.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Factura f = new Factura();
                f.setId(rs.getInt("id"));
                f.setDuenoId(rs.getInt("dueno_id"));
                Timestamp fe = rs.getTimestamp("fecha_emision");
                if (fe != null)
                    f.setFechaEmision(fe.toLocalDateTime());
                f.setTotal(rs.getDouble("total"));
                Timestamp ts = rs.getTimestamp("created_at");
                if (ts != null)
                    f.setCreatedAt(ts.toLocalDateTime());
                lista.add(f);
            }
        } catch (SQLException e) {
            logger.warning("Error al listar facturas: " + e.getMessage());
        }
        return lista;
    }

    // BUSCAR POR ID
    public Factura buscarPorId(int id) {
        String sql = "SELECT * FROM factura WHERE id = ?";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Factura f = new Factura();
                    f.setId(rs.getInt("id"));
                    f.setDuenoId(rs.getInt("dueno_id"));
                    Timestamp fe = rs.getTimestamp("fecha_emision");
                    if (fe != null)
                        f.setFechaEmision(fe.toLocalDateTime());
                    f.setTotal(rs.getDouble("total"));
                    Timestamp ts = rs.getTimestamp("created_at");
                    if (ts != null)
                        f.setCreatedAt(ts.toLocalDateTime());
                    return f;
                }
            }
        } catch (SQLException e) {
            logger.warning("Error al buscar factura: " + e.getMessage());
        }
        return null;
    }

    // ACTUALIZAR
    public String actualizar(Factura f) {
        String sql = "UPDATE factura SET dueno_id = ?, total = ? WHERE id = ?";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, f.getDuenoId());
            ps.setDouble(2, f.getTotal());
            ps.setInt(3, f.getId());

            int filas = ps.executeUpdate();
            return filas > 0
                    ? "Factura actualizada correctamente."
                    : "No se encontró factura con ID " + f.getId();

        } catch (SQLIntegrityConstraintViolationException e) {
            return "No se pudo actualizar: el dueño con ID " + f.getDuenoId() + " no existe.";
        } catch (SQLException e) {
            logger.warning("Error al actualizar factura: " + e.getMessage());
            return "Error al actualizar factura: " + e.getMessage();
        }
    }

    // ➤ ELIMINAR
    public String eliminar(int id) {
        String sql = "DELETE FROM factura WHERE id = ?";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, id);
            int filas = ps.executeUpdate();
            return filas > 0
                    ? "Factura eliminada correctamente."
                    : "No se encontró factura con ID " + id;

        } catch (SQLIntegrityConstraintViolationException e) {
            return "No se puede eliminar: esta factura tiene ítems asociados.";
        } catch (SQLException e) {
            logger.warning("Error al eliminar factura: " + e.getMessage());
            return "Error al eliminar factura: " + e.getMessage();
        }
    }

    public void actualizarTotal(int id, double total) {
        String sql = "UPDATE factura SET total=? WHERE id=?";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setDouble(1, total);
            ps.setInt(2, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            logger.warning("Error al actualizar total de factura: " + e.getMessage());
        }
    }

}
