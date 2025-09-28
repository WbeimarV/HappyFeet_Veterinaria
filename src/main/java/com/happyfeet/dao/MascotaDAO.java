package com.happyfeet.dao;

import com.happyfeet.modelo.Mascota;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class MascotaDAO {

    private final Connection conexion;
    private static final Logger logger = Logger.getLogger(MascotaDAO.class.getName());

    public MascotaDAO(Connection conexion) {
        this.conexion = conexion;
    }

    // INSERTAR
    public String insertar(Mascota m) {
        String sql = "INSERT INTO mascota(dueno_id, nombre, raza_id, fecha_nacimiento, sexo, url_foto) " +
                     "VALUES (?,?,?,?,?,?)";
        try (PreparedStatement ps = conexion.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, m.getDuenoId());
            ps.setString(2, m.getNombre());
            ps.setInt(3, m.getRazaId());

            if (m.getFechaNacimiento() != null) {
                ps.setDate(4, Date.valueOf(m.getFechaNacimiento()));
            } else {
                ps.setNull(4, Types.DATE);
            }

            ps.setString(5, m.getSexo());
            ps.setString(6, m.getUrlFoto());
            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {
                if (rs.next()) {
                    m.setId(rs.getInt(1));
                }
            }
            return "Mascota insertada con éxito. ID: " + m.getId();

        } catch (SQLIntegrityConstraintViolationException e) {
            return "Error de integridad: revisa dueno_id o raza_id.";
        } catch (SQLException e) {
            logger.warning("Error al insertar mascota: " + e.getMessage());
            return "Error al insertar mascota: " + e.getMessage();
        }
    }

    // LISTAR TODAS
    public List<Mascota> listar() {
        List<Mascota> lista = new ArrayList<>();
        String sql = "SELECT * FROM mascota";
        try (PreparedStatement ps = conexion.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Mascota m = new Mascota();
                m.setId(rs.getInt("id"));
                m.setDuenoId(rs.getInt("dueno_id"));
                m.setNombre(rs.getString("nombre"));
                m.setRazaId(rs.getInt("raza_id"));

                Date fecha = rs.getDate("fecha_nacimiento");
                if (fecha != null) {
                    m.setFechaNacimiento(fecha.toLocalDate());
                }

                m.setSexo(rs.getString("sexo"));
                m.setUrlFoto(rs.getString("url_foto"));
                m.setCreatedAt(rs.getString("created_at"));
                lista.add(m);
            }
        } catch (SQLException e) {
            logger.warning("Error al listar mascotas: " + e.getMessage());
        }
        return lista;
    }

    // BUSCAR POR ID
    public Mascota buscarPorId(int id) {
        String sql = "SELECT * FROM mascota WHERE id = ?";
        try (PreparedStatement ps = conexion.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Mascota m = new Mascota();
                    m.setId(rs.getInt("id"));
                    m.setDuenoId(rs.getInt("dueno_id"));
                    m.setNombre(rs.getString("nombre"));
                    m.setRazaId(rs.getInt("raza_id"));

                    Date fecha = rs.getDate("fecha_nacimiento");
                    if (fecha != null) {
                        m.setFechaNacimiento(fecha.toLocalDate());
                    }

                    m.setSexo(rs.getString("sexo"));
                    m.setUrlFoto(rs.getString("url_foto"));
                    m.setCreatedAt(rs.getString("created_at"));
                    return m;
                }
            }
        } catch (SQLException e) {
            logger.warning("Error al buscar mascota: " + e.getMessage());
        }
        return null;
    }
}
