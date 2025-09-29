package com.happyfeet.happyfeet_veterinaria;

import java.sql.Connection;
import javax.swing.JOptionPane;
import com.happyfeet.happyfeet_veterinaria.menu.*;;

public class HappyFeet_Veterinaria {

    public static void main(String[] args) {
        try (Connection con = Conexion.getConexion()) {  // try-with-resources cierra solo
            mostrarMenuPrincipal(con);                   // Delegamos el menú
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,
                "Error en la aplicación: " + e.getMessage());
        }
    }

    // MENÚ PRINCIPAL
    private static void mostrarMenuPrincipal(Connection con) {
        boolean salir = false;

        while (!salir) {
            String opcion = JOptionPane.showInputDialog(
                    null,
                    "=== MENÚ PRINCIPAL ===\n"
                  + "1. Dueños\n"
                  + "2. Mascotas\n"
                  + "3. Citas\n"
                  + "4. Inventario\n"
                  + "5. Facturas\n"
                  + "0. Salir\n\n"
                  + "Elige una opción:"
            );

            if (opcion == null) break;  // Usuario cerró la ventana

            switch (opcion) {
                case "1" -> MenuDueno.mostrar(con);
                case "2" -> MenuMascota.mostrar(con);
                case "3" -> MenuCita.mostrar(con);
                case "4" -> MenuInventario.mostrar(con);
                case "5" -> MenuFactura.mostrar(con);
                case "0" -> salir = true;
                default  -> JOptionPane.showMessageDialog(null, "Opción no válida");
            }
        }

        JOptionPane.showMessageDialog(null, "Conexión cerrada");
    }
}
