/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.happyfeet.dao;

import com.happyfeet.happyfeet_veterinaria.Conexion;
import com.happyfeet.modelo.Mascota;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MascotaDAO {

    // 🔹 Crear (INSERT)
    public void insertar(Mascota m) throws SQLException {
        String sql = "INSERT INTO mascota(dueno_id, nombre, raza_id, fecha_nacimiento, sexo, url_foto) "
                   + "VALUES (?,?,?,?,?,?)";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, m.getDuenoId());
            ps.setString(2, m.getNombre());
            ps.setInt(3, m.getRazaId());

            // fecha_nacimiento puede ser nula
            if (m.getFechaNacimiento() != null) {
                ps.setDate(4, Date.valueOf(m.getFechaNacimiento()));
            } else {
                ps.setNull(4, Types.DATE);
            }

            ps.setString(5, m.getSexo());
            ps.setString(6, m.getUrlFoto());
            ps.executeUpdate();
        }
    }

    // 🔹 Listar todas
    public List<Mascota> listar() throws SQLException {
        List<Mascota> lista = new ArrayList<>();
        String sql = "SELECT * FROM mascota";

        try (Connection con = Conexion.getConexion();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

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
        }
        return lista;
    }

    // 🔹 Buscar por ID
    public Mascota buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM mascota WHERE id = ?";
        Mascota m = null;

        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    m = new Mascota();
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
                }
            }
        }
        return m;
    }
}

