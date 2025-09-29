package com.happyfeet.happyfeet_veterinaria.menu;

import com.happyfeet.dao.CitaDAO;
import com.happyfeet.dao.MascotaDAO;
import com.happyfeet.dao.EstadoDAO;
import com.happyfeet.modelo.Cita;
import com.happyfeet.modelo.Mascota;
import com.happyfeet.modelo.Estado;

import javax.swing.*;
import java.sql.Connection;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class MenuCita {

    public static void mostrar(Connection con) {
        CitaDAO citaDAO = new CitaDAO(con);
        MascotaDAO mascotaDAO = new MascotaDAO(con);
        EstadoDAO estadoDAO = new EstadoDAO(con);

        boolean volver = false;

        while (!volver) {
            String op = JOptionPane.showInputDialog(
                    null,
                    " MENÚ CITAS \n"
                            + "1. Registrar\n"
                            + "2. Listar\n"
                            + "3. Modificar\n"
                            + "4. Eliminar\n"
                            + "0. Volver");

            if (op == null)
                break;

            try {
                switch (op) {
                    case "1" -> registrar(citaDAO, mascotaDAO, estadoDAO);
                    case "2" -> listar(citaDAO, estadoDAO, mascotaDAO);
                    case "3" -> modificar(citaDAO, mascotaDAO, estadoDAO);
                    case "4" -> eliminar(citaDAO, estadoDAO, mascotaDAO);
                    case "0" -> volver = true;
                    default -> JOptionPane.showMessageDialog(null, "Opción inválida");
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Error: " + e.getMessage());
            }
        }
    }

    // REGISTRAR
    private static void registrar(CitaDAO citaDAO, MascotaDAO mascotaDAO, EstadoDAO estadoDAO) {
        try {
            // Mostrar mascotas
            List<Mascota> mascotas = mascotaDAO.listar();
            if (mascotas.isEmpty()) {
                JOptionPane.showMessageDialog(null, "No hay mascotas registradas.");
                return;
            }
            StringBuilder sm = new StringBuilder("MASCOTAS DISPONIBLES:\n");
            for (Mascota m : mascotas) {
                sm.append(m.getId()).append(" - ").append(m.getNombre()).append("\n");
            }
            String mascotaIdStr = JOptionPane.showInputDialog(sm + "\nIngrese ID de la mascota:");
            if (mascotaIdStr == null)
                return;
            int mascotaId = Integer.parseInt(mascotaIdStr);

            // Fecha y hora
            String fechaStr = JOptionPane.showInputDialog("Ingrese fecha y hora (yyyy-MM-dd HH:mm):");
            if (fechaStr == null)
                return;
            LocalDateTime fechaHora = LocalDateTime.parse(fechaStr, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));

            // Motivo
            String motivo = JOptionPane.showInputDialog("Ingrese motivo de la cita:");
            if (motivo == null)
                return;

            // Estados disponibles
            List<Estado> estados = estadoDAO.listar();
            if (estados.isEmpty()) {
                JOptionPane.showMessageDialog(null, "No hay estados registrados.");
                return;
            }
            StringBuilder se = new StringBuilder("ESTADOS DISPONIBLES:\n");
            for (Estado e : estados) {
                se.append(e.getId()).append(" - ").append(e.getNombre()).append("\n");
            }
            String estadoIdStr = JOptionPane.showInputDialog(se + "\nIngrese ID del estado:");
            if (estadoIdStr == null)
                return;
            int estadoId = Integer.parseInt(estadoIdStr);

            // Crear objeto
            Cita c = new Cita();
            c.setMascotaId(mascotaId);
            c.setFechaHora(fechaHora);
            c.setMotivo(motivo);
            c.setEstadoId(estadoId);

            JOptionPane.showMessageDialog(null, citaDAO.insertar(c));
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al registrar: " + e.getMessage());
        }
    }

    // LISTAR
    private static void listar(CitaDAO citaDao, EstadoDAO estadoDao, MascotaDAO mascotaDao) {
        try {
            String idStr = JOptionPane.showInputDialog("Ingrese el ID de la mascota:");
            if (idStr == null || idStr.isBlank())
                return;

            int mascotaId = Integer.parseInt(idStr);

            // Buscar la mascota para obtener su nombre
            Mascota mascota = mascotaDao.buscarPorId(mascotaId);
            if (mascota == null) {
                JOptionPane.showMessageDialog(null, "⚠️ No existe una mascota con ese ID.");
                return;
            }

            // Traer todas las citas
            List<Cita> todas = citaDao.listar();

            // Filtrar por mascota_id
            List<Cita> filtradas = todas.stream()
                    .filter(c -> c.getMascotaId() == mascotaId)
                    .toList();

            if (filtradas.isEmpty()) {
                JOptionPane.showMessageDialog(null, "No hay citas registradas para la mascota: " + mascota.getNombre());
                return;
            }

            StringBuilder sb = new StringBuilder("CITAS DE " + mascota.getNombre().toUpperCase() + " \n");
            for (Cita c : filtradas) {
                // Buscar el nombre del estado
                String nombreEstado = "-";
                Estado est = estadoDao.buscarPorId(c.getEstadoId());
                if (est != null)
                    nombreEstado = est.getNombre();

                sb.append("ID: ").append(c.getId())
                        .append(" | Fecha: ").append(c.getFechaHora())
                        .append(" | Motivo: ").append(c.getMotivo())
                        .append(" | Estado: ").append(nombreEstado)
                        .append("\n");
            }

            JOptionPane.showMessageDialog(null, sb.toString());

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "El ID debe ser un número válido.");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al listar citas: " + e.getMessage());
        }
    }

    // MODIFICAR
    private static void modificar(CitaDAO citaDAO, MascotaDAO mascotaDAO, EstadoDAO estadoDAO) {
        try {
            // 1. Pedir ID de la mascota
            String idMascotaStr = JOptionPane.showInputDialog("Ingrese ID de la mascota:");
            if (idMascotaStr == null || idMascotaStr.isBlank())
                return;

            int mascotaId = Integer.parseInt(idMascotaStr);
            Mascota mascota = mascotaDAO.buscarPorId(mascotaId);
            if (mascota == null) {
                JOptionPane.showMessageDialog(null, "⚠️ No existe una mascota con ese ID.");
                return;
            }

            // 2. Listar citas de esa mascota
            List<Cita> todas = citaDAO.listar();
            List<Cita> filtradas = todas.stream()
                    .filter(c -> c.getMascotaId() == mascotaId)
                    .toList();

            if (filtradas.isEmpty()) {
                JOptionPane.showMessageDialog(null, "No hay citas registradas para la mascota: " + mascota.getNombre());
                return;
            }

            StringBuilder sb = new StringBuilder("=== CITAS DE LA MASCOTA " + mascota.getNombre() + " ===\n");
            for (Cita c : filtradas) {
                String nombreEstado = "-";
                Estado est = estadoDAO.buscarPorId(c.getEstadoId());
                if (est != null)
                    nombreEstado = est.getNombre();

                sb.append("ID: ").append(c.getId())
                        .append(" | Fecha: ").append(c.getFechaHora())
                        .append(" | Motivo: ").append(c.getMotivo())
                        .append(" | Estado: ").append(nombreEstado)
                        .append("\n");
            }
            sb.append("\nIngrese el ID de la cita a modificar:");

            String idCitaStr = JOptionPane.showInputDialog(null, sb.toString());
            if (idCitaStr == null || idCitaStr.isBlank())
                return;

            int idCita = Integer.parseInt(idCitaStr);
            Cita c = citaDAO.buscarPorId(idCita);
            if (c == null) {
                JOptionPane.showMessageDialog(null, "⚠️ No se encontró la cita seleccionada.");
                return;
            }

            // 3. Modificar fecha y hora
            String fechaStr = JOptionPane.showInputDialog("Ingrese nueva fecha y hora (yyyy-MM-dd HH:mm):",
                    c.getFechaHora().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
            if (fechaStr == null)
                return;
            LocalDateTime fechaHora = LocalDateTime.parse(fechaStr, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));

            // 4. Modificar estado
            List<Estado> estados = estadoDAO.listar();
            StringBuilder se = new StringBuilder("ESTADOS DISPONIBLES:\n");
            for (Estado e : estados) {
                se.append(e.getId()).append(" - ").append(e.getNombre()).append("\n");
            }
            String estadoIdStr = JOptionPane.showInputDialog(se + "\nIngrese ID del estado:", c.getEstadoId());
            if (estadoIdStr == null)
                return;
            int estadoId = Integer.parseInt(estadoIdStr);

            // 5. Actualizar objeto
            c.setFechaHora(fechaHora);
            c.setEstadoId(estadoId);

            // 6. Guardar cambios
            JOptionPane.showMessageDialog(null, citaDAO.actualizar(c));

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "⚠️ El ID debe ser un número válido.");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al modificar: " + e.getMessage());
        }
    }

    // ELIMINAR
    private static void eliminar(CitaDAO citaDAO, EstadoDAO estadoDao, MascotaDAO mascotaDao) {
        try {
            // Pedir ID de la mascota
            String idMascotaStr = JOptionPane.showInputDialog("Ingrese ID de la mascota:");
            if (idMascotaStr == null || idMascotaStr.isBlank())
                return;

            int mascotaId = Integer.parseInt(idMascotaStr);

            // Buscar la mascota
            Mascota mascota = mascotaDao.buscarPorId(mascotaId);
            if (mascota == null) {
                JOptionPane.showMessageDialog(null, "⚠️ No existe una mascota con ese ID.");
                return;
            }

            // Listar las citas de esa mascota
            List<Cita> todas = citaDAO.listar();
            List<Cita> filtradas = todas.stream()
                    .filter(c -> c.getMascotaId() == mascotaId)
                    .toList();

            if (filtradas.isEmpty()) {
                JOptionPane.showMessageDialog(null, "No hay citas registradas para la mascota: " + mascota.getNombre());
                return;
            }

            StringBuilder sb = new StringBuilder("CITAS DE " + mascota.getNombre().toUpperCase() + " ===\n");
            for (Cita c : filtradas) {
                String nombreEstado = "-";
                Estado est = estadoDao.buscarPorId(c.getEstadoId());
                if (est != null)
                    nombreEstado = est.getNombre();

                sb.append("ID: ").append(c.getId())
                        .append(" | Fecha: ").append(c.getFechaHora())
                        .append(" | Motivo: ").append(c.getMotivo())
                        .append(" | Estado: ").append(nombreEstado)
                        .append("\n");
            }
            sb.append("\nIndique el ID de la cita que desea eliminar.");

            // Mostrar citas antes de pedir el ID
            String idCitaStr = JOptionPane.showInputDialog(null, sb.toString());
            if (idCitaStr == null || idCitaStr.isBlank())
                return;

            int idCita = Integer.parseInt(idCitaStr);

            // Eliminar cita
            JOptionPane.showMessageDialog(null, citaDAO.eliminar(idCita));

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "El ID debe ser un número válido.");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al eliminar: " + e.getMessage());
        }
    }

}
