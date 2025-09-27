CREATE DATABASE IF NOT EXISTS veterinaria
  CHARACTER SET utf8mb4
  COLLATE utf8mb4_unicode_ci;
USE veterinaria;

-- =================== TABLAS CATÁLOGO ===================

CREATE TABLE especie (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) UNIQUE NOT NULL,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE raza (
    id INT AUTO_INCREMENT PRIMARY KEY,
    especie_id INT NOT NULL,
    nombre VARCHAR(100) NOT NULL,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (especie_id) REFERENCES especie(id)
        ON UPDATE CASCADE
        ON DELETE RESTRICT,
    UNIQUE (especie_id, nombre) -- evita duplicados dentro de la misma especie
);

CREATE TABLE producto_tipo (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) UNIQUE NOT NULL,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE evento_tipo (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) UNIQUE NOT NULL,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE cita_estado (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) UNIQUE NOT NULL,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP
);

-- =================== TABLAS PRINCIPALES ===================

CREATE TABLE dueno (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre_completo VARCHAR(255) NOT NULL,
    documento_identidad VARCHAR(20) UNIQUE NOT NULL,
    direccion VARCHAR(255),
    telefono VARCHAR(20),
    email VARCHAR(100) UNIQUE NOT NULL,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE mascota (
    id INT AUTO_INCREMENT PRIMARY KEY,
    dueno_id INT NOT NULL,
    nombre VARCHAR(100) NOT NULL,
    raza_id INT NOT NULL,
    fecha_nacimiento DATE,
    sexo ENUM('Macho','Hembra') NOT NULL,
    url_foto VARCHAR(255),
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (dueno_id) REFERENCES dueno(id)
        ON UPDATE CASCADE
        ON DELETE CASCADE,
    FOREIGN KEY (raza_id) REFERENCES raza(id)
        ON UPDATE CASCADE
        ON DELETE RESTRICT
);

CREATE TABLE historial_medico (
    id INT AUTO_INCREMENT PRIMARY KEY,
    mascota_id INT NOT NULL,
    fecha_evento DATETIME NOT NULL,
    evento_tipo_id INT NOT NULL,
    descripcion TEXT,
    diagnostico TEXT,
    tratamiento_recomendado TEXT,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (mascota_id) REFERENCES mascota(id)
        ON UPDATE CASCADE
        ON DELETE CASCADE,
    FOREIGN KEY (evento_tipo_id) REFERENCES evento_tipo(id)
        ON UPDATE CASCADE
        ON DELETE RESTRICT
);

CREATE TABLE inventario (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre_producto VARCHAR(255) NOT NULL,
    producto_tipo_id INT NOT NULL,
    descripcion TEXT,
    fabricante VARCHAR(100),
    lote VARCHAR(50),
    cantidad_stock INT NOT NULL DEFAULT 0,
    stock_minimo INT NOT NULL DEFAULT 1,
    fecha_vencimiento DATE,
    precio_venta DECIMAL(10,2) NOT NULL,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (producto_tipo_id) REFERENCES producto_tipo(id)
        ON UPDATE CASCADE
        ON DELETE RESTRICT
);

CREATE TABLE cita (
    id INT AUTO_INCREMENT PRIMARY KEY,
    mascota_id INT NOT NULL,
    fecha_hora DATETIME NOT NULL,
    motivo VARCHAR(255),
    estado_id INT NOT NULL,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (mascota_id) REFERENCES mascota(id)
        ON UPDATE CASCADE
        ON DELETE CASCADE,
    FOREIGN KEY (estado_id) REFERENCES cita_estado(id)
        ON UPDATE CASCADE
        ON DELETE RESTRICT
);

CREATE TABLE factura (
    id INT AUTO_INCREMENT PRIMARY KEY,
    dueno_id INT NOT NULL,
    fecha_emision DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    total DECIMAL(10,2) NOT NULL,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (dueno_id) REFERENCES dueno(id)
        ON UPDATE CASCADE
        ON DELETE CASCADE
);

CREATE TABLE item_factura (
    id INT AUTO_INCREMENT PRIMARY KEY,
    factura_id INT NOT NULL,
    producto_id INT NULL, -- Puede ser NULL si es solo un servicio
    servicio_descripcion VARCHAR(255), -- Texto libre para servicios
    cantidad INT NOT NULL DEFAULT 1,
    precio_unitario DECIMAL(10,2) NOT NULL,
    subtotal DECIMAL(10,2) NOT NULL,
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (factura_id) REFERENCES factura(id)
        ON UPDATE CASCADE
        ON DELETE CASCADE,
    FOREIGN KEY (producto_id) REFERENCES inventario(id)
        ON UPDATE CASCADE
        ON DELETE SET NULL
);
