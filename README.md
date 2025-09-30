# HappyFeet Veterinaria

## Descripción del Contexto
El sistema **HappyFeet Veterinaria** es una aplicación de consola desarrollada en Java que permite a una clínica veterinaria gestionar sus operaciones diarias. El sistema facilita el manejo de dueños, mascotas, citas, historial médico, inventario y facturación, garantizando un control organizado y confiable de la información.

## Tecnologías Utilizadas
- **Lenguaje:** Java (versión 17 o superior compatible)
- **Gestor de dependencias:** Maven
- **Base de Datos:** MySQL
- **Conexión a BD:** JDBC
- **IDE recomendado:** NetBeans o VS Code
- **Control de Versiones:** Git / GitHub

## Funcionalidades Implementadas
El sistema implementa los siguientes módulos principales:

- **Gestión de Dueños:** Registro, consulta y actualización de datos de los dueños de las mascotas.
- **Gestión de Mascotas:** Registro de mascotas, asociadas a sus dueños, incluyendo especie y raza.
- **Gestión de Citas:** Registro de citas médicas, con estados definidos.
- **Historial Médico:** Registro de atenciones realizadas a cada mascota.
- **Inventario:** Administración de productos disponibles (medicamentos, insumos, etc.).
- **Facturación:** Registro de facturas y detalle de ítems asociados.
- **Reportes básicos:** Consulta de facturas y listados de datos principales.

## Modelo de la Base de Datos
La base de datos está compuesta por varias tablas que representan las entidades principales:

- **Dueno**
- **Mascota**
- **Especie**
- **Raza**
- **Factura**
- **ItemFactura**
- **Inventario**
- **ProductoTipo**
- **Cita**
- **Estado**
- **EventoTipo**
- **HistorialMedico**

Las relaciones están definidas mediante claves foráneas, por ejemplo:
- Un `Dueno` puede tener varias `Mascota`.
- Una `Factura` tiene varios `ItemFactura`.
- Una `Mascota` puede tener múltiples registros en el `HistorialMedico`.

Los scripts para generar la base de datos se encuentran en la carpeta `/database`:
- **schema.sql:** Estructura de la base de datos.
- **data.sql:** Datos de prueba iniciales.

## Instrucciones de Instalación y Ejecución

### Requisitos Previos
- JDK 17 o superior
- Maven
- MySQL Server


### Configuración de la Base de Datos

- Ejecutar primero schema.sql y luego data.sql para crear las tablas y poblar los datos iniciales.

- Configurar la clase Conexion.java con las credenciales correctas de tu servidor MySQL

```bash
private static final String URL = "jdbc:mysql://localhost:3306/veterinaria";
private static final String USER = "tu_nombre_usuario";
private static final String PASSWORD = "tu_contraseña";
```

### ▶Ejecución del Proyecto

Abrir y ejecutar el archivo HappyFeet_Veterinaria.java

### Guía de Uso

Al ejecutar la aplicación se muestra un menú principal que permite navegar entre los distintos módulos:

Dueños

Mascotas

Citas

Inventario

Facturación

Cada módulo ofrece opciones de registrar, listar y consultar la información.