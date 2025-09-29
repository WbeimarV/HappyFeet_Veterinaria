package com.happyfeet.happyfeet_veterinaria.menu;

import java.sql.Connection;
import javax.swing.JOptionPane;
import com.happyfeet.dao.DuenoDAO;
import com.happyfeet.modelo.Dueno;

public class MenuDueno {

    public static void mostrar(Connection con) {
        DuenoDAO dao = new DuenoDAO(con);
        boolean volver = false;

        while (!volver) {
            String op = JOptionPane.showInputDialog(
                    null,
                    " MENÚ DUEÑOS \n"
                  + "1. Registrar\n"
                  + "2. Listar\n"
                  + "3. Eliminar\n"
                  + "0. Volver"
            );

            if (op == null) break;

            try {
                switch (op) {
                    case "1" -> registrar(dao);
                    case "2" -> listar(dao);
                    case "3" -> eliminar(dao);
                    case "0" -> volver = true;
                    default  -> JOptionPane.showMessageDialog(null, "Opción inválida");
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
            }
        }
    }   

    private static void registrar(DuenoDAO dao) throws Exception {
        String nombre = JOptionPane.showInputDialog("Nombre completo:");
        String doc    = JOptionPane.showInputDialog("Documento:");
        String dir    = JOptionPane.showInputDialog("Dirección:");
        String tel    = JOptionPane.showInputDialog("Teléfono:");
        String email  = JOptionPane.showInputDialog("Email:");

        dao.insertar(new Dueno(nombre, doc, dir, tel, email));
        JOptionPane.showMessageDialog(null, "Dueño registrado");
    }

    private static void listar(DuenoDAO dao) throws Exception {
        var lista = dao.listar();
        StringBuilder sb = new StringBuilder(" LISTA DE DUEÑOS \n");
        for (Dueno d : lista) {
            sb.append("ID: ").append(d.getId())
              .append(" | Nombre: ").append(d.getNombreCompleto())
              .append(" | Tel: ").append(d.getTelefono())
              .append("\n");
        }
        JOptionPane.showMessageDialog(null,
            sb.length() > 0 ? sb.toString() : "No hay registros");
    }

    private static void eliminar(DuenoDAO dao) throws Exception {
        String idStr = JOptionPane.showInputDialog("ID del dueño a eliminar:");
        if (idStr != null) {
            int id = Integer.parseInt(idStr);
            dao.eliminar(id);
            JOptionPane.showMessageDialog(null, "Dueño eliminado");
        }
    }
}
