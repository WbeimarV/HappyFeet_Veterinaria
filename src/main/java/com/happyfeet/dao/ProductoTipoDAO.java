package com.happyfeet.dao;

import com.happyfeet.modelo.ProductoTipo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class ProductoTipoDAO {

    private final Connection conexion;
    private static final Logger logger = Logger.getLogger(ProductoTipoDAO.class.getName());

    public ProductoTipoDAO(Connection conexion) {
        this.conexion = conexion;
    }

    //  INSERTAR 
    public String insertar(ProductoTipo tipo) {
        String sql = "INSERT INTO producto_tipo (nombre) VALUES (?)";
        try (PreparedStatement ps = conexion.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, tipo.getNombre());
            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) tipo.setId(rs.getInt(1));
            }

            return "Tipo de producto insertado con éxito (ID: " + tipo.getId() + ")";

        } catch (SQLIntegrityConstraintViolationException e) {
            if (e.getMessage().toLowerCase().contains("duplicate")) {
                return "Ya existe un tipo de producto con ese nombre.";
            }
            return "Error de restricción de integridad.";
        } catch (SQLException e) {
            logger.warning("Error al insertar tipo de producto: " + e.getMessage());
            return "Error al insertar tipo de producto: " + e.getMessage();
        }
    }

    //  ACTUALIZAR 
    public String actualizar(ProductoTipo tipo) {
        String sql = "UPDATE producto_tipo SET nombre = ? WHERE id = ?";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setString(1, tipo.getNombre());
            ps.setInt(2, tipo.getId());
            int filas = ps.executeUpdate();

            return filas > 0
                    ? "Tipo de producto actualizado correctamente."
                    : "No se encontró el tipo de producto a actualizar.";

        } catch (SQLIntegrityConstraintViolationException e) {
            if (e.getMessage().toLowerCase().contains("duplicate")) {
                return "Ya existe un tipo de producto con ese nombre.";
            }
            return "Error de restricción de integridad.";
        } catch (SQLException e) {
            logger.warning("Error al actualizar tipo de producto: " + e.getMessage());
            return "Error al actualizar tipo de producto: " + e.getMessage();
        }
    }

    //  ELIMINAR 
    public String eliminar(int id) {
        String sql = "DELETE FROM producto_tipo WHERE id = ?";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setInt(1, id);
            int filas = ps.executeUpdate();

            return filas > 0
                    ? "Tipo de producto eliminado correctamente."
                    : "No se encontró el tipo de producto a eliminar.";

        } catch (SQLIntegrityConstraintViolationException e) {
            return "No se puede eliminar: existen productos en inventario que usan este tipo.";
        } catch (SQLException e) {
            logger.warning("Error al eliminar tipo de producto: " + e.getMessage());
            return "Error al eliminar tipo de producto: " + e.getMessage();
        }
    }

    //  BUSCAR POR ID 
    public ProductoTipo buscarPorId(int id) {
        String sql = "SELECT * FROM producto_tipo WHERE id = ?";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    ProductoTipo tipo = new ProductoTipo();
                    tipo.setId(rs.getInt("id"));
                    tipo.setNombre(rs.getString("nombre"));
                    tipo.setCreatedAt(rs.getTimestamp("created_at"));
                    return tipo;
                }
            }
        } catch (SQLException e) {
            logger.warning("Error al buscar tipo de producto: " + e.getMessage());
        }
        return null;
    }

    //  LISTAR TODOS 
    public List<ProductoTipo> listarTodos() {
        List<ProductoTipo> lista = new ArrayList<>();
        String sql = "SELECT * FROM producto_tipo ORDER BY id";
        try (PreparedStatement ps = conexion.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                ProductoTipo tipo = new ProductoTipo();
                tipo.setId(rs.getInt("id"));
                tipo.setNombre(rs.getString("nombre"));
                tipo.setCreatedAt(rs.getTimestamp("created_at"));
                lista.add(tipo);
            }
        } catch (SQLException e) {
            logger.warning("Error al listar tipos de producto: " + e.getMessage());
        }
        return lista;
    }
}
