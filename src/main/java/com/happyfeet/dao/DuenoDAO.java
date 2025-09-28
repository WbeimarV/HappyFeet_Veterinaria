package com.happyfeet.dao;

import com.happyfeet.modelo.Dueno;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DuenoDAO {

    private static final Logger LOGGER = Logger.getLogger(DuenoDAO.class.getName());
    private final Connection conn; // Conexión única

    public DuenoDAO(Connection conn) {
        this.conn = conn;
    }

    // Crear
    public String insertar(Dueno dueno) {
        String sql = "INSERT INTO dueno(nombre_completo, documento_identidad, direccion, telefono, email) "
                   + "VALUES (?,?,?,?,?)";
        try (PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, dueno.getNombreCompleto());
            ps.setString(2, dueno.getDocumentoIdentidad());
            ps.setString(3, dueno.getDireccion());
            ps.setString(4, dueno.getTelefono());
            ps.setString(5, dueno.getEmail());
            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    dueno.setId(rs.getInt(1));
                }
            }
            LOGGER.info("Dueño insertado con ID " + dueno.getId());
            return "Dueño insertado con ID " + dueno.getId();

        } catch (SQLIntegrityConstraintViolationException e) {
            LOGGER.warning("No se pudo insertar, documento o email duplicado: " + e.getMessage());
            return "No se pudo insertar, el documento o email ya existe.";
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al insertar dueño", e);
            return "Error al insertar dueño: " + e.getMessage();
        }
    }

    // Leer
    public List<Dueno> listar() {
        List<Dueno> lista = new ArrayList<>();
        String sql = "SELECT * FROM dueno";
        try (Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                Dueno d = new Dueno();
                d.setId(rs.getInt("id"));
                d.setNombreCompleto(rs.getString("nombre_completo"));
                d.setDocumentoIdentidad(rs.getString("documento_identidad"));
                d.setDireccion(rs.getString("direccion"));
                d.setTelefono(rs.getString("telefono"));
                d.setEmail(rs.getString("email"));
                lista.add(d);
            }
            LOGGER.info("Se listaron " + lista.size() + " dueños.");
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al listar dueños", e);
        }
        return lista;
    }

    // Buscar por ID
    public Dueno buscarPorId(int id) {
        String sql = "SELECT * FROM dueno WHERE id = ?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Dueno d = new Dueno();
                    d.setId(rs.getInt("id"));
                    d.setNombreCompleto(rs.getString("nombre_completo"));
                    d.setDocumentoIdentidad(rs.getString("documento_identidad"));
                    d.setDireccion(rs.getString("direccion"));
                    d.setTelefono(rs.getString("telefono"));
                    d.setEmail(rs.getString("email"));
                    return d;
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al buscar dueño con id=" + id, e);
        }
        return null;
    }

    // Actualizar
    public String actualizar(Dueno dueno) {
        String sql = "UPDATE dueno SET nombre_completo=?, documento_identidad=?, direccion=?, telefono=?, email=? WHERE id=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, dueno.getNombreCompleto());
            ps.setString(2, dueno.getDocumentoIdentidad());
            ps.setString(3, dueno.getDireccion());
            ps.setString(4, dueno.getTelefono());
            ps.setString(5, dueno.getEmail());
            ps.setInt(6, dueno.getId());

            int filas = ps.executeUpdate();
            if (filas > 0) {
                LOGGER.info("Dueño con ID " + dueno.getId() + " actualizado correctamente.");
                return "Dueño actualizado correctamente.";
            } else {
                LOGGER.warning("No se encontró el dueño con ID " + dueno.getId() + " para actualizar.");
                return "No se encontró el dueño a actualizar.";
            }

        } catch (SQLIntegrityConstraintViolationException e) {
            LOGGER.warning("No se pudo actualizar, documento o email duplicado: " + e.getMessage());
            return "No se pudo actualizar, el documento o email ya existe.";
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al actualizar dueño con id=" + dueno.getId(), e);
            return "Error al actualizar dueño: " + e.getMessage();
        }
    }

    // Eliminar
    public String eliminar(int id) {
        String sql = "DELETE FROM dueno WHERE id=?";
        try (PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            int filas = ps.executeUpdate();
            if (filas > 0) {
                LOGGER.info("Dueño con ID " + id + " eliminado correctamente.");
                return "Dueño eliminado correctamente.";
            } else {
                LOGGER.warning("No se encontró el dueño con ID " + id + " para eliminar.");
                return "No se encontró el dueño a eliminar.";
            }
        } catch (SQLIntegrityConstraintViolationException e) {
            LOGGER.warning("No se puede eliminar el dueño con ID " + id + ", tiene registros asociados.");
            return "No se puede eliminar, el dueño tiene registros asociados.";
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Error al eliminar dueño con id=" + id, e);
            return "Error al eliminar dueño: " + e.getMessage();
        }
    }
}
