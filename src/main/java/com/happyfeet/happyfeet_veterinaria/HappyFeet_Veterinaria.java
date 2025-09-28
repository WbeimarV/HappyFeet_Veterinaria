package com.happyfeet.happyfeet_veterinaria;

import java.sql.Connection;

import javax.swing.JOptionPane;

import com.happyfeet.modelo.*;
import com.happyfeet.dao.*;
import com.happyfeet.happyfeet_veterinaria.Conexion;;

public class HappyFeet_Veterinaria {

    public static void main(String[] args) {
        Connection con = null;

        try {
            // conexión
            con = Conexion.getConexion();

            // Menú principal
            boolean salir = false;
            while (!salir) {
                String opcion = JOptionPane.showInputDialog(
                        null,
                        "=== MENÚ PRINCIPAL ===\n"
                      + "1. Dueños y Mascotas\n"
                      + "2. Citas\n"
                      + "3. Historial Médico\n"
                      + "4. Inventario\n"
                      + "0. Salir\n\n"
                      + "Elige una opción:"
                );

                if (opcion == null) { // usuario cerró la ventana
                    break;
                }

                switch (opcion) {
                    case "1":
                        menuDueno(con);
                        break;
                    case "2":
                        menuCitas(con);
                        break;
                    case "3":
                        menuHistorial(con);
                        break;
                    case "4":
                        menuInventario(con);
                        break;
                    case "0":
                        salir = true;
                        break;
                    default:
                        JOptionPane.showMessageDialog(null, "⚠️ Opción no válida");
                }
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error en la aplicación: " + e.getMessage());
        } finally {
            try {
                if (con != null && !con.isClosed()) {
                    con.close();
                    JOptionPane.showMessageDialog(null, "🔒 Conexión cerrada");
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Error al cerrar la conexión");
            }
        }
    }

    // ================== SUBMENÚS ==================
    private static void menuDueno(Connection con) {
        DuenoDAO duenoDAO = new DuenoDAO(con); // DAO adaptado para recibir la conexión
        String op = JOptionPane.showInputDialog(
                null,
                "=== DUEÑOS ===\n"
              + "1. Registrar Dueño\n"
              + "2. Listar Dueños\n"
              + "0. Volver\n\n"
              + "Elige una opción:"
        );
        if ("1".equals(op)) {
            String nombre = JOptionPane.showInputDialog("Nombre completo:");
            String doc    = JOptionPane.showInputDialog("Documento:");
            String dir    = JOptionPane.showInputDialog("Dirección:");
            String tel    = JOptionPane.showInputDialog("Teléfono:");
            String email  = JOptionPane.showInputDialog("Email:");

            try {
                duenoDAO.insertar(
                    new com.happyfeet.modelo.Dueno(nombre, doc, dir, tel, email)
                );
                JOptionPane.showMessageDialog(null, "✅ Dueño registrado");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
            }
        } else if ("2".equals(op)) {
            try {
                var lista = duenoDAO.listar();
                StringBuilder sb = new StringBuilder("=== LISTA DE DUEÑOS ===\n");
                for (var d : lista) {
                    sb.append(d.toString()).append("\n");
                }
                JOptionPane.showMessageDialog(null, sb.toString());
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
            }
        }
    }

    private static void menuCitas(Connection con) {
        JOptionPane.showMessageDialog(null, "Aquí irán las opciones de Citas");
    }

    private static void menuHistorial(Connection con) {
        JOptionPane.showMessageDialog(null, "Aquí irán las opciones de Historial Médico");
    }

    private static void menuInventario(Connection con) {
        JOptionPane.showMessageDialog(null, "Aquí irán las opciones de Inventario");
    }
}
