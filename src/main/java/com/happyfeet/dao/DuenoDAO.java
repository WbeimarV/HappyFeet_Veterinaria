/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.happyfeet.dao;

import com.happyfeet.modelo.Dueno;
import com.happyfeet.happyfeet_veterinaria.Conexion;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DuenoDAO {

    // Crear
    public void insertar(Dueno dueno) throws SQLException {
        String sql = "INSERT INTO dueno(nombre_completo, documento_identidad, direccion, telefono, email) "
                   + "VALUES (?,?,?,?,?)";
        try (Connection con = Conexion.getConexion();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, dueno.getNombreCompleto());
            ps.setString(2, dueno.getDocumentoIdentidad());
            ps.setString(3, dueno.getDireccion());
            ps.setString(4, dueno.getTelefono());
            ps.setString(5, dueno.getEmail());
            ps.executeUpdate();
        }
    }

    // Leer
    public List<Dueno> listar() throws SQLException {
        List<Dueno> lista = new ArrayList<>();
        String sql = "SELECT * FROM dueno";
        try (Connection con = Conexion.getConexion();
             Statement st = con.createStatement();
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
        }
        return lista;
    }
}