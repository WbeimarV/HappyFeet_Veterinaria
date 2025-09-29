package com.happyfeet.happyfeet_veterinaria.menu;

import com.happyfeet.dao.MascotaDAO;
import com.happyfeet.dao.RazaDAO;
import com.happyfeet.modelo.Mascota;
import com.happyfeet.modelo.Raza;

import javax.swing.*;
import java.sql.Connection;
import java.time.LocalDate;
import java.util.List;

public class MenuMascota {

    public static void mostrar(Connection con) {
        MascotaDAO dao = new MascotaDAO(con);
        RazaDAO razaDao = new RazaDAO(con);

        boolean volver = false;
        while (!volver) {
            String op = JOptionPane.showInputDialog(
                    null,
                    " MENÚ MASCOTAS \n"
                            + "1. Registrar\n"
                            + "2. Listar\n"
                            + "3. Modificar\n"
                            + "4. Eliminar\n"
                            + "0. Volver");

            if (op == null)
                break;

            try {
                switch (op) {
                    case "1" -> registrar(dao, razaDao);
                    case "2" -> listar(dao, razaDao);
                    case "3" -> modificar(dao, razaDao);
                    case "4" -> eliminar(dao);
                    case "0" -> volver = true;
                    default -> JOptionPane.showMessageDialog(null, "⚠️ Opción inválida");
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
            }
        }
    }

    // REGISTRAR
    private static void registrar(MascotaDAO dao, RazaDAO razaDao) {
        try {
            int duenoId = Integer.parseInt(JOptionPane.showInputDialog("ID del dueño:"));
            String nombre = JOptionPane.showInputDialog("Nombre de la mascota:");

            // Mostrar las razas y pedir el ID en el mismo cuadro
            List<Raza> razas = razaDao.listarTodos();
            if (razas.isEmpty()) {
                JOptionPane.showMessageDialog(null, "No hay razas registradas. Registre una raza primero.");
                return;
            }

            StringBuilder sb = new StringBuilder("RAZAS DISPONIBLES\n");
            for (Raza r : razas) {
                sb.append("ID: ").append(r.getId())
                        .append(" | ").append(r.getNombre())
                        .append("\n");
            }
            sb.append("\nIngrese el ID de la raza:");

            int razaId = Integer.parseInt(JOptionPane.showInputDialog(sb.toString()));

            String fecha = JOptionPane.showInputDialog("Fecha de nacimiento (YYYY-MM-DD):");
            String sexo = JOptionPane.showInputDialog("Sexo (Macho/Hembra)");

            Mascota m = new Mascota();
            m.setDuenoId(duenoId);
            m.setNombre(nombre);
            m.setRazaId(razaId);

            if (fecha != null && !fecha.isBlank()) {
                m.setFechaNacimiento(LocalDate.parse(fecha));
            }

            m.setSexo((sexo != null && !sexo.isBlank()) ? sexo : null);
            m.setUrlFoto(null);

            String mensaje = dao.insertar(m);
            JOptionPane.showMessageDialog(null, mensaje);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Entrada inválida.");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al registrar: " + e.getMessage());
        }
    }

    // LISTAR
    private static void listar(MascotaDAO dao, RazaDAO razaDao) {
        List<Mascota> lista = dao.listar();
        if (lista.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No hay mascotas registradas.");
            return;
        }

        StringBuilder sb = new StringBuilder("=== LISTA DE MASCOTAS ===\n");
        for (Mascota m : lista) {

            String nombreRaza = "";
            Raza r = razaDao.buscarPorId(m.getRazaId());
            if (r != null) {
                nombreRaza = r.getNombre();
            }

            sb.append(" | Dueño ID: ").append(m.getDuenoId())
                    .append(" | Nombre: ").append(m.getNombre())
                    .append(" | Raza: ").append(nombreRaza)
                    .append(" | Nacimiento: ")
                    .append(m.getFechaNacimiento() != null ? m.getFechaNacimiento() : "-")
                    .append(" | Sexo: ")
                    .append(m.getSexo() != null ? m.getSexo() : "-")
                    .append("\n");
        }

        JOptionPane.showMessageDialog(null, sb.toString());
    }

    // MODIFICAR
    private static void modificar(MascotaDAO dao, RazaDAO razaDao) {
        try {
            int id = Integer.parseInt(JOptionPane.showInputDialog("ID de la mascota a modificar:"));
            Mascota m = dao.buscarPorId(id);

            if (m == null) {
                JOptionPane.showMessageDialog(null, "No se encontró una mascota con ese ID.");
                return;
            }

            // Pedir nuevos datos
            String nuevoNombre = JOptionPane.showInputDialog(
                    "Nuevo nombre (" + m.getNombre() + "):", m.getNombre());

            // Mostrar razas 
            List<Raza> razas = razaDao.listarTodos();
            if (razas.isEmpty()) {
                JOptionPane.showMessageDialog(null, "No hay razas registradas. Registre una raza primero.");
                return;
            }

            StringBuilder sb = new StringBuilder("RAZAS DISPONIBLES\n");
            for (Raza r : razas) {
                sb.append("ID: ").append(r.getId())
                        .append(" | ").append(r.getNombre())
                        .append("\n");
            }
            sb.append("\nNuevo ID de raza (actual: ").append(m.getRazaId()).append("):");

            String razaInput = JOptionPane.showInputDialog(sb.toString(), m.getRazaId());

            String fechaInput = JOptionPane.showInputDialog(
                    "Nueva fecha de nacimiento (YYYY-MM-DD) (" +
                            (m.getFechaNacimiento() != null ? m.getFechaNacimiento() : "sin dato") + "):");

            // Actualizar objeto con los nuevos datos
            m.setNombre(nuevoNombre != null && !nuevoNombre.isBlank() ? nuevoNombre : m.getNombre());

            if (razaInput != null && !razaInput.isBlank())
                m.setRazaId(Integer.parseInt(razaInput));

            if (fechaInput != null && !fechaInput.isBlank())
                m.setFechaNacimiento(LocalDate.parse(fechaInput));

            // 🔹 Guardar cambios
            String mensaje = dao.actualizar(m);
            JOptionPane.showMessageDialog(null, mensaje);

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "⚠️ Entrada inválida.");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al modificar: " + e.getMessage());
        }
    }

    // ELIMINAR
    private static void eliminar(MascotaDAO dao) {
        try {
            int id = Integer.parseInt(JOptionPane.showInputDialog("ID de la mascota a eliminar:"));
            int conf = JOptionPane.showConfirmDialog(null,
                    "¿Eliminar la mascota con ID " + id + "?", "Confirmar",
                    JOptionPane.YES_NO_OPTION);
            if (conf == JOptionPane.YES_OPTION) {
                boolean ok = dao.eliminar(id);
                JOptionPane.showMessageDialog(null,
                        ok ? "Mascota eliminada correctamente."
                                : "No se encontró la mascota con ese ID.");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "⚠️ El ID debe ser un número.");
        }
    }
}
